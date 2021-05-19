package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="puntos_usados")
public class PuntosUsados
{
    @Id
    @Column(name = "id_puntos_usados")
    @Basic(optional = false)
    @GeneratedValue(generator = "puntosUsadosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "puntosUsadosSec", sequenceName = "puntos_usados_sec", allocationSize = 0)
    private Integer idPuntosUsados;

    @Column (name = "puntaje_utilizado", length = 50)
    @Basic(optional = false)
    private Integer puntajeUtilizado;

    @Column(name = "fecha")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha;

    @Column (name = "concepto", length = 50)
    @Basic(optional = false)
    private String concepto;

    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "puntosUsados", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PuntosUsadosDetalle> detalles;

    public PuntosUsados(){}

    public Integer getIdPuntosUsados() {
        return idPuntosUsados;
    }

    public void setIdPuntosUsados(Integer idPuntosUsados) {
        this.idPuntosUsados = idPuntosUsados;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PuntosUsadosDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PuntosUsadosDetalle> detalles) {
        this.detalles = detalles;
    }
}
