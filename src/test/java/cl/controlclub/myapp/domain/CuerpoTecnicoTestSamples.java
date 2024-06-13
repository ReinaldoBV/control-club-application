package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CuerpoTecnicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CuerpoTecnico getCuerpoTecnicoSample1() {
        return new CuerpoTecnico()
            .id(1L)
            .nombres("nombres1")
            .apellidos("apellidos1")
            .rolTecnico("rolTecnico1")
            .telefono("telefono1")
            .email("email1");
    }

    public static CuerpoTecnico getCuerpoTecnicoSample2() {
        return new CuerpoTecnico()
            .id(2L)
            .nombres("nombres2")
            .apellidos("apellidos2")
            .rolTecnico("rolTecnico2")
            .telefono("telefono2")
            .email("email2");
    }

    public static CuerpoTecnico getCuerpoTecnicoRandomSampleGenerator() {
        return new CuerpoTecnico()
            .id(longCount.incrementAndGet())
            .nombres(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .rolTecnico(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
