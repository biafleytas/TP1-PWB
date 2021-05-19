package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Regla;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ReglaDAO
{
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar (Regla entidad)
    {
        this.em.persist(entidad);
    }

    public List<Regla> lista()
    {
        Query q = this.em.createQuery("select p from Regla p");

        return (List<Regla>) q.getResultList();
    }

    public void eliminar(int idRegla)
    {
        Query q = this.em.createQuery("select count (p) from Regla p where p.idRegla= :id");
        q.setParameter("id",idRegla);
        Long cantidad =(Long) q.getSingleResult();
        if (cantidad!=0)
        {
            q = this.em.createQuery("delete from Regla p where p.idRegla= :id");
            q.setParameter("id", idRegla);
            int rows = q.executeUpdate();
        }else{
            System.out.println("La regla que desea eliminar no existe");
        }
    }

    public void modificar(int idRegla, int limiteInferior, int limiteSuperior, int montoEquivalencia)
    {
        Query q = this.em.createQuery("select count (p) from Regla p where p.idRegla= :id");
        q.setParameter("id",idRegla);
        Long cantidad =(Long) q.getSingleResult();
        if (cantidad!=0)
        {
            q = this.em.createQuery("update Regla p " +
                    "set p.limiteInferior= :limiteInferior, p.limiteSuperior= :limiteSuperior, p.montoEquivalencia= :montoEquivalencia " +
                    "where p.idRegla= :idRegla");
            q.setParameter("idRegla", idRegla);
            q.setParameter("limiteInferior", limiteInferior);
            q.setParameter("limiteSuperior", limiteSuperior);
            q.setParameter("montoEquivalencia", montoEquivalencia);
            int rows = q.executeUpdate();
        }else{
            System.out.println("La regla que desea modificar no existe");
        }
    }

}
