package cl.controlclub.myapp.domain;

import cl.controlclub.myapp.domain.enumeration.TipoAsistencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Asistencia.
 */
@Entity
@Table(name = "asistencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Asistencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAsistencia tipo;

    @NotNull
    @Column(name = "id_evento", nullable = false)
    private Long idEvento;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "asistencia", nullable = false)
    private Boolean asistencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asistencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAsistencia getTipo() {
        return this.tipo;
    }

    public Asistencia tipo(TipoAsistencia tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoAsistencia tipo) {
        this.tipo = tipo;
    }

    public Long getIdEvento() {
        return this.idEvento;
    }

    public Asistencia idEvento(Long idEvento) {
        this.setIdEvento(idEvento);
        return this;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Asistencia fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getAsistencia() {
        return this.asistencia;
    }

    public Asistencia asistencia(Boolean asistencia) {
        this.setAsistencia(asistencia);
        return this;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asistencia)) {
            return false;
        }
        return getId() != null && getId().equals(((Asistencia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asistencia{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", idEvento=" + getIdEvento() +
            ", fecha='" + getFecha() + "'" +
            ", asistencia='" + getAsistencia() + "'" +
            "}";
    }
}
