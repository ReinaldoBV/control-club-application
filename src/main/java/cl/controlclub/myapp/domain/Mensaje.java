package cl.controlclub.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mensaje.
 */
@Entity
@Table(name = "mensaje")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "remitente_id", nullable = false)
    private Long remitenteId;

    @NotNull
    @Column(name = "destinatario_id", nullable = false)
    private Long destinatarioId;

    @NotNull
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "leido")
    private Boolean leido;

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
    private Jugador remitente;

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
    private Jugador destinatario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mensaje id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemitenteId() {
        return this.remitenteId;
    }

    public Mensaje remitenteId(Long remitenteId) {
        this.setRemitenteId(remitenteId);
        return this;
    }

    public void setRemitenteId(Long remitenteId) {
        this.remitenteId = remitenteId;
    }

    public Long getDestinatarioId() {
        return this.destinatarioId;
    }

    public Mensaje destinatarioId(Long destinatarioId) {
        this.setDestinatarioId(destinatarioId);
        return this;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public Mensaje mensaje(String mensaje) {
        this.setMensaje(mensaje);
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeido() {
        return this.leido;
    }

    public Mensaje leido(Boolean leido) {
        this.setLeido(leido);
        return this;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public Jugador getRemitente() {
        return this.remitente;
    }

    public void setRemitente(Jugador jugador) {
        this.remitente = jugador;
    }

    public Mensaje remitente(Jugador jugador) {
        this.setRemitente(jugador);
        return this;
    }

    public Jugador getDestinatario() {
        return this.destinatario;
    }

    public void setDestinatario(Jugador jugador) {
        this.destinatario = jugador;
    }

    public Mensaje destinatario(Jugador jugador) {
        this.setDestinatario(jugador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mensaje)) {
            return false;
        }
        return getId() != null && getId().equals(((Mensaje) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mensaje{" +
            "id=" + getId() +
            ", remitenteId=" + getRemitenteId() +
            ", destinatarioId=" + getDestinatarioId() +
            ", mensaje='" + getMensaje() + "'" +
            ", leido='" + getLeido() + "'" +
            "}";
    }
}
