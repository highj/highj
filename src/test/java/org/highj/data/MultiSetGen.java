package org.highj.data;

import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

@SuppressWarnings("rawtypes")
public class MultiSetGen extends ComponentizedGenerator<MultiSet> {

    public MultiSetGen() {
        super(MultiSet.class);
    }

    @Override
    public MultiSet<?> generate(SourceOfRandomness random, GenerationStatus status) {
        return MultiSet.of1(componentGenerators().get(0).times(random.nextInt(20)));
    }

    @Override public int numberOfNeededComponents() {
        return 1;
    }
}
