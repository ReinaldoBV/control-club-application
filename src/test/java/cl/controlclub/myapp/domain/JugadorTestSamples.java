package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JugadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Jugador getJugadorSample1() {
        return new Jugador()
            .id(1L)
            .idJugador(1L)
            .nroIdentificacion(1L)
            .nombres("nombres1")
            .apellidos("apellidos1")
            .edad(1)
            .numeroCamisa(1)
            .contactoEmergencia("contactoEmergencia1")
            .calleAvenidaDireccion("calleAvenidaDireccion1")
            .numeroDireccion(1L)
            .numeroPersonal(1L);
    }

    public static Jugador getJugadorSample2() {
        return new Jugador()
            .id(2L)
            .idJugador(2L)
            .nroIdentificacion(2L)
            .nombres("nombres2")
            .apellidos("apellidos2")
            .edad(2)
            .numeroCamisa(2)
            .contactoEmergencia("contactoEmergencia2")
            .calleAvenidaDireccion("calleAvenidaDireccion2")
            .numeroDireccion(2L)
            .numeroPersonal(2L);
    }

    public static Jugador getJugadorRandomSampleGenerator() {
        return new Jugador()
            .id(longCount.incrementAndGet())
            .idJugador(longCount.incrementAndGet())
            .nroIdentificacion(longCount.incrementAndGet())
            .nombres(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .edad(intCount.incrementAndGet())
            .numeroCamisa(intCount.incrementAndGet())
            .contactoEmergencia(UUID.randomUUID().toString())
            .calleAvenidaDireccion(UUID.randomUUID().toString())
            .numeroDireccion(longCount.incrementAndGet())
            .numeroPersonal(longCount.incrementAndGet());
    }
}
