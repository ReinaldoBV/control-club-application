package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CentroSalud.
 */
@Entity
@Table(name = "centro_salud")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "centro_salud", nullable = false)
    private String centroSalud;

    @JsonIgnoreProperties(value = { "centroSalud", "previsionSalud", "centroEducativo", "categorias", "usuario" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "centroSalud")
    private Jugador jugador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CentroSalud id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCentroSalud() {
        return this.centroSalud;
    }

    public CentroSalud centroSalud(String centroSalud) {
        this.setCentroSalud(centroSalud);
        return this;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        if (this.jugador != null) {
            this.jugador.setCentroSalud(null);
        }
        if (jugador != null) {
            jugador.setCentroSalud(this);
        }
        this.jugador = jugador;
    }

    public CentroSalud jugador(Jugador jugador) {
        this.setJugador(jugador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroSalud)) {
            return false;
        }
        return getId() != null && getId().equals(((CentroSalud) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroSalud{" +
            "id=" + getId() +
            ", centroSalud='" + getCentroSalud() + "'" +
            "}";
    }
}
