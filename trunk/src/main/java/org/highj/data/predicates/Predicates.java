package org.highj.data.predicates;

import org.highj.data.predicates.predicates.AndGroup;
import org.highj.data.predicates.predicates.OrGroup;
import org.highj.typeclass0.group.Group;

import java.util.function.Function;
import java.util.function.Predicate;


public enum Predicates {
    ;

    public static <A> Function<A,Boolean> toFunction(Predicate<A> predicate) {
        return predicate::test;
    }

    public static <A> Predicate<A> fromFunction(Function<A,Boolean> fn) {
        return fn::apply;
    }

    public static <A> Predicate<A> True() {
        return a -> true;
    }

    public static <A> Predicate<A> False() {
        return a -> false;
    }

    public static <A> Predicate<A> not(final Predicate<A> predicate) {
        return a -> !predicate.test(a);
    }

    @SafeVarargs
    public static <A> Predicate<A> and(final Predicate<A>... predicates) {
        return a -> {
            for (Predicate<A> predicate : predicates) {
                if (!predicate.test(a)) {
                    return false;
                }
            }
            return true;
        };
    }

    @SafeVarargs
    public static <A> Predicate<A> or(final Predicate<A>... predicates) {
        return a -> {
            for (Predicate<A> predicate : predicates) {
                if (predicate.test(a)) {
                    return true;
                }
            }
            return false;
        };
    }

    public static <A> Predicate<A> xor(final Predicate<A> p1, final Predicate<A> p2) {
        return a -> p1.test(a) ^ p2.test(a);
    }

    public static <A> Predicate<A> eq(final Predicate<A> p1, final Predicate<A> p2) {
        return a -> p1.test(a) == p2.test(a);
    }

    public static <A> Group<Predicate<A>> andGroup() {
        return new AndGroup<>();
    }

    public static <A> Group<Predicate<A>> orGroup() {
        return new OrGroup<>();
    }

}
