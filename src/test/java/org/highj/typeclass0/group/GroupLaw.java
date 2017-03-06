package org.highj.typeclass0.group;

import org.highj.data.eq.Eq;
import org.highj.util.Gen;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupLaw<A> extends MonoidLaw<A> {

    private final Group<A> group;

    public GroupLaw(Group<A> group, Gen<A> gen, Eq<A> eq) {
        super(group, gen, eq);
        this.group = group;
    }

    public void leftInverse() {
        for(A a : gen.get(20)) {
            A result = group.apply(group.inverse(a), a);
            assertThat(eq.eq(result, group.identity())).isTrue();
        }
    }

    public void rightInverse() {
        for(A a : gen.get(20)) {
            A result = group.apply(a, group.inverse(a));
            assertThat(eq.eq(result, group.identity())).isTrue();
        }
    }

    public void identityInverse() {
        A result = group.inverse(group.identity());
        assertThat(eq.eq(result, group.identity())).isTrue();
    }

    @Override
    public void test() {
        leftInverse();
        rightInverse();
        identityInverse();
        super.test();
    }

}
