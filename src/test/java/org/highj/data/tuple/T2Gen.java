package org.highj.data.tuple;

import com.pholser.junit.quickcheck.generator.ComponentizedGenerator;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

@SuppressWarnings("rawtypes")
public class T2Gen extends ComponentizedGenerator<T2> {

    public T2Gen() {
        super(T2.class);
    }

    @Override
    public T2<?, ?> generate(SourceOfRandomness random, GenerationStatus status) {
        return T2.of(
            componentGenerators().get(0).generate(random, status),
            componentGenerators().get(1).generate(random, status)
        );
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
