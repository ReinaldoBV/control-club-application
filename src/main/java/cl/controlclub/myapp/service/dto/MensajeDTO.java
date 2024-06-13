package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Mensaje} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MensajeDTO implements Serializable {

    private Long id;

    @NotNull
    private Long remitenteId;

    @NotNull
    private Long destinatarioId;

    @NotNull
    private String mensaje;

    private Boolean leido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(Long remitenteId) {
        this.remitenteId = remitenteId;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MensajeDTO)) {
            return false;
        }

        MensajeDTO mensajeDTO = (MensajeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mensajeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MensajeDTO{" +
            "id=" + getId() +
            ", remitenteId=" + getRemitenteId() +
            ", destinatarioId=" + getDestinatarioId() +
            ", mensaje='" + getMensaje() + "'" +
            ", leido='" + getLeido() + "'" +
            "}";
    }
}
