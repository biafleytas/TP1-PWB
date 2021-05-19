package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ReglaDAO;
import py.com.progweb.prueba.model.Regla;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("regla")
@Consumes("application/json")
@Produces("application/json")
public class ReglaRest
{
    @Inject
    private ReglaDAO reglaDAO;

    @GET
    @Path("/")
    public Response listar()
    {
        return Response.ok(reglaDAO.lista()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{idRegla}")
    public Response modificar(@PathParam("idRegla") int idRegla,
                              @FormParam("limiteInferior") int limiteInferior,
                              @FormParam("limiteSuperior") int limiteSuperior,
                              @FormParam("montoEquivalencia") int montoEquivalencia)
    {
        this.reglaDAO.modificar(idRegla, limiteInferior, limiteSuperior, montoEquivalencia);
        return Response.ok().build();
    }

    @POST
    @Path("/")
    public Response crear(Regla p)
    {
        this.reglaDAO.agregar(p);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{idRegla}")
    public Response eliminar(@PathParam("idRegla") int idRegla)
    {
        this.reglaDAO.eliminar(idRegla);
        return Response.ok().build();
    }
}
