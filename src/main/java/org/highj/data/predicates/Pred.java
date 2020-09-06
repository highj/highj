package org.highj.data.predicates;

import org.derive4j.hkt.__;
import org.highj.data.predicates.pred.PredContravariant;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface Pred<A> extends __<Pred.µ, A>, Predicate<A> {

    interface µ {
    }

    static <A> Pred<A> fromPredicate(Predicate<A> predicate) {
        return predicate::test;
    }

    static <A> Pred<A> fromFunction(Function<A, Boolean> fn) {
        return fn::apply;
    }

    static <A> Pred<A> True() {
        return a -> true;
    }

    static <A> Pred<A> False() {
        return a -> false;
    }

    default Pred<A> not() {
        return a -> !test(a);
    }

    default Pred<A> and(Pred<A> that) {
        return a -> this.test(a) && that.test(a);
    }

    default Pred<A> or(Pred<A> that) {
        return a -> this.test(a) || that.test(a);
    }

    default Pred<A> xor(Pred<A> that) {
        return a -> this.test(a) ^ that.test(a);
    }

    default Pred<A> eq(Pred<A> that) {
        return a -> this.test(a) == that.test(a);
    }

    static <A> Pred<A> fromJavaSet(Set<A> set) {
        return set::contains;
    }

    @SafeVarargs
    static <A> Pred<A> ands(final Pred<A>... predicates) {
        return a -> {
            for (Pred<A> pred : predicates) {
                if (!pred.test(a)) {
                    return false;
                }
            }
            return true;
        };
    }

    @SafeVarargs
    static <A> Pred<A> ors(final Pred<A>... predicates) {
        return a -> {
            for (Pred<A> pred : predicates) {
                if (pred.test(a)) {
                    return true;
                }
            }
            return false;
        };
    }

    static <A> Monoid<Pred<A>> andMonoid() {
        return Monoid.create(Pred.True(), (f,g) -> x -> f.test(x) && g.test(x));
    }

    static <A> Monoid<Pred<A>> orMonoid() {
        return Monoid.create(Pred.False(), (f,g) -> x -> f.test(x) || g.test(x));
    }

    PredContravariant contravariant = new PredContravariant(){};

}
