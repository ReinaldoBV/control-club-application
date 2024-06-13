package cl.controlclub.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategoriasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Categorias getCategoriasSample1() {
        return new Categorias().id(1L).anioInicio(1L).anioFinal(1L).nombreCategoria("nombreCategoria1");
    }

    public static Categorias getCategoriasSample2() {
        return new Categorias().id(2L).anioInicio(2L).anioFinal(2L).nombreCategoria("nombreCategoria2");
    }

    public static Categorias getCategoriasRandomSampleGenerator() {
        return new Categorias()
            .id(longCount.incrementAndGet())
            .anioInicio(longCount.incrementAndGet())
            .anioFinal(longCount.incrementAndGet())
            .nombreCategoria(UUID.randomUUID().toString());
    }
}
