package cl.controlclub.myapp.service.dto;

import cl.controlclub.myapp.domain.enumeration.TipoPrevision;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.PrevisionSalud} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrevisionSaludDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoPrevision tipoPrevision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPrevision getTipoPrevision() {
        return tipoPrevision;
    }

    public void setTipoPrevision(TipoPrevision tipoPrevision) {
        this.tipoPrevision = tipoPrevision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrevisionSaludDTO)) {
            return false;
        }

        PrevisionSaludDTO previsionSaludDTO = (PrevisionSaludDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, previsionSaludDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrevisionSaludDTO{" +
            "id=" + getId() +
            ", tipoPrevision='" + getTipoPrevision() + "'" +
            "}";
    }
}
