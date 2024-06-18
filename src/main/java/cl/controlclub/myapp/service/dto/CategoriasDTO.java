package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Categorias} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriasDTO implements Serializable {

    private Long id;

    @NotNull
    private Long anioInicio;

    @NotNull
    private Long anioFinal;

    @NotNull
    private String nombreCategoria;

    private JugadorDTO jugador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnioInicio() {
        return anioInicio;
    }

    public void setAnioInicio(Long anioInicio) {
        this.anioInicio = anioInicio;
    }

    public Long getAnioFinal() {
        return anioFinal;
    }

    public void setAnioFinal(Long anioFinal) {
        this.anioFinal = anioFinal;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public JugadorDTO getJugador() {
        return jugador;
    }

    public void setJugador(JugadorDTO jugador) {
        this.jugador = jugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriasDTO)) {
            return false;
        }

        CategoriasDTO categoriasDTO = (CategoriasDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoriasDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriasDTO{" +
            "id=" + getId() +
            ", anioInicio=" + getAnioInicio() +
            ", anioFinal=" + getAnioFinal() +
            ", nombreCategoria='" + getNombreCategoria() + "'" +
            ", jugador=" + getJugador() +
            "}";
    }
}
