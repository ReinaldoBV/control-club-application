package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CuerpoTecnico.
 */
@Entity
@Table(name = "cuerpo_tecnico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuerpoTecnico implements Serializable {

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
    @Column(name = "rol_tecnico", nullable = false)
    private String rolTecnico;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnoreProperties(value = { "usuario", "cuerpoTecnico", "directivos" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Asociados asociados;

    @JsonIgnoreProperties(value = { "jugador", "asociados", "directivos", "cuerpoTecnico" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cuerpoTecnico")
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CuerpoTecnico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return this.nombres;
    }

    public CuerpoTecnico nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public CuerpoTecnico apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRolTecnico() {
        return this.rolTecnico;
    }

    public CuerpoTecnico rolTecnico(String rolTecnico) {
        this.setRolTecnico(rolTecnico);
        return this;
    }

    public void setRolTecnico(String rolTecnico) {
        this.rolTecnico = rolTecnico;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public CuerpoTecnico telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public CuerpoTecnico fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getEmail() {
        return this.email;
    }

    public CuerpoTecnico email(String email) {
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

    public CuerpoTecnico asociados(Asociados asociados) {
        this.setAsociados(asociados);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != null) {
            this.usuario.setCuerpoTecnico(null);
        }
        if (usuario != null) {
            usuario.setCuerpoTecnico(this);
        }
        this.usuario = usuario;
    }

    public CuerpoTecnico usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuerpoTecnico)) {
            return false;
        }
        return getId() != null && getId().equals(((CuerpoTecnico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuerpoTecnico{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", rolTecnico='" + getRolTecnico() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
