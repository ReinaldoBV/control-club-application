package cl.controlclub.myapp.service.dto;

import cl.controlclub.myapp.domain.enumeration.RolUsuario;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Usuario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private RolUsuario rol;

    private JugadorDTO jugador;

    private AsociadosDTO asociados;

    private DirectivosDTO directivos;

    private CuerpoTecnicoDTO cuerpoTecnico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public JugadorDTO getJugador() {
        return jugador;
    }

    public void setJugador(JugadorDTO jugador) {
        this.jugador = jugador;
    }

    public AsociadosDTO getAsociados() {
        return asociados;
    }

    public void setAsociados(AsociadosDTO asociados) {
        this.asociados = asociados;
    }

    public DirectivosDTO getDirectivos() {
        return directivos;
    }

    public void setDirectivos(DirectivosDTO directivos) {
        this.directivos = directivos;
    }

    public CuerpoTecnicoDTO getCuerpoTecnico() {
        return cuerpoTecnico;
    }

    public void setCuerpoTecnico(CuerpoTecnicoDTO cuerpoTecnico) {
        this.cuerpoTecnico = cuerpoTecnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioDTO)) {
            return false;
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usuarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", rol='" + getRol() + "'" +
            ", jugador=" + getJugador() +
            ", asociados=" + getAsociados() +
            ", directivos=" + getDirectivos() +
            ", cuerpoTecnico=" + getCuerpoTecnico() +
            "}";
    }
}
