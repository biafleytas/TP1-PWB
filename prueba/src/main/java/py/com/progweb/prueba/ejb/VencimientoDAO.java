package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Vencimiento;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class VencimientoDAO
{
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar (Vencimiento entidad)
    {
        this.em.persist(entidad);
    }

    public List<Vencimiento> lista()
    {
        Query q = this.em.createQuery("select p from Vencimiento p");
        return (List<Vencimiento>) q.getResultList();
    }

    public void eliminar(int idVencimiento)
    {
        Query q = this.em.createQuery("select count (p) from Vencimiento p where p.idVencimiento= :id");
        q.setParameter("id",idVencimiento);
        Long cantidad =(Long) q.getSingleResult();
        if (cantidad!=0)
        {
            q = this.em.createQuery("delete from Vencimiento p where p.idVencimiento= :id");
            q.setParameter("id", idVencimiento);
            int rows = q.executeUpdate();
        }else{
            System.out.println("El vencimiento que desea eliminar no existe");
        }
    }

    public void modificar(int idVencimiento, Date fechaInicio, Date fechaFin, int duracion)
    {
        Query q = this.em.createQuery("select count (p) from Vencimiento p where p.idVencimiento= :id");
        q.setParameter("id",idVencimiento);
        Long cantidad =(Long) q.getSingleResult();
        if (cantidad!=0)
        {
            q = this.em.createQuery("update Vencimiento p " +
                    "set p.fechaInicio= :fechaInicio, p.fechaFin= :fechaFin, p.duracion= :duracion " +
                    "where p.idVencimiento= :idVencimiento");
            q.setParameter("idVencimiento", idVencimiento);
            q.setParameter("fechaInicio", fechaInicio);
            q.setParameter("fechaFin", fechaFin);
            q.setParameter("duracion", duracion);
            int rows = q.executeUpdate();
        }else{
            System.out.println("El vencimiento que desea modificar no existe");
        }
    }
}
