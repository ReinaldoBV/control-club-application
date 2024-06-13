package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Directivos.
 */
@Entity
@Table(name = "directivos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Directivos implements Serializable {

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
    @Column(name = "fecha_eleccion", nullable = false)
    private LocalDate fechaEleccion;

    @NotNull
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnoreProperties(value = { "jugador", "usuario", "directivos", "cuerpoTecnico" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Asociados asociados;

    @JsonIgnoreProperties(value = { "jugador", "asociados", "directivos", "cuerpoTecnico" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "directivos")
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Directivos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return this.nombres;
    }

    public Directivos nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Directivos apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return this.cargo;
    }

    public Directivos cargo(String cargo) {
        this.setCargo(cargo);
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Directivos telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaEleccion() {
        return this.fechaEleccion;
    }

    public Directivos fechaEleccion(LocalDate fechaEleccion) {
        this.setFechaEleccion(fechaEleccion);
        return this;
    }

    public void setFechaEleccion(LocalDate fechaEleccion) {
        this.fechaEleccion = fechaEleccion;
    }

    public LocalDate getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public Directivos fechaVencimiento(LocalDate fechaVencimiento) {
        this.setFechaVencimiento(fechaVencimiento);
        return this;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEmail() {
        return this.email;
    }

    public Directivos email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Asociados getAsociados() {
        return this.asociados;
    }

    public void setAsociados(Asociados asociados) {
        this.asociados = asociados;
    }

    public Directivos asociados(Asociados asociados) {
        this.setAsociados(asociados);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != null) {
            this.usuario.setDirectivos(null);
        }
        if (usuario != null) {
            usuario.setDirectivos(this);
        }
        this.usuario = usuario;
    }

    public Directivos usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Directivos)) {
            return false;
        }
        return getId() != null && getId().equals(((Directivos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Directivos{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaEleccion='" + getFechaEleccion() + "'" +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
