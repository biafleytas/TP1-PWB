package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="bolsa")
public class Bolsa
{
    @Id
    @Column(name = "id_bolsa")
    @Basic(optional = false)
    @GeneratedValue(generator = "bolsaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bolsaSec", sequenceName = "bolsa_sec", allocationSize = 0)
    private Integer idBolsa;

    @Column(name = "fecha_asignacion")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaAsignacion;

    @Column(name = "fecha_caducidad")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaCaducidad;

    @Column (name = "puntaje_asignado", length = 50)
    @Basic(optional = false)
    private Integer puntajeAsignado;

    @Column (name = "puntaje_utilizado", length = 50)
    @Basic(optional = false)
    private Integer puntajeUtilizado;

    @Column (name = "saldo", length = 50)
    @Basic(optional = false)
    private Integer saldo;

    @Column (name = "monto_operacion", length = 50)
    @Basic(optional = false)
    private Integer montoOperacion;

    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Cliente cliente;

    @Column (name = "estado", length = 50)
    @Basic(optional = false)
    private String estado;

    public Bolsa(){}

    public Integer getIdBolsa() {
        return idBolsa;
    }

    public void setIdBolsa(Integer idBolsa) {
        this.idBolsa = idBolsa;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Integer getPuntajeAsignado() {
        return puntajeAsignado;
    }

    public void setPuntajeAsignado(Integer puntajeAsignado) {
        this.puntajeAsignado = puntajeAsignado;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Integer getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(Integer montoOperacion) {
        this.montoOperacion = montoOperacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
