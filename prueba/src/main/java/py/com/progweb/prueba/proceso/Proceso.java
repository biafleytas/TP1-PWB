package py.com.progweb.prueba.proceso;

import py.com.progweb.prueba.ejb.ProcesoDAO;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class Proceso
{
    @Inject
    ProcesoDAO procesoDAO;

    @Schedule(minute = "*/5", hour = "*", info = "5MinScheduler", persistent = false)
    public void vencimientos()
    {
        procesoDAO.bolsasVencidas();
    }
}
