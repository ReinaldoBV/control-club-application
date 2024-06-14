package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CentroEducativo.
 */
@Entity
@Table(name = "centro_educativo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroEducativo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "centro_educativo", nullable = false)
    private String centroEducativo;

    @JsonIgnoreProperties(value = { "centroSalud", "previsionSalud", "centroEducativo", "categorias", "usuario" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "centroEducativo")
    private Jugador jugador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CentroEducativo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCentroEducativo() {
        return this.centroEducativo;
    }

    public CentroEducativo centroEducativo(String centroEducativo) {
        this.setCentroEducativo(centroEducativo);
        return this;
    }

    public void setCentroEducativo(String centroEducativo) {
        this.centroEducativo = centroEducativo;
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        if (this.jugador != null) {
            this.jugador.setCentroEducativo(null);
        }
        if (jugador != null) {
            jugador.setCentroEducativo(this);
        }
        this.jugador = jugador;
    }

    public CentroEducativo jugador(Jugador jugador) {
        this.setJugador(jugador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroEducativo)) {
            return false;
        }
        return getId() != null && getId().equals(((CentroEducativo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroEducativo{" +
            "id=" + getId() +
            ", centroEducativo='" + getCentroEducativo() + "'" +
            "}";
    }
}
