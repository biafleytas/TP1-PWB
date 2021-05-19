package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Bolsa;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.Punto;
import py.com.progweb.prueba.model.PuntosUsados;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class ReporteDAO
{
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public List<Cliente> reporteCliente(String nombre, String apellido, Date fechaNacimiento)
    {
        Query q = null;
        if (nombre!=null)
        {
            q = this.em.createQuery("select p from Cliente p where LOWER(p.nombre) like LOWER(:nombre)");
            q.setParameter("nombre", "%"+nombre+"%");
        }else if (apellido!=null){
            q = this.em.createQuery("select p from Cliente p where LOWER(p.apellido) like LOWER(:apellido)");
            q.setParameter("apellido", "%"+apellido+"%");
        }else if (fechaNacimiento!=null)
        {
            q = this.em.createQuery("select p from Cliente p where p.fechaNacimiento= :fecha");
            q.setParameter("fecha", fechaNacimiento);
        }
        return (List<Cliente>) q.getResultList();
    }

    public List<Bolsa> reporteBolsaPorCliente(int idCliente)
    {
        Query q = this.em.createQuery("select b from Bolsa b where b.cliente.idCliente= :idCliente");
        q.setParameter("idCliente", idCliente);
        return (List<Bolsa>) q.getResultList();
    }

    public List<Bolsa> reporteBolsaPorRango(Integer inferior, Integer superior)
    {
        if (inferior == null)
        {
            inferior=0;
        }
        if(superior == null)
        {
            superior=Integer.MAX_VALUE;
        }
        Query q = this.em.createQuery("select b from Bolsa b where b.saldo between :inferior and :superior");
        q.setParameter("inferior", inferior);
        q.setParameter("superior", superior);
        return (List<Bolsa>) q.getResultList();
    }

    public List<Bolsa> reporteClientesAVencer(int dias)
    {
        Date fecha = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        fecha = calendar.getTime();
        Query q = this.em.createQuery("select distinct b.cliente from Bolsa b where b.fechaCaducidad <= :fechaCaducidad and b.estado='activo'");
        q.setParameter("fechaCaducidad", fecha);
        return (List<Bolsa>) q.getResultList();
    }

    public List<PuntosUsados> puntoPorConcepto(String concepto)
    {
        Query q = this.em.createQuery("select p from PuntosUsados p where p.concepto= :concepto");
        q.setParameter("concepto", concepto);
        return (List<PuntosUsados>) q.getResultList();
    }

    public List<PuntosUsados> puntoPorFecha(Date fecha)
    {
        Query q = this.em.createQuery("select p from PuntosUsados p where p.fecha= :fecha");
        q.setParameter("fecha", fecha);
        return (List<PuntosUsados>) q.getResultList();
    }

    public List<PuntosUsados> puntoPorCliente(int idCliente)
    {
        Query q = this.em.createQuery("select p from PuntosUsados p where p.cliente.idCliente= :idCliente");
        q.setParameter("idCliente", idCliente);
        return (List<PuntosUsados>) q.getResultList();
    }
}
