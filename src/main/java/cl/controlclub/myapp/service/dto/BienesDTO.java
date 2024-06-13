package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Bienes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BienesDTO implements Serializable {

    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    private Integer cantidad;

    @NotNull
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BienesDTO)) {
            return false;
        }

        BienesDTO bienesDTO = (BienesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bienesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BienesDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad=" + getCantidad() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
