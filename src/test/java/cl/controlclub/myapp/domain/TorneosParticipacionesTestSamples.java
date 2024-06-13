package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TorneosParticipacionesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TorneosParticipaciones getTorneosParticipacionesSample1() {
        return new TorneosParticipaciones().id(1L).descripcion("descripcion1");
    }

    public static TorneosParticipaciones getTorneosParticipacionesSample2() {
        return new TorneosParticipaciones().id(2L).descripcion("descripcion2");
    }

    public static TorneosParticipaciones getTorneosParticipacionesRandomSampleGenerator() {
        return new TorneosParticipaciones().id(longCount.incrementAndGet()).descripcion(UUID.randomUUID().toString());
    }
}
