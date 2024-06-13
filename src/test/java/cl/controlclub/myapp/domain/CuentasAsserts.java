package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class CuentasAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCuentasAllPropertiesEquals(Cuentas expected, Cuentas actual) {
        assertCuentasAutoGeneratedPropertiesEquals(expected, actual);
        assertCuentasAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCuentasAllUpdatablePropertiesEquals(Cuentas expected, Cuentas actual) {
        assertCuentasUpdatableFieldsEquals(expected, actual);
        assertCuentasUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCuentasAutoGeneratedPropertiesEquals(Cuentas expected, Cuentas actual) {
        assertThat(expected)
            .as("Verify Cuentas auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCuentasUpdatableFieldsEquals(Cuentas expected, Cuentas actual) {
        assertThat(expected)
            .as("Verify Cuentas relevant properties")
            .satisfies(e -> assertThat(e.getTipo()).as("check tipo").isEqualTo(actual.getTipo()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getMonto()).as("check monto").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getMonto()))
            .satisfies(e -> assertThat(e.getNroCuotas()).as("check nroCuotas").isEqualTo(actual.getNroCuotas()))
            .satisfies(e -> assertThat(e.getFechaVencimiento()).as("check fechaVencimiento").isEqualTo(actual.getFechaVencimiento()))
            .satisfies(e -> assertThat(e.getEstado()).as("check estado").isEqualTo(actual.getEstado()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCuentasUpdatableRelationshipsEquals(Cuentas expected, Cuentas actual) {}
}
