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
 * A Categorias.
 */
@Entity
@Table(name = "categorias")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categorias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "anio_inicio", nullable = false)
    private Long anioInicio;

    @NotNull
    @Column(name = "anio_final", nullable = false)
    private Long anioFinal;

    @NotNull
    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuario", "categoria" }, allowSetters = true)
    private Set<Jugador> jugadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Categorias id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnioInicio() {
        return this.anioInicio;
    }

    public Categorias anioInicio(Long anioInicio) {
        this.setAnioInicio(anioInicio);
        return this;
    }

    public void setAnioInicio(Long anioInicio) {
        this.anioInicio = anioInicio;
    }

    public Long getAnioFinal() {
        return this.anioFinal;
    }

    public Categorias anioFinal(Long anioFinal) {
        this.setAnioFinal(anioFinal);
        return this;
    }

    public void setAnioFinal(Long anioFinal) {
        this.anioFinal = anioFinal;
    }

    public String getNombreCategoria() {
        return this.nombreCategoria;
    }

    public Categorias nombreCategoria(String nombreCategoria) {
        this.setNombreCategoria(nombreCategoria);
        return this;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Set<Jugador> getJugadors() {
        return this.jugadors;
    }

    public void setJugadors(Set<Jugador> jugadors) {
        if (this.jugadors != null) {
            this.jugadors.forEach(i -> i.setCategoria(null));
        }
        if (jugadors != null) {
            jugadors.forEach(i -> i.setCategoria(this));
        }
        this.jugadors = jugadors;
    }

    public Categorias jugadors(Set<Jugador> jugadors) {
        this.setJugadors(jugadors);
        return this;
    }

    public Categorias addJugador(Jugador jugador) {
        this.jugadors.add(jugador);
        jugador.setCategoria(this);
        return this;
    }

    public Categorias removeJugador(Jugador jugador) {
        this.jugadors.remove(jugador);
        jugador.setCategoria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorias)) {
            return false;
        }
        return getId() != null && getId().equals(((Categorias) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorias{" +
            "id=" + getId() +
            ", anioInicio=" + getAnioInicio() +
            ", anioFinal=" + getAnioFinal() +
            ", nombreCategoria='" + getNombreCategoria() + "'" +
            "}";
    }
}
