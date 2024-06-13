package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class TorneosParticipacionesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTorneosParticipacionesAllPropertiesEquals(TorneosParticipaciones expected, TorneosParticipaciones actual) {
        assertTorneosParticipacionesAutoGeneratedPropertiesEquals(expected, actual);
        assertTorneosParticipacionesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTorneosParticipacionesAllUpdatablePropertiesEquals(
        TorneosParticipaciones expected,
        TorneosParticipaciones actual
    ) {
        assertTorneosParticipacionesUpdatableFieldsEquals(expected, actual);
        assertTorneosParticipacionesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTorneosParticipacionesAutoGeneratedPropertiesEquals(
        TorneosParticipaciones expected,
        TorneosParticipaciones actual
    ) {
        assertThat(expected)
            .as("Verify TorneosParticipaciones auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTorneosParticipacionesUpdatableFieldsEquals(TorneosParticipaciones expected, TorneosParticipaciones actual) {
        assertThat(expected)
            .as("Verify TorneosParticipaciones relevant properties")
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getMonto()).as("check monto").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getMonto()))
            .satisfies(e -> assertThat(e.getFecha()).as("check fecha").isEqualTo(actual.getFecha()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTorneosParticipacionesUpdatableRelationshipsEquals(
        TorneosParticipaciones expected,
        TorneosParticipaciones actual
    ) {}
}
