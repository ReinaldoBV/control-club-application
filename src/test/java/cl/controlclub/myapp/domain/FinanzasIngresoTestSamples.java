package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FinanzasIngresoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FinanzasIngreso getFinanzasIngresoSample1() {
        return new FinanzasIngreso().id(1L).descripcion("descripcion1");
    }

    public static FinanzasIngreso getFinanzasIngresoSample2() {
        return new FinanzasIngreso().id(2L).descripcion("descripcion2");
    }

    public static FinanzasIngreso getFinanzasIngresoRandomSampleGenerator() {
        return new FinanzasIngreso().id(longCount.incrementAndGet()).descripcion(UUID.randomUUID().toString());
    }
}
