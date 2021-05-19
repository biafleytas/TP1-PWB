package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ProcesoDAO;
import py.com.progweb.prueba.ejb.ReporteDAO;
import py.com.progweb.prueba.ejb.ServicioDAO;
import py.com.progweb.prueba.model.Punto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("servicio")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces("application/json")
public class ServicioRest
{
    @Inject
    private ServicioDAO servicioDAO;

    @GET
    @Path("/equivalencia")
    public Response puntosEquivalencia(@FormParam("monto") int monto)
    {
        return Response.ok(servicioDAO.equivalencia(monto)).build();
    }

    @POST
    @Path("/cargaPuntos")
    public Response cargaPuntos(@FormParam("idCliente") int idCliente,
                                @FormParam("monto") int monto)
    {
        this.servicioDAO.asignarPuntos(idCliente, monto);
        return Response.ok().build();
    }

    @POST
    @Path("/puntosUtilizados")
    public Response utilizacionPuntos(@FormParam("idCliente") int idCliente,
                                      @FormParam("idPunto") int idPunto)
    {
        this.servicioDAO.descontarPuntos(idCliente, idPunto);
        return Response.ok().build();
    }

}
