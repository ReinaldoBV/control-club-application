package cl.controlclub.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TorneosParticipaciones.
 */
@Entity
@Table(name = "torneos_participaciones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TorneosParticipaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "monto", precision = 21, scale = 2, nullable = false)
    private BigDecimal monto;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TorneosParticipaciones id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public TorneosParticipaciones descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return this.monto;
    }

    public TorneosParticipaciones monto(BigDecimal monto) {
        this.setMonto(monto);
        return this;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public TorneosParticipaciones fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TorneosParticipaciones)) {
            return false;
        }
        return getId() != null && getId().equals(((TorneosParticipaciones) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TorneosParticipaciones{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", monto=" + getMonto() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
