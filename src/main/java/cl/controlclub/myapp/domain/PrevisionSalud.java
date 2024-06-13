package cl.controlclub.myapp.domain;

import cl.controlclub.myapp.domain.enumeration.TipoPrevision;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PrevisionSalud.
 */
@Entity
@Table(name = "prevision_salud")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrevisionSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_prevision", nullable = false)
    private TipoPrevision tipoPrevision;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PrevisionSalud id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPrevision getTipoPrevision() {
        return this.tipoPrevision;
    }

    public PrevisionSalud tipoPrevision(TipoPrevision tipoPrevision) {
        this.setTipoPrevision(tipoPrevision);
        return this;
    }

    public void setTipoPrevision(TipoPrevision tipoPrevision) {
        this.tipoPrevision = tipoPrevision;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrevisionSalud)) {
            return false;
        }
        return getId() != null && getId().equals(((PrevisionSalud) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrevisionSalud{" +
            "id=" + getId() +
            ", tipoPrevision='" + getTipoPrevision() + "'" +
            "}";
    }
}
