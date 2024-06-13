package cl.controlclub.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Partido.
 */
@Entity
@Table(name = "partido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "oponente", nullable = false)
    private String oponente;

    @Column(name = "resultado")
    private String resultado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Partido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Partido fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getOponente() {
        return this.oponente;
    }

    public Partido oponente(String oponente) {
        this.setOponente(oponente);
        return this;
    }

    public void setOponente(String oponente) {
        this.oponente = oponente;
    }

    public String getResultado() {
        return this.resultado;
    }

    public Partido resultado(String resultado) {
        this.setResultado(resultado);
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partido)) {
            return false;
        }
        return getId() != null && getId().equals(((Partido) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partido{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", oponente='" + getOponente() + "'" +
            ", resultado='" + getResultado() + "'" +
            "}";
    }
}
