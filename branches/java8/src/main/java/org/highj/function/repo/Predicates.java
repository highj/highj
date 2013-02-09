package org.highj.function.repo;

import org.highj.function.Functions;
import org.highj.typeclass0.group.Group;

import java.util.function.Function;


public enum Predicates {
    ;

    public static <A> Function<A, Boolean> True() {
        return Functions.constant(true);
    }

    public static <A> Function<A, Boolean> False() {
        return Functions.constant(false);
    }

    public static <A> Function<A, Boolean> not(final Function<A, Boolean> predicate) {
        return a -> !predicate.apply(a);
    }

    @SafeVarargs
    public static <A> Function<A, Boolean> and(final Function<A, Boolean>... predicates) {
        return a -> {
            for (Function<A, Boolean> predicate : predicates) {
                if (!predicate.apply(a)) {
                    return false;
                }
            }
            return true;
        };
    }

    @SafeVarargs
    public static <A> Function<A, Boolean> or(final Function<A, Boolean>... predicates) {
        return a -> {
            for (Function<A, Boolean> predicate : predicates) {
                if (predicate.apply(a)) {
                    return true;
                }
            }
            return false;
        };
    }

    public static <A> Function<A, Boolean> xor(final Function<A, Boolean> p1, final Function<A, Boolean> p2) {
        return a -> p1.apply(a) ^ p2.apply(a);
    }

    public static <A> Function<A, Boolean> eq(final Function<A, Boolean> p1, final Function<A, Boolean> p2) {
        return a -> p1.apply(a) == p2.apply(a);
    }

    public static <A> Group<Function<A, Boolean>> andGroup() {
        return new Group<Function<A, Boolean>>(){
            @Override
            public Function<A, Boolean> inverse(Function<A, Boolean> f) {
                return x -> ! f.apply(x);
            }

            @Override
            public Function<A, Boolean> identity() {
                return True();
            }

            @Override
            public Function<A, Boolean> dot(Function<A, Boolean> f, Function<A, Boolean> g) {
                return x -> f.apply(x) && g.apply(x);
            }
        };
    }

    public static <A> Group<Function<A, Boolean>> orGroup() {
        return new Group<Function<A, Boolean>>(){
            @Override
            public Function<A, Boolean> inverse(Function<A, Boolean> f) {
                return x -> ! f.apply(x);
            }

            @Override
            public Function<A, Boolean> identity() {
                return False();
            }

            @Override
            public Function<A, Boolean> dot(Function<A, Boolean> f, Function<A, Boolean> g) {
                return x -> f.apply(x) || g.apply(x);
            }
        };
    }

}
