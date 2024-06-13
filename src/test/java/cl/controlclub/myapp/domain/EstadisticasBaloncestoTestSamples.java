package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EstadisticasBaloncestoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EstadisticasBaloncesto getEstadisticasBaloncestoSample1() {
        return new EstadisticasBaloncesto().id(1L).puntos(1).rebotes(1).asistencias(1).robos(1).bloqueos(1);
    }

    public static EstadisticasBaloncesto getEstadisticasBaloncestoSample2() {
        return new EstadisticasBaloncesto().id(2L).puntos(2).rebotes(2).asistencias(2).robos(2).bloqueos(2);
    }

    public static EstadisticasBaloncesto getEstadisticasBaloncestoRandomSampleGenerator() {
        return new EstadisticasBaloncesto()
            .id(longCount.incrementAndGet())
            .puntos(intCount.incrementAndGet())
            .rebotes(intCount.incrementAndGet())
            .asistencias(intCount.incrementAndGet())
            .robos(intCount.incrementAndGet())
            .bloqueos(intCount.incrementAndGet());
    }
}
