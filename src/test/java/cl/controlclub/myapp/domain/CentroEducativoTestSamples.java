package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CentroEducativoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CentroEducativo getCentroEducativoSample1() {
        return new CentroEducativo().id(1L).centroEducativo("centroEducativo1");
    }

    public static CentroEducativo getCentroEducativoSample2() {
        return new CentroEducativo().id(2L).centroEducativo("centroEducativo2");
    }

    public static CentroEducativo getCentroEducativoRandomSampleGenerator() {
        return new CentroEducativo().id(longCount.incrementAndGet()).centroEducativo(UUID.randomUUID().toString());
    }
}
