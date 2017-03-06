package org.highj.typeclass0.group;

import org.highj.data.List;
import org.highj.data.eq.Eq;
import org.highj.util.Gen;

import static org.assertj.core.api.Assertions.assertThat;

public class MonoidLaw<A> extends SemigroupLaw<A>  {

    private final Monoid<A> monoid;

    public MonoidLaw(Monoid<A> monoid, Gen<A> gen, Eq<A> eq) {
       super(monoid, gen, eq);
       this.monoid = monoid;
    }


    public void leftIdentity() {
        for(A a : gen.get(20)) {
            A result = monoid.apply(monoid.identity(), a);
            assertThat(eq.eq(result, a)).isTrue();
        }
    }
    public void rightIdentity() {
        for(A a : gen.get(20)) {
            A result = monoid.apply(a, monoid.identity());
            assertThat(eq.eq(result, a)).isTrue();
        }
    }

    public void fold() {
        List<A> as = List.fromIterable(gen.get(20));
        A monoidFoldResult = monoid.fold(as);
        A listFoldResult = as.foldr(monoid::apply, monoid.identity());
        assertThat(eq.eq(monoidFoldResult, listFoldResult)).isTrue();
    }

    @Override
    public void test() {
        leftIdentity();
        rightIdentity();
        fold();
        super.test();
    }
}
