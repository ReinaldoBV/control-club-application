package cl.controlclub.myapp.service.dto;

import cl.controlclub.myapp.domain.enumeration.EstadoCuenta;
import cl.controlclub.myapp.domain.enumeration.TipoCuenta;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Cuentas} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuentasDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoCuenta tipo;

    @NotNull
    private String descripcion;

    @NotNull
    private BigDecimal monto;

    @NotNull
    private Long nroCuotas;

    @NotNull
    private LocalDate fechaVencimiento;

    @NotNull
    private EstadoCuenta estado;

    private JugadorDTO jugador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getNroCuotas() {
        return nroCuotas;
    }

    public void setNroCuotas(Long nroCuotas) {
        this.nroCuotas = nroCuotas;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        this.estado = estado;
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
        if (!(o instanceof CuentasDTO)) {
            return false;
        }

        CuentasDTO cuentasDTO = (CuentasDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cuentasDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuentasDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", monto=" + getMonto() +
            ", nroCuotas=" + getNroCuotas() +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", estado='" + getEstado() + "'" +
            ", jugador=" + getJugador() +
            "}";
    }
}
