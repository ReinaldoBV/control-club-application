package cl.controlclub.myapp.domain;

import cl.controlclub.myapp.domain.enumeration.TipoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FinanzasEgreso.
 */
@Entity
@Table(name = "finanzas_egreso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FinanzasEgreso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPago tipo;

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

    public FinanzasEgreso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPago getTipo() {
        return this.tipo;
    }

    public FinanzasEgreso tipo(TipoPago tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoPago tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public FinanzasEgreso descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return this.monto;
    }

    public FinanzasEgreso monto(BigDecimal monto) {
        this.setMonto(monto);
        return this;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public FinanzasEgreso fecha(LocalDate fecha) {
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
        if (!(o instanceof FinanzasEgreso)) {
            return false;
        }
        return getId() != null && getId().equals(((FinanzasEgreso) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FinanzasEgreso{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", monto=" + getMonto() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
