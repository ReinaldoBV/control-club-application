package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BienesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Bienes getBienesSample1() {
        return new Bienes().id(1L).descripcion("descripcion1").cantidad(1).estado("estado1");
    }

    public static Bienes getBienesSample2() {
        return new Bienes().id(2L).descripcion("descripcion2").cantidad(2).estado("estado2");
    }

    public static Bienes getBienesRandomSampleGenerator() {
        return new Bienes()
            .id(longCount.incrementAndGet())
            .descripcion(UUID.randomUUID().toString())
            .cantidad(intCount.incrementAndGet())
            .estado(UUID.randomUUID().toString());
    }
}
