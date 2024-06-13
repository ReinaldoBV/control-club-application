package cl.controlclub.myapp.service.dto;

import cl.controlclub.myapp.domain.enumeration.RMComuna;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Comuna} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComunaDTO implements Serializable {

    private Long id;

    @NotNull
    private RMComuna comuna;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RMComuna getComuna() {
        return comuna;
    }

    public void setComuna(RMComuna comuna) {
        this.comuna = comuna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComunaDTO)) {
            return false;
        }

        ComunaDTO comunaDTO = (ComunaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, comunaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComunaDTO{" +
            "id=" + getId() +
            ", comuna='" + getComuna() + "'" +
            "}";
    }
}
