package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Club} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClubDTO implements Serializable {

    private Long id;

    @NotNull
    private String razon;

    @NotNull
    private String direccion;

    @NotNull
    private String telefono;

    @NotNull
    private String email;

    @NotNull
    private LocalDate fechaFundacion;

    @NotNull
    private String presidente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClubDTO)) {
            return false;
        }

        ClubDTO clubDTO = (ClubDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clubDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClubDTO{" +
            "id=" + getId() +
            ", razon='" + getRazon() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", fechaFundacion='" + getFechaFundacion() + "'" +
            ", presidente='" + getPresidente() + "'" +
            "}";
    }
}
