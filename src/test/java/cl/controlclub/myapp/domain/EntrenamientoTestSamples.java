package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EntrenamientoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Entrenamiento getEntrenamientoSample1() {
        return new Entrenamiento().id(1L).duracion(1);
    }

    public static Entrenamiento getEntrenamientoSample2() {
        return new Entrenamiento().id(2L).duracion(2);
    }

    public static Entrenamiento getEntrenamientoRandomSampleGenerator() {
        return new Entrenamiento().id(longCount.incrementAndGet()).duracion(intCount.incrementAndGet());
    }
}
