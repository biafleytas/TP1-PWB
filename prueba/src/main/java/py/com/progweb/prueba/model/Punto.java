package py.com.progweb.prueba.model;

import javax.persistence.*;


@Entity
@Table(name="punto")
public class Punto
{
    @Id
    @Column(name = "id_punto")
    @Basic(optional = false)
    @GeneratedValue(generator = "puntoSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "puntoSec", sequenceName = "punto_sec", allocationSize = 0)
    private Integer idPunto;

    @Column (name = "concepto", length = 50)
    @Basic(optional = false)
    private String concepto;

    @Column (name = "puntos_requeridos", length = 50)
    @Basic(optional = false)
    private Integer puntosRequeridos;

    public Punto(){}

    public Integer getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(Integer idPunto) {
        this.idPunto = idPunto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(Integer puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }
}
