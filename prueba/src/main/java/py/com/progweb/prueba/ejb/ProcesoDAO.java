package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Bolsa;
import py.com.progweb.prueba.model.PuntosUsadosDetalle;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class ProcesoDAO
{
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void bolsasVencidas()
    {
        Date fecha = new Date();
        Query q = this.em.createQuery("select b from Bolsa b where b.estado= 'activo' and b.fechaCaducidad < :fecha");
        q.setParameter("fecha", fecha);
        List<Bolsa> bolsas = (List<Bolsa>) q.getResultList();
        for (Bolsa v: bolsas)
        {
            q = this.em.createQuery("update Bolsa b set b.estado='vencido' where b.idBolsa= :idBolsa");
            q.setParameter("idBolsa", v.getIdBolsa());
            int rows =q.executeUpdate();
        }
    }
}
