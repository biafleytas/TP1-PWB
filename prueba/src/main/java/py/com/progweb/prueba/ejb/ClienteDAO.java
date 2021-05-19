package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Bolsa;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.Punto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class ClienteDAO
{
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar (Cliente entidad)
    {
        this.em.persist(entidad);
    }

    public List<Cliente> lista()
    {
        Query q = this.em.createQuery("select p from Cliente p");

        return (List<Cliente>) q.getResultList();
    }

    public void eliminar(int idCliente)
    {
        Query q = this.em.createQuery("select count (p) from Cliente p where p.idCliente= :id");
        q.setParameter("id",idCliente);
        Long cantidad =(Long) q.getSingleResult();
        if (cantidad!=0)
        {
            q = this.em.createQuery("delete from Cliente p where p.idCliente= :id");
            q.setParameter("id", idCliente);
            try {
                int rows = q.executeUpdate();
            }catch (Exception e)
            {
                System.out.println("El cliente que desea eliminar ya ha realizado transacciones, por lo que no puede ser eliminado");
            }
        }else{
            System.out.println("El cliente que desea eliminar  no existe");
        }

    }

    public void modificar(int idCliente, String nombre, String apellido, String numeroDocumento, String tipoDocumento, String nacionalidad, String email, String telefono, Date fechaNacimiento)
    {
        Query q = this.em.createQuery("select count (p) from Cliente p where p.idCliente= :id");
        q.setParameter("id",idCliente);
        Long cantidad =(Long) q.getSingleResult();
        if (cantidad!=0)
        {
            q = this.em.createQuery("update Cliente p " +
                    "set p.nombre= :nombre, " +
                    "p.apellido= :apellido," +
                    "p.numeroDocumento= :numeroDocumento," +
                    "p.tipoDocumento= :tipoDocumento," +
                    "p.nacionalidad= :nacionalidad," +
                    "p.email= :email," +
                    "p.telefono= :telefono," +
                    "p.fechaNacimiento= :fechaNacimiento" +
                    " where p.idCliente= :idCliente");
            q.setParameter("idCliente", idCliente);
            q.setParameter("nombre", nombre);
            q.setParameter("apellido", apellido);
            q.setParameter("numeroDocumento", numeroDocumento);
            q.setParameter("tipoDocumento", tipoDocumento);
            q.setParameter("nacionalidad", nacionalidad);
            q.setParameter("email", email);
            q.setParameter("telefono", telefono);
            q.setParameter("fechaNacimiento", fechaNacimiento);
            int rows = q.executeUpdate();
        }else{
            System.out.println("El cliente que desea modificar no existe");
        }
    }

}
