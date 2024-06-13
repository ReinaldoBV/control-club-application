package cl.controlclub.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PartidoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartidoAllPropertiesEquals(Partido expected, Partido actual) {
        assertPartidoAutoGeneratedPropertiesEquals(expected, actual);
        assertPartidoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartidoAllUpdatablePropertiesEquals(Partido expected, Partido actual) {
        assertPartidoUpdatableFieldsEquals(expected, actual);
        assertPartidoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartidoAutoGeneratedPropertiesEquals(Partido expected, Partido actual) {
        assertThat(expected)
            .as("Verify Partido auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartidoUpdatableFieldsEquals(Partido expected, Partido actual) {
        assertThat(expected)
            .as("Verify Partido relevant properties")
            .satisfies(e -> assertThat(e.getFecha()).as("check fecha").isEqualTo(actual.getFecha()))
            .satisfies(e -> assertThat(e.getOponente()).as("check oponente").isEqualTo(actual.getOponente()))
            .satisfies(e -> assertThat(e.getResultado()).as("check resultado").isEqualTo(actual.getResultado()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartidoUpdatableRelationshipsEquals(Partido expected, Partido actual) {}
}