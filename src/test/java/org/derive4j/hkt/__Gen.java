package org.derive4j.hkt;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

@SuppressWarnings("rawtype")
public class __Gen extends Generator<__> {

    public __Gen() {
        super(__.class);
        System.err.println("__Gen");
    }

    @Override
    @SuppressWarnings("unchecked")
    public __<?, ?> generate(SourceOfRandomness random, GenerationStatus status) {
        Generator<__<?, ?>> gen = (Generator<__<?, ?>>) (gen(random).type(
            types().get(0).getEnclosingClass(),
            types().get(1)
        ));
        return gen.generate(random, status);
    }

    @Override
    public boolean hasComponents() {
        return false;
    }
}
