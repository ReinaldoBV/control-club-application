package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CuentasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cuentas getCuentasSample1() {
        return new Cuentas().id(1L).descripcion("descripcion1").nroCuotas(1L);
    }

    public static Cuentas getCuentasSample2() {
        return new Cuentas().id(2L).descripcion("descripcion2").nroCuotas(2L);
    }

    public static Cuentas getCuentasRandomSampleGenerator() {
        return new Cuentas()
            .id(longCount.incrementAndGet())
            .descripcion(UUID.randomUUID().toString())
            .nroCuotas(longCount.incrementAndGet());
    }
}
