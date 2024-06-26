package cl.controlclub.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MensajeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMensajeAllPropertiesEquals(Mensaje expected, Mensaje actual) {
        assertMensajeAutoGeneratedPropertiesEquals(expected, actual);
        assertMensajeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMensajeAllUpdatablePropertiesEquals(Mensaje expected, Mensaje actual) {
        assertMensajeUpdatableFieldsEquals(expected, actual);
        assertMensajeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMensajeAutoGeneratedPropertiesEquals(Mensaje expected, Mensaje actual) {
        assertThat(expected)
            .as("Verify Mensaje auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMensajeUpdatableFieldsEquals(Mensaje expected, Mensaje actual) {
        assertThat(expected)
            .as("Verify Mensaje relevant properties")
            .satisfies(e -> assertThat(e.getRemitenteId()).as("check remitenteId").isEqualTo(actual.getRemitenteId()))
            .satisfies(e -> assertThat(e.getDestinatarioId()).as("check destinatarioId").isEqualTo(actual.getDestinatarioId()))
            .satisfies(e -> assertThat(e.getMensaje()).as("check mensaje").isEqualTo(actual.getMensaje()))
            .satisfies(e -> assertThat(e.getLeido()).as("check leido").isEqualTo(actual.getLeido()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMensajeUpdatableRelationshipsEquals(Mensaje expected, Mensaje actual) {}
}
