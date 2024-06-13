package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PartidoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Partido getPartidoSample1() {
        return new Partido().id(1L).oponente("oponente1").resultado("resultado1");
    }

    public static Partido getPartidoSample2() {
        return new Partido().id(2L).oponente("oponente2").resultado("resultado2");
    }

    public static Partido getPartidoRandomSampleGenerator() {
        return new Partido().id(longCount.incrementAndGet()).oponente(UUID.randomUUID().toString()).resultado(UUID.randomUUID().toString());
    }
}
