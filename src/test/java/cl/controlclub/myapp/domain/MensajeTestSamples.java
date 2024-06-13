package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MensajeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mensaje getMensajeSample1() {
        return new Mensaje().id(1L).remitenteId(1L).destinatarioId(1L).mensaje("mensaje1");
    }

    public static Mensaje getMensajeSample2() {
        return new Mensaje().id(2L).remitenteId(2L).destinatarioId(2L).mensaje("mensaje2");
    }

    public static Mensaje getMensajeRandomSampleGenerator() {
        return new Mensaje()
            .id(longCount.incrementAndGet())
            .remitenteId(longCount.incrementAndGet())
            .destinatarioId(longCount.incrementAndGet())
            .mensaje(UUID.randomUUID().toString());
    }
}
