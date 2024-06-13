package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
        value = {
            "centroSalud",
            "previsionSalud",
            "comuna",
            "centroEducativo",
            "categorias",
            "usuario",
            "finanzasIngresos",
            "cuentas",
            "padres",
            "asociados",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "comuna")
    private Jugador jugador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comuna")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comuna", "jugador" }, allowSetters = true)
    private Set<CentroSalud> centroSaluds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comuna")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comuna", "jugador" }, allowSetters = true)
    private Set<CentroEducativo> centroEducativos = new HashSet<>();

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

    public Set<CentroSalud> getCentroSaluds() {
        return this.centroSaluds;
    }

    public void setCentroSaluds(Set<CentroSalud> centroSaluds) {
        if (this.centroSaluds != null) {
            this.centroSaluds.forEach(i -> i.setComuna(null));
        }
        if (centroSaluds != null) {
            centroSaluds.forEach(i -> i.setComuna(this));
        }
        this.centroSaluds = centroSaluds;
    }

    public Comuna centroSaluds(Set<CentroSalud> centroSaluds) {
        this.setCentroSaluds(centroSaluds);
        return this;
    }

    public Comuna addCentroSalud(CentroSalud centroSalud) {
        this.centroSaluds.add(centroSalud);
        centroSalud.setComuna(this);
        return this;
    }

    public Comuna removeCentroSalud(CentroSalud centroSalud) {
        this.centroSaluds.remove(centroSalud);
        centroSalud.setComuna(null);
        return this;
    }

    public Set<CentroEducativo> getCentroEducativos() {
        return this.centroEducativos;
    }

    public void setCentroEducativos(Set<CentroEducativo> centroEducativos) {
        if (this.centroEducativos != null) {
            this.centroEducativos.forEach(i -> i.setComuna(null));
        }
        if (centroEducativos != null) {
            centroEducativos.forEach(i -> i.setComuna(this));
        }
        this.centroEducativos = centroEducativos;
    }

    public Comuna centroEducativos(Set<CentroEducativo> centroEducativos) {
        this.setCentroEducativos(centroEducativos);
        return this;
    }

    public Comuna addCentroEducativo(CentroEducativo centroEducativo) {
        this.centroEducativos.add(centroEducativo);
        centroEducativo.setComuna(this);
        return this;
    }

    public Comuna removeCentroEducativo(CentroEducativo centroEducativo) {
        this.centroEducativos.remove(centroEducativo);
        centroEducativo.setComuna(null);
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
