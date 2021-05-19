package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.model.Cliente;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;


@Path("cliente")
@Consumes("application/json")
@Produces("application/json")
public class ClienteRest
{
    @Inject
    private ClienteDAO clienteDAO;

    @GET
    @Path("/")
    public Response listar()
    {
        return Response.ok(clienteDAO.lista()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{idCliente}")
    public Response modificar(@PathParam("idCliente") int idCliente,
                              @FormParam("nombre") String nombre,
                              @FormParam("apellido") String apellido,
                              @FormParam("numeroDocumento") String numeroDocumento,
                              @FormParam("tipoDocumento") String tipoDocumento,
                              @FormParam("nacionalidad") String nacionalidad,
                              @FormParam("email") String email,
                              @FormParam("telefono") String telefono,
                              @FormParam("fechaNacimiento") Date fechaNacimiento)
    {
        this.clienteDAO.modificar(idCliente, nombre, apellido, numeroDocumento, tipoDocumento,nacionalidad,email,telefono,fechaNacimiento);
        return Response.ok().build();
    }

    @POST
    @Path("/")
    public Response crear(Cliente p)
    {
        this.clienteDAO.agregar(p);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{idCliente}")
    public Response eliminar(@PathParam("idCliente") int idCliente)
    {
        this.clienteDAO.eliminar(idCliente);
        return Response.ok().build();
    }

}
