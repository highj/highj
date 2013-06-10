package org.highj.data.predicates;

import org.highj._;
import org.highj.data.predicates.pred.AndGroup;
import org.highj.data.predicates.pred.OrGroup;
import org.highj.data.predicates.pred.PredContravariant;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface Pred<A> extends _<Pred.µ, A>, Predicate<A> {

    public static class µ {
    }

    @SuppressWarnings("unchecked")
    public static <A> Pred<A> narrow(_<Pred.µ, A> nestedA) {
        return (Pred) nestedA;
    }

    public static <A> Pred<A> fromPredicate(Predicate<A> predicate) {
        return predicate::test;
    }

    public static <A> Pred<A> fromFunction(Function<A, Boolean> fn) {
        return fn::apply;
    }

    public static <A> Pred<A> True() {
        return a -> true;
    }

    public static <A> Pred<A> False() {
        return a -> false;
    }

    public default Pred<A> not() {
        return a -> !test(a);
    }

    public default Pred<A> and(Pred<A> that) {
        return a -> this.test(a) && that.test(a);
    }

    public default Pred<A> or(Pred<A> that) {
        return a -> this.test(a) || that.test(a);
    }

    public default Pred<A> xor(Pred<A> that) {
        return a -> this.test(a) ^ that.test(a);
    }

    public default Pred<A> eq(Pred<A> that) {
        return a -> this.test(a) == that.test(a);
    }

    public static <A> Group<Pred<A>> andGroup() {
        return new AndGroup<>();
    }

    public static <A> Group<Pred<A>> orGroup() {
        return new OrGroup<>();
    }

    public static final PredContravariant contravariant = new PredContravariant();

}
