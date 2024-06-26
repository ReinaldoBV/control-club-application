package cl.controlclub.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AsociadosAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAsociadosAllPropertiesEquals(Asociados expected, Asociados actual) {
        assertAsociadosAutoGeneratedPropertiesEquals(expected, actual);
        assertAsociadosAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAsociadosAllUpdatablePropertiesEquals(Asociados expected, Asociados actual) {
        assertAsociadosUpdatableFieldsEquals(expected, actual);
        assertAsociadosUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAsociadosAutoGeneratedPropertiesEquals(Asociados expected, Asociados actual) {
        assertThat(expected)
            .as("Verify Asociados auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAsociadosUpdatableFieldsEquals(Asociados expected, Asociados actual) {
        assertThat(expected)
            .as("Verify Asociados relevant properties")
            .satisfies(e -> assertThat(e.getNombres()).as("check nombres").isEqualTo(actual.getNombres()))
            .satisfies(e -> assertThat(e.getApellidos()).as("check apellidos").isEqualTo(actual.getApellidos()))
            .satisfies(e -> assertThat(e.getCargo()).as("check cargo").isEqualTo(actual.getCargo()))
            .satisfies(e -> assertThat(e.getTelefono()).as("check telefono").isEqualTo(actual.getTelefono()))
            .satisfies(e -> assertThat(e.getFechaAsoc()).as("check fechaAsoc").isEqualTo(actual.getFechaAsoc()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAsociadosUpdatableRelationshipsEquals(Asociados expected, Asociados actual) {}
}
