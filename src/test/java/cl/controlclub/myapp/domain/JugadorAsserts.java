package cl.controlclub.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class JugadorAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJugadorAllPropertiesEquals(Jugador expected, Jugador actual) {
        assertJugadorAutoGeneratedPropertiesEquals(expected, actual);
        assertJugadorAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJugadorAllUpdatablePropertiesEquals(Jugador expected, Jugador actual) {
        assertJugadorUpdatableFieldsEquals(expected, actual);
        assertJugadorUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJugadorAutoGeneratedPropertiesEquals(Jugador expected, Jugador actual) {
        assertThat(expected)
            .as("Verify Jugador auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJugadorUpdatableFieldsEquals(Jugador expected, Jugador actual) {
        assertThat(expected)
            .as("Verify Jugador relevant properties")
            .satisfies(e -> assertThat(e.getIdJugador()).as("check idJugador").isEqualTo(actual.getIdJugador()))
            .satisfies(e -> assertThat(e.getNroIdentificacion()).as("check nroIdentificacion").isEqualTo(actual.getNroIdentificacion()))
            .satisfies(e -> assertThat(e.getTipoIdentificacion()).as("check tipoIdentificacion").isEqualTo(actual.getTipoIdentificacion()))
            .satisfies(e -> assertThat(e.getNombres()).as("check nombres").isEqualTo(actual.getNombres()))
            .satisfies(e -> assertThat(e.getApellidos()).as("check apellidos").isEqualTo(actual.getApellidos()))
            .satisfies(e -> assertThat(e.getNacionalidad()).as("check nacionalidad").isEqualTo(actual.getNacionalidad()))
            .satisfies(e -> assertThat(e.getEdad()).as("check edad").isEqualTo(actual.getEdad()))
            .satisfies(e -> assertThat(e.getFechaNacimiento()).as("check fechaNacimiento").isEqualTo(actual.getFechaNacimiento()))
            .satisfies(e -> assertThat(e.getNumeroCamisa()).as("check numeroCamisa").isEqualTo(actual.getNumeroCamisa()))
            .satisfies(e -> assertThat(e.getContactoEmergencia()).as("check contactoEmergencia").isEqualTo(actual.getContactoEmergencia()))
            .satisfies(
                e -> assertThat(e.getCalleAvenidaDireccion()).as("check calleAvenidaDireccion").isEqualTo(actual.getCalleAvenidaDireccion())
            )
            .satisfies(e -> assertThat(e.getNumeroDireccion()).as("check numeroDireccion").isEqualTo(actual.getNumeroDireccion()))
            .satisfies(e -> assertThat(e.getNumeroPersonal()).as("check numeroPersonal").isEqualTo(actual.getNumeroPersonal()))
            .satisfies(e -> assertThat(e.getImagenJugador()).as("check imagenJugador").isEqualTo(actual.getImagenJugador()))
            .satisfies(
                e ->
                    assertThat(e.getImagenJugadorContentType())
                        .as("check imagenJugador contenty type")
                        .isEqualTo(actual.getImagenJugadorContentType())
            )
            .satisfies(
                e ->
                    assertThat(e.getDocumentoIdentificacion())
                        .as("check documentoIdentificacion")
                        .isEqualTo(actual.getDocumentoIdentificacion())
            )
            .satisfies(
                e ->
                    assertThat(e.getDocumentoIdentificacionContentType())
                        .as("check documentoIdentificacion contenty type")
                        .isEqualTo(actual.getDocumentoIdentificacionContentType())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJugadorUpdatableRelationshipsEquals(Jugador expected, Jugador actual) {
        assertThat(expected)
            .as("Verify Jugador relationships")
            .satisfies(e -> assertThat(e.getCategoria()).as("check categoria").isEqualTo(actual.getCategoria()));
    }
}
