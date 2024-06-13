package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AsistenciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Asistencia getAsistenciaSample1() {
        return new Asistencia().id(1L).idEvento(1L);
    }

    public static Asistencia getAsistenciaSample2() {
        return new Asistencia().id(2L).idEvento(2L);
    }

    public static Asistencia getAsistenciaRandomSampleGenerator() {
        return new Asistencia().id(longCount.incrementAndGet()).idEvento(longCount.incrementAndGet());
    }
}
