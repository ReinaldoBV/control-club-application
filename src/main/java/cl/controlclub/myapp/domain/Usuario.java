package cl.controlclub.myapp.domain;

import cl.controlclub.myapp.domain.enumeration.RolUsuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @JsonIgnoreProperties(value = { "categorias", "usuario" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Jugador jugador;

    @JsonIgnoreProperties(value = { "directivos", "cuerpoTecnico", "usuario" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Asociados asociados;

    @JsonIgnoreProperties(value = { "usuario", "asociados" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Directivos directivos;

    @JsonIgnoreProperties(value = { "usuario", "asociados" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CuerpoTecnico cuerpoTecnico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public Usuario username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Usuario password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getRol() {
        return this.rol;
    }

    public Usuario rol(RolUsuario rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Usuario jugador(Jugador jugador) {
        this.setJugador(jugador);
        return this;
    }

    public Asociados getAsociados() {
        return this.asociados;
    }

    public void setAsociados(Asociados asociados) {
        this.asociados = asociados;
    }

    public Usuario asociados(Asociados asociados) {
        this.setAsociados(asociados);
        return this;
    }

    public Directivos getDirectivos() {
        return this.directivos;
    }

    public void setDirectivos(Directivos directivos) {
        this.directivos = directivos;
    }

    public Usuario directivos(Directivos directivos) {
        this.setDirectivos(directivos);
        return this;
    }

    public CuerpoTecnico getCuerpoTecnico() {
        return this.cuerpoTecnico;
    }

    public void setCuerpoTecnico(CuerpoTecnico cuerpoTecnico) {
        this.cuerpoTecnico = cuerpoTecnico;
    }

    public Usuario cuerpoTecnico(CuerpoTecnico cuerpoTecnico) {
        this.setCuerpoTecnico(cuerpoTecnico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return getId() != null && getId().equals(((Usuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", rol='" + getRol() + "'" +
            "}";
    }
}
