package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PrevisionSaludTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PrevisionSalud getPrevisionSaludSample1() {
        return new PrevisionSalud().id(1L);
    }

    public static PrevisionSalud getPrevisionSaludSample2() {
        return new PrevisionSalud().id(2L);
    }

    public static PrevisionSalud getPrevisionSaludRandomSampleGenerator() {
        return new PrevisionSalud().id(longCount.incrementAndGet());
    }
}
