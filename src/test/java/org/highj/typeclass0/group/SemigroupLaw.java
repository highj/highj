package org.highj.typeclass0.group;

import org.highj.data.eq.Eq;
import org.highj.util.Gen;
import org.highj.util.Law;

import static org.assertj.core.api.Assertions.assertThat;

public class SemigroupLaw<A> implements Law {

    private final Semigroup<A> semigroup;
    protected final Gen<A> gen;
    protected final Eq<A> eq;

    public SemigroupLaw(Semigroup<A> semigroup, Gen<A> gen, Eq<A> eq) {
        this.semigroup = semigroup;
        this.gen = gen;
        this.eq = eq;
    }

    private void associativity() {
        for(A a : gen.get(10)) {
            for(A b : gen.get(10)) {
                for(A c : gen.get(10)) {
                    A result1 = semigroup.apply(a, semigroup.apply(b,c));
                    A result2 = semigroup.apply(semigroup.apply(a, b), c);
                    assertThat(eq.eq(result1, result2)).isTrue();
                }
            }
        }
    }

    @Override
    public void test() {
        associativity();
    }

}
