package py.com.progweb.prueba.model;


import javax.persistence.*;

@Entity
@Table(name="regla")
public class Regla
{
    @Id
    @Column(name = "id_regla")
    @Basic(optional = false)
    @GeneratedValue(generator = "reglaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reglaSec", sequenceName = "regla_sec", allocationSize = 0)
    private Integer idRegla;

    @Column (name = "limite_inferior", length = 50)
    @Basic(optional = false)
    private Integer limiteInferior;

    @Column (name = "limite_superior", length = 50)
    @Basic(optional = false)
    private Integer limiteSuperior;

    @Column (name = "monto_equivalencia", length = 50)
    @Basic(optional = false)
    private Integer montoEquivalencia;

    public Regla(){}

    public Integer getIdRegla() {
        return idRegla;
    }

    public void setIdRegla(Integer idRegla) {
        this.idRegla = idRegla;
    }

    public Integer getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(Integer limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public Integer getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(Integer limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public Integer getMontoEquivalencia() {
        return montoEquivalencia;
    }

    public void setMontoEquivalencia(Integer montoEquivalencia) {
        this.montoEquivalencia = montoEquivalencia;
    }
}
