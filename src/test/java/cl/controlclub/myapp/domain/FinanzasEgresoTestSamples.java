package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FinanzasEgresoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FinanzasEgreso getFinanzasEgresoSample1() {
        return new FinanzasEgreso().id(1L).descripcion("descripcion1");
    }

    public static FinanzasEgreso getFinanzasEgresoSample2() {
        return new FinanzasEgreso().id(2L).descripcion("descripcion2");
    }

    public static FinanzasEgreso getFinanzasEgresoRandomSampleGenerator() {
        return new FinanzasEgreso().id(longCount.incrementAndGet()).descripcion(UUID.randomUUID().toString());
    }
}
