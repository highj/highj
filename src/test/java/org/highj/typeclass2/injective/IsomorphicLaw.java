package org.highj.typeclass2.injective;

import org.highj.data.eq.Eq;
import org.highj.util.Gen;

import static org.assertj.core.api.Assertions.assertThat;

public class IsomorphicLaw<A, B> extends InjectiveLaw<A, B> {

    private final Isomorphic<A, B> isomorphic;
    protected final Gen<B> genB;

    public IsomorphicLaw(Isomorphic<A, B> isomorphic, Gen<A> genA, Gen<B> genB, Eq<A> eqA, Eq<B> eqB) {
        super(isomorphic, genA, eqA, eqB);
        this.isomorphic = isomorphic;
        this.genB = genB;
    }

    public void isomorphism() {
        for (A a : genA.get(20)) {
            assertThat(eqA.eq(a, isomorphic.from(isomorphic.to(a)))).isTrue();
        }
        for (B b : genB.get(20)) {
            assertThat(eqB.eq(b, isomorphic.to(isomorphic.from(b)))).isTrue();
        }
    }

    public void inverseInjectivity() {
        new InjectiveLaw<>(isomorphic.inverse(), genB, eqB, eqA).test();
    }

    @Override
    public void test() {
        isomorphism();
        inverseInjectivity();
        super.test();
    }

}
