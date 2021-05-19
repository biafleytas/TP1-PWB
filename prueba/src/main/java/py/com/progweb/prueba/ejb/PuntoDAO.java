package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Punto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PuntoDAO
{
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar (Punto entidad)
    {
        this.em.persist(entidad);
    }

    public List<Punto> lista()
    {
        Query q = this.em.createQuery("select p from Punto p");

        return (List<Punto>) q.getResultList();
    }

    public List<Punto> puntoPorConcepto(String concepto)
    {
        Query q = this.em.createQuery("select p from Punto p where p.concepto= :concepto").setParameter("concepto", concepto);
        return (List<Punto>) q.getResultList();
    }

    public void eliminar(int idPunto)
    {
        Query q = this.em.createQuery("select count (p) from Punto p where p.idPunto= :id");
        q.setParameter("id",idPunto);
        Long punto =(Long) q.getSingleResult();
        if (punto!=0)
        {
            q = this.em.createQuery("delete from Punto p where p.idPunto= :id");
            q.setParameter("id", idPunto);
            int rows = q.executeUpdate();
        }else{
            System.out.println("El punto que desea eliminar no existe");
        }
    }

    public void modificar(int idPunto, int puntosRequeridos, String concepto)
    {
        Query q = this.em.createQuery("select count (p) from Punto p where p.idPunto= :id");
        q.setParameter("id",idPunto);
        Long cantidad =(Long) q.getSingleResult();
        if (cantidad!=0) {
            q = this.em.createQuery("update Punto p " +
                    "set p.concepto= :concepto, p.puntosRequeridos= :puntosRequeridos " +
                    "where p.idPunto= :idPunto");
            q.setParameter("idPunto", idPunto);
            q.setParameter("concepto", concepto);
            q.setParameter("puntosRequeridos", puntosRequeridos);
            int rows = q.executeUpdate();
        }else{
            System.out.println("El punto que desea modificar no existe");
        }
    }

}
