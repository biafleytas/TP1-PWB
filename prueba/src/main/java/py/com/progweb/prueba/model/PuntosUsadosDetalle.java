package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="puntos_usados_detalle")
public class PuntosUsadosDetalle
{
    @Id
    @Column(name = "id_puntos_usados_detalle")
    @Basic(optional = false)
    @GeneratedValue(generator = "puntosUsadosDetalleSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "puntosUsadosDetalleSec", sequenceName = "puntos_usados_detalle_sec", allocationSize = 0)
    private Integer idPuntosUsadosDetalle;

    @Column (name = "puntaje_utilizado", length = 50)
    @Basic(optional = false)
    private Integer puntajeUtilizado;

    @JoinColumn(name = "id_puntos_usados", referencedColumnName = "id_puntos_usados")
    @ManyToOne(optional = false)
    @JsonBackReference
    private PuntosUsados puntosUsados;

    @JoinColumn(name = "id_bolsa", referencedColumnName = "id_bolsa")
    @ManyToOne(optional = false)
    private Bolsa bolsa;

    public PuntosUsadosDetalle(){}

    public Integer getIdPuntosUsadosDetalle() {
        return idPuntosUsadosDetalle;
    }

    public void setIdPuntosUsadosDetalle(Integer idPuntosUsadosDetalle) {
        this.idPuntosUsadosDetalle = idPuntosUsadosDetalle;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public PuntosUsados getPuntosUsados() {
        return puntosUsados;
    }

    public void setPuntosUsados(PuntosUsados puntosUsados) {
        this.puntosUsados = puntosUsados;
    }

    public Bolsa getBolsa() {
        return bolsa;
    }

    public void setBolsa(Bolsa bolsa) {
        this.bolsa = bolsa;
    }
}
