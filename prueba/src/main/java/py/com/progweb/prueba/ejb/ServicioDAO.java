package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.*;

import javax.ejb.Stateless;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;


@Stateless
public class ServicioDAO
{
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;


    public Integer equivalencia(int monto)
    {
        Integer puntosEquivalentes = 0;
        try
        {
            Query q = this.em.createQuery("select r from Regla r where :monto between r.limiteInferior and r.limiteSuperior");
            q.setParameter("monto", monto);
            Regla regla = (Regla) q.getSingleResult();
            puntosEquivalentes = monto / regla.getMontoEquivalencia();
        }catch(javax.persistence.NoResultException e)
        {
            System.out.println("No existe una regla definida para ese monto");
        }
        return (puntosEquivalentes);
    }

    public void asignarPuntos (int idCliente, int monto)
    {
        String errorVencimiento = "";
        String errorCliente = "";
        Bolsa bolsa=new Bolsa();

        Date fecha = new Date();
        bolsa.setFechaAsignacion(fecha);

        Integer puntajeAsignado = equivalencia(monto);
        bolsa.setPuntajeAsignado(puntajeAsignado);

        bolsa.setPuntajeUtilizado(0);
        bolsa.setSaldo(puntajeAsignado);
        bolsa.setMontoOperacion(monto);
        bolsa.setEstado("activo");

        Query q = this.em.createQuery("select c from Cliente c where c.idCliente= :idCliente");
        q.setParameter("idCliente", idCliente);
        try {
            Cliente cliente = (Cliente) q.getSingleResult();
            bolsa.setCliente(cliente);
        }catch(javax.persistence.NoResultException e)
        {
            errorCliente = "El cliente no existe";
        }

        q = this.em.createQuery("select v from Vencimiento v where :fecha between v.fechaInicio and v.fechaFin");
        q.setParameter("fecha", fecha);
        try {
            Vencimiento vencimiento = (Vencimiento) q.getSingleResult();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.add(Calendar.DAY_OF_YEAR, vencimiento.getDuracion());
            fecha = calendar.getTime();
            bolsa.setFechaCaducidad(fecha);
        }catch(javax.persistence.NoResultException e)
        {
            errorVencimiento = "No existe regla para la duracion de puntos";
        }

        if(puntajeAsignado!=0 && errorCliente=="" && errorVencimiento=="")
        {
            this.em.persist(bolsa);
        }else{
            System.out.println("No se puede registrar los puntos: "+errorCliente+" "+errorVencimiento);
        }
    }

    public void descontarPuntos (int idCliente, int idPunto)
    {
        String destinatario = "";
        int puntosADescontar = 0;
        Punto punto = new Punto();
        Query q = this.em.createQuery("select p from Punto p where p.idPunto = :idPunto");
        q.setParameter("idPunto", idPunto);
        try {
            punto = (Punto) q.getSingleResult();
            puntosADescontar = punto.getPuntosRequeridos();
        }catch(javax.persistence.NoResultException e){
            System.out.println("El concepto de uso no existe");
            return;
        }

        PuntosUsados cabecera = new PuntosUsados();
        Date fecha = new Date();
        cabecera.setFecha(fecha);
        cabecera.setConcepto(punto.getConcepto());
        cabecera.setPuntajeUtilizado(puntosADescontar);
        q = this.em.createQuery("select c from Cliente c where c.idCliente = :idCliente");
        q.setParameter("idCliente", idCliente);
        try
        {
            Cliente cliente = (Cliente) q.getSingleResult();
            cabecera.setCliente(cliente);
            destinatario = cliente.getEmail();
        }catch(javax.persistence.NoResultException e){
            System.out.println("El cliente no existe");
            return;
        }

        List<PuntosUsadosDetalle> detalles= new ArrayList<>();

        q = this.em.createQuery("select b from Bolsa b where b.cliente.idCliente= :idCliente and b.estado='activo' order by b.fechaAsignacion asc");
        q.setParameter("idCliente", idCliente);
        List<Bolsa> bolsas = (List<Bolsa>) q.getResultList();
        int i =0;
        q = this.em.createQuery("Select Sum(b.saldo) from Bolsa b where b.cliente.idCliente = :idCliente");
        q.setParameter("idCliente", idCliente);
        Long totalPuntos = (Long) q.getSingleResult();
        if (totalPuntos < puntosADescontar)
        {
            System.out.println("Cliente no posee puntos suficientes");
        }else {
            while (puntosADescontar > 0) {
                Bolsa bolsa = bolsas.get(i);
                int saldoEnBolsa = bolsa.getSaldo();
                if (saldoEnBolsa > puntosADescontar) {
                    q = this.em.createQuery("update Bolsa b set b.saldo= :saldo, b.puntajeUtilizado= :utilizado where b.idBolsa= :idBolsa");
                    q.setParameter("idBolsa", bolsa.getIdBolsa());
                    q.setParameter("saldo", saldoEnBolsa - puntosADescontar);
                    q.setParameter("utilizado", bolsa.getPuntajeUtilizado() + puntosADescontar);
                    int rows = q.executeUpdate();

                    PuntosUsadosDetalle detalle = new PuntosUsadosDetalle();
                    detalle.setPuntajeUtilizado(puntosADescontar);
                    detalle.setBolsa(bolsa);
                    detalles.add(detalle);

                    puntosADescontar = 0;

                } else if (saldoEnBolsa <= puntosADescontar) {
                    q = this.em.createQuery("update Bolsa b set b.saldo= 0, b.puntajeUtilizado= :utilizado, b.estado='usado' where b.idBolsa= :idBolsa");
                    q.setParameter("idBolsa", bolsa.getIdBolsa());
                    q.setParameter("utilizado", bolsa.getPuntajeUtilizado() + bolsa.getSaldo());
                    int rows = q.executeUpdate();
                    puntosADescontar = puntosADescontar - saldoEnBolsa;

                    PuntosUsadosDetalle detalle = new PuntosUsadosDetalle();
                    detalle.setPuntajeUtilizado(bolsa.getSaldo());
                    detalle.setBolsa(bolsa);
                    detalles.add(detalle);
                }
                i = i + 1;
            }
            this.em.persist(cabecera);
            for (PuntosUsadosDetalle d : detalles) {
                d.setPuntosUsados(cabecera);
                this.em.persist(d);
            }
            String asunto = "Puntos Utilizados";
            String cuerpo = "Se descontaron " +punto.getPuntosRequeridos()+ " puntos de su cuenta en concepto de " + punto.getConcepto();
            enviarCorreo(destinatario, asunto, cuerpo);
        }
    }

    private static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        String remitente = "sistemagestionreservas@gmail.com";
        String clave = "*linkin1995";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
