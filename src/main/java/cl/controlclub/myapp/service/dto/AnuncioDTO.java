package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Anuncio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnuncioDTO implements Serializable {

    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String contenido;

    @NotNull
    private LocalDate fechaPublicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnuncioDTO)) {
            return false;
        }

        AnuncioDTO anuncioDTO = (AnuncioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anuncioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnuncioDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            "}";
    }
}
