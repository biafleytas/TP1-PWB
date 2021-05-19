package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.PuntoDAO;
import py.com.progweb.prueba.model.Punto;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("punto")
@Consumes("application/json")
@Produces("application/json")
public class PuntoRest
{
    @Inject
    private PuntoDAO puntoDAO;

    @GET
    @Path("/{concepto}")
    public Response listarPuntoPorConcepto(@PathParam("concepto") String concepto)
    {
        return Response.ok(puntoDAO.puntoPorConcepto(concepto)).build();
    }

    @GET
    @Path("/")
    public Response listar()
    {
        return Response.ok(puntoDAO.lista()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{idPunto}")
    public Response modificar(@PathParam("idPunto") int idPunto,
                              @FormParam("puntosRequeridos") int puntosRequeridos,
                              @FormParam("concepto") String concepto)
    {
        this.puntoDAO.modificar(idPunto, puntosRequeridos, concepto);
        return Response.ok().build();
    }

    @POST
    @Path("/")
    public Response crear(Punto p)
    {
        this.puntoDAO.agregar(p);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{idPunto}")
    public Response eliminar(@PathParam("idPunto") int idPunto)
    {
        this.puntoDAO.eliminar(idPunto);
        return Response.ok().build();
    }


}
