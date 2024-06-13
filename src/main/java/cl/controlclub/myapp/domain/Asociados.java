package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Asociados.
 */
@Entity
@Table(name = "asociados")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Asociados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombres", nullable = false)
    private String nombres;

    @NotNull
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @NotNull
    @Column(name = "cargo", nullable = false)
    private String cargo;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "fecha_asoc", nullable = false)
    private LocalDate fechaAsoc;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "centroSalud",
            "previsionSalud",
            "comuna",
            "centroEducativo",
            "categorias",
            "usuario",
            "finanzasIngresos",
            "cuentas",
            "padres",
            "asociados",
        },
        allowSetters = true
    )
    private Jugador jugador;

    @JsonIgnoreProperties(value = { "jugador", "asociados", "directivos", "cuerpoTecnico" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "asociados")
    private Usuario usuario;

    @JsonIgnoreProperties(value = { "asociados", "usuario" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "asociados")
    private Directivos directivos;

    @JsonIgnoreProperties(value = { "asociados", "usuario", "entrenamientos" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "asociados")
    private CuerpoTecnico cuerpoTecnico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asociados id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return this.nombres;
    }

    public Asociados nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Asociados apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return this.cargo;
    }

    public Asociados cargo(String cargo) {
        this.setCargo(cargo);
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Asociados telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAsoc() {
        return this.fechaAsoc;
    }

    public Asociados fechaAsoc(LocalDate fechaAsoc) {
        this.setFechaAsoc(fechaAsoc);
        return this;
    }

    public void setFechaAsoc(LocalDate fechaAsoc) {
        this.fechaAsoc = fechaAsoc;
    }

    public String getEmail() {
        return this.email;
    }

    public Asociados email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Asociados jugador(Jugador jugador) {
        this.setJugador(jugador);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != null) {
            this.usuario.setAsociados(null);
        }
        if (usuario != null) {
            usuario.setAsociados(this);
        }
        this.usuario = usuario;
    }

    public Asociados usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public Directivos getDirectivos() {
        return this.directivos;
    }

    public void setDirectivos(Directivos directivos) {
        if (this.directivos != null) {
            this.directivos.setAsociados(null);
        }
        if (directivos != null) {
            directivos.setAsociados(this);
        }
        this.directivos = directivos;
    }

    public Asociados directivos(Directivos directivos) {
        this.setDirectivos(directivos);
        return this;
    }

    public CuerpoTecnico getCuerpoTecnico() {
        return this.cuerpoTecnico;
    }

    public void setCuerpoTecnico(CuerpoTecnico cuerpoTecnico) {
        if (this.cuerpoTecnico != null) {
            this.cuerpoTecnico.setAsociados(null);
        }
        if (cuerpoTecnico != null) {
            cuerpoTecnico.setAsociados(this);
        }
        this.cuerpoTecnico = cuerpoTecnico;
    }

    public Asociados cuerpoTecnico(CuerpoTecnico cuerpoTecnico) {
        this.setCuerpoTecnico(cuerpoTecnico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asociados)) {
            return false;
        }
        return getId() != null && getId().equals(((Asociados) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asociados{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaAsoc='" + getFechaAsoc() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
