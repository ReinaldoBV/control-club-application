package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comuna.
 */
@Entity
@Table(name = "comuna")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "comuna", nullable = false)
    private String comuna;

    @JsonIgnoreProperties(
        value = { "centroSalud", "previsionSalud", "comuna", "centroEducativo", "categorias", "usuario" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "comuna")
    private Jugador jugador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comuna id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComuna() {
        return this.comuna;
    }

    public Comuna comuna(String comuna) {
        this.setComuna(comuna);
        return this;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        if (this.jugador != null) {
            this.jugador.setComuna(null);
        }
        if (jugador != null) {
            jugador.setComuna(this);
        }
        this.jugador = jugador;
    }

    public Comuna jugador(Jugador jugador) {
        this.setJugador(jugador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comuna)) {
            return false;
        }
        return getId() != null && getId().equals(((Comuna) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comuna{" +
            "id=" + getId() +
            ", comuna='" + getComuna() + "'" +
            "}";
    }
}
