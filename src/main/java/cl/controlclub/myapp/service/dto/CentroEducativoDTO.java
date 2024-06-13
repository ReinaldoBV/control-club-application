package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.CentroEducativo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroEducativoDTO implements Serializable {

    private Long id;

    @NotNull
    private String centroEducativo;

    private ComunaDTO comuna;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCentroEducativo() {
        return centroEducativo;
    }

    public void setCentroEducativo(String centroEducativo) {
        this.centroEducativo = centroEducativo;
    }

    public ComunaDTO getComuna() {
        return comuna;
    }

    public void setComuna(ComunaDTO comuna) {
        this.comuna = comuna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroEducativoDTO)) {
            return false;
        }

        CentroEducativoDTO centroEducativoDTO = (CentroEducativoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, centroEducativoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroEducativoDTO{" +
            "id=" + getId() +
            ", centroEducativo='" + getCentroEducativo() + "'" +
            ", comuna=" + getComuna() +
            "}";
    }
}
