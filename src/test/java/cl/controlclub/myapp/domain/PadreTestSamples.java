package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PadreTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Padre getPadreSample1() {
        return new Padre().id(1L).nombres("nombres1").apellidos("apellidos1").relacion("relacion1").telefono("telefono1").email("email1");
    }

    public static Padre getPadreSample2() {
        return new Padre().id(2L).nombres("nombres2").apellidos("apellidos2").relacion("relacion2").telefono("telefono2").email("email2");
    }

    public static Padre getPadreRandomSampleGenerator() {
        return new Padre()
            .id(longCount.incrementAndGet())
            .nombres(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .relacion(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
