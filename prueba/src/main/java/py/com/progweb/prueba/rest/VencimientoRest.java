package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.VencimientoDAO;
import py.com.progweb.prueba.model.Vencimiento;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;


@Path("vencimiento")
@Consumes("application/json")
@Produces("application/json")
public class VencimientoRest
{
    @Inject
    private VencimientoDAO vencimientoDAO;

    @GET
    @Path("/")
    public Response listar()
    {
        return Response.ok(vencimientoDAO.lista()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{idVencimiento}")
    public Response modificar(@PathParam("idVencimiento") int idVencimiento,
                              @FormParam("fechaInicio") Date fechaInicio,
                              @FormParam("fechaFin") Date fechaFin,
                              @FormParam("duracion") int duracion)
    {
        this.vencimientoDAO.modificar(idVencimiento, fechaInicio, fechaFin, duracion);
        return Response.ok().build();
    }

    @POST
    @Path("/")
    public Response crear(Vencimiento p)
    {
        this.vencimientoDAO.agregar(p);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{idVencimiento}")
    public Response eliminar(@PathParam("idVencimiento") int idVencimiento)
    {
        this.vencimientoDAO.eliminar(idVencimiento);
        return Response.ok().build();
    }
}
