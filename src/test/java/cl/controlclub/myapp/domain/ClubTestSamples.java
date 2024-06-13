package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClubTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Club getClubSample1() {
        return new Club().id(1L).razon("razon1").direccion("direccion1").telefono("telefono1").email("email1").presidente("presidente1");
    }

    public static Club getClubSample2() {
        return new Club().id(2L).razon("razon2").direccion("direccion2").telefono("telefono2").email("email2").presidente("presidente2");
    }

    public static Club getClubRandomSampleGenerator() {
        return new Club()
            .id(longCount.incrementAndGet())
            .razon(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .presidente(UUID.randomUUID().toString());
    }
}
