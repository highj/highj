package org.highj.typeclass2.injective;

import org.highj.data.eq.Eq;
import org.highj.util.Gen;
import org.highj.util.Law;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectiveLaw<A, B> implements Law {

    private final Injective<A, B> injective;
    protected final Gen<A> genA;
    protected final Eq<A> eqA;
    protected final Eq<B> eqB;

    public InjectiveLaw(Injective<A, B> injective, Gen<A> genA, Eq<A> eqA, Eq<B> eqB) {
        this.injective = injective;
        this.genA = genA;
        this.eqA = eqA;
        this.eqB = eqB;
    }

    public void injectivity() {
        for (A a1 : genA.get(10)) {
            B b1 = injective.to(a1);
            for (A a2 : genA.get(10)) {
                B b2 = injective.to(a2);
                assertThat(eqA.eq(a1, a2) == eqB.eq(b1, b2)).isTrue();
            }
        }
    }

    @Override
    public void test() {
        injectivity();
    }
}
