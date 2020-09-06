package org.highj.data;

import com.pholser.junit.quickcheck.generator.ComponentizedGenerator;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

@SuppressWarnings("rawtypes")
public class ListGen extends ComponentizedGenerator<List> {

    public ListGen() {
        super(List.class);
    }

    @Override
    public List generate(SourceOfRandomness random, GenerationStatus status) {
        return List.of(componentGenerators().get(0).times(random.nextInt(20)));
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }
}
