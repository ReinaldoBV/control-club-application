package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AsociadosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Asociados getAsociadosSample1() {
        return new Asociados().id(1L).nombres("nombres1").apellidos("apellidos1").cargo("cargo1").telefono("telefono1").email("email1");
    }

    public static Asociados getAsociadosSample2() {
        return new Asociados().id(2L).nombres("nombres2").apellidos("apellidos2").cargo("cargo2").telefono("telefono2").email("email2");
    }

    public static Asociados getAsociadosRandomSampleGenerator() {
        return new Asociados()
            .id(longCount.incrementAndGet())
            .nombres(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .cargo(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
