package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.ejb.ReporteDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;


@Path("reporte")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces("application/json")
public class ReporteRest
{
    @Inject
    private ReporteDAO reporteDAO;

    @GET
    @Path("/reporteCliente")
    public Response listarClientes(@FormParam("nombre") String nombre,
                                   @FormParam("apellido") String apellido,
                                   @FormParam("fechaNacimiento") Date fechaNacimiento)
    {
        return Response.ok(reporteDAO.reporteCliente(nombre, apellido, fechaNacimiento)).build();
    }

    @GET
    @Path("/bolsasPorCliente")
    public Response listarBolsasPorCliente(@FormParam("idCliente") int idCliente)
    {
        return Response.ok(reporteDAO.reporteBolsaPorCliente(idCliente)).build();
    }

    @GET
    @Path("/bolsasPorRango")
    public Response listarBolsasPorRango(@FormParam("inferior") Integer inferior,
                                         @FormParam("superior") Integer superior)
    {
        return Response.ok(reporteDAO.reporteBolsaPorRango(inferior, superior)).build();
    }

    @GET
    @Path("/bolsasAVencer")
    public Response listarClientesAVencer(@FormParam("dias") int dias)
    {
        return Response.ok(reporteDAO.reporteClientesAVencer(dias)).build();
    }

    @GET
    @Path("/puntosUsadosPorConcepto")
    public Response listarPuntoPorConcepto(@FormParam("concepto") String concepto)
    {
        return Response.ok(reporteDAO.puntoPorConcepto(concepto)).build();
    }

    @GET
    @Path("/puntosUsadosPorFecha")
    public Response listarPuntoPorFecha(@FormParam("fecha") Date fecha)
    {
        return Response.ok(reporteDAO.puntoPorFecha(fecha)).build();
    }

    @GET
    @Path("/puntosUsadosPorCliente")
    public Response listarPuntoPorCliente(@FormParam("idCliente") int idCliente)
    {
        return Response.ok(reporteDAO.puntoPorCliente(idCliente)).build();
    }
}
