package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ComunaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Comuna getComunaSample1() {
        return new Comuna().id(1L);
    }

    public static Comuna getComunaSample2() {
        return new Comuna().id(2L);
    }

    public static Comuna getComunaRandomSampleGenerator() {
        return new Comuna().id(longCount.incrementAndGet());
    }
}
