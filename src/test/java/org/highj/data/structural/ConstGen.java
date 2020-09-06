package org.highj.data.structural;

import com.pholser.junit.quickcheck.generator.ComponentizedGenerator;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

@SuppressWarnings("rawtypes")
public class ConstGen extends ComponentizedGenerator<Const> {

    public ConstGen() {
        super(Const.class);
    }

    @Override
    public Const generate(SourceOfRandomness random, GenerationStatus status) {
        return Const.Const(componentGenerators().get(0).generate(random, status));
    }

    @Override
    public int numberOfNeededComponents() {
        return 2;
    }
}
