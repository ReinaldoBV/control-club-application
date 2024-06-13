package cl.controlclub.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EstadisticasBaloncesto.
 */
@Entity
@Table(name = "estadisticas_baloncesto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadisticasBaloncesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "puntos")
    private Integer puntos;

    @Column(name = "rebotes")
    private Integer rebotes;

    @Column(name = "asistencias")
    private Integer asistencias;

    @Column(name = "robos")
    private Integer robos;

    @Column(name = "bloqueos")
    private Integer bloqueos;

    @Column(name = "porcentaje_tiro", precision = 21, scale = 2)
    private BigDecimal porcentajeTiro;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstadisticasBaloncesto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntos() {
        return this.puntos;
    }

    public EstadisticasBaloncesto puntos(Integer puntos) {
        this.setPuntos(puntos);
        return this;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getRebotes() {
        return this.rebotes;
    }

    public EstadisticasBaloncesto rebotes(Integer rebotes) {
        this.setRebotes(rebotes);
        return this;
    }

    public void setRebotes(Integer rebotes) {
        this.rebotes = rebotes;
    }

    public Integer getAsistencias() {
        return this.asistencias;
    }

    public EstadisticasBaloncesto asistencias(Integer asistencias) {
        this.setAsistencias(asistencias);
        return this;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public Integer getRobos() {
        return this.robos;
    }

    public EstadisticasBaloncesto robos(Integer robos) {
        this.setRobos(robos);
        return this;
    }

    public void setRobos(Integer robos) {
        this.robos = robos;
    }

    public Integer getBloqueos() {
        return this.bloqueos;
    }

    public EstadisticasBaloncesto bloqueos(Integer bloqueos) {
        this.setBloqueos(bloqueos);
        return this;
    }

    public void setBloqueos(Integer bloqueos) {
        this.bloqueos = bloqueos;
    }

    public BigDecimal getPorcentajeTiro() {
        return this.porcentajeTiro;
    }

    public EstadisticasBaloncesto porcentajeTiro(BigDecimal porcentajeTiro) {
        this.setPorcentajeTiro(porcentajeTiro);
        return this;
    }

    public void setPorcentajeTiro(BigDecimal porcentajeTiro) {
        this.porcentajeTiro = porcentajeTiro;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadisticasBaloncesto)) {
            return false;
        }
        return getId() != null && getId().equals(((EstadisticasBaloncesto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadisticasBaloncesto{" +
            "id=" + getId() +
            ", puntos=" + getPuntos() +
            ", rebotes=" + getRebotes() +
            ", asistencias=" + getAsistencias() +
            ", robos=" + getRobos() +
            ", bloqueos=" + getBloqueos() +
            ", porcentajeTiro=" + getPorcentajeTiro() +
            "}";
    }
}
