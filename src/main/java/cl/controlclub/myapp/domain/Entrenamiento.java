package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entrenamiento.
 */
@Entity
@Table(name = "entrenamiento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entrenamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private LocalDate fechaHora;

    @NotNull
    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "asociados", "usuario", "entrenamientos" }, allowSetters = true)
    private CuerpoTecnico cuerpoTecnico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entrenamiento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaHora() {
        return this.fechaHora;
    }

    public Entrenamiento fechaHora(LocalDate fechaHora) {
        this.setFechaHora(fechaHora);
        return this;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getDuracion() {
        return this.duracion;
    }

    public Entrenamiento duracion(Integer duracion) {
        this.setDuracion(duracion);
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public CuerpoTecnico getCuerpoTecnico() {
        return this.cuerpoTecnico;
    }

    public void setCuerpoTecnico(CuerpoTecnico cuerpoTecnico) {
        this.cuerpoTecnico = cuerpoTecnico;
    }

    public Entrenamiento cuerpoTecnico(CuerpoTecnico cuerpoTecnico) {
        this.setCuerpoTecnico(cuerpoTecnico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entrenamiento)) {
            return false;
        }
        return getId() != null && getId().equals(((Entrenamiento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entrenamiento{" +
            "id=" + getId() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", duracion=" + getDuracion() +
            "}";
    }
}
