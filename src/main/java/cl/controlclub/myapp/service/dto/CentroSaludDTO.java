package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.CentroSalud} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroSaludDTO implements Serializable {

    private Long id;

    @NotNull
    private String centroSalud;

    private ComunaDTO comuna;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
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
        if (!(o instanceof CentroSaludDTO)) {
            return false;
        }

        CentroSaludDTO centroSaludDTO = (CentroSaludDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, centroSaludDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroSaludDTO{" +
            "id=" + getId() +
            ", centroSalud='" + getCentroSalud() + "'" +
            ", comuna=" + getComuna() +
            "}";
    }
}
