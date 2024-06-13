package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Partido} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartidoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private String oponente;

    private String resultado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getOponente() {
        return oponente;
    }

    public void setOponente(String oponente) {
        this.oponente = oponente;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartidoDTO)) {
            return false;
        }

        PartidoDTO partidoDTO = (PartidoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partidoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartidoDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", oponente='" + getOponente() + "'" +
            ", resultado='" + getResultado() + "'" +
            "}";
    }
}
