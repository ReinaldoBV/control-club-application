package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CentroSaludTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CentroSalud getCentroSaludSample1() {
        return new CentroSalud().id(1L).centroSalud("centroSalud1");
    }

    public static CentroSalud getCentroSaludSample2() {
        return new CentroSalud().id(2L).centroSalud("centroSalud2");
    }

    public static CentroSalud getCentroSaludRandomSampleGenerator() {
        return new CentroSalud().id(longCount.incrementAndGet()).centroSalud(UUID.randomUUID().toString());
    }
}
