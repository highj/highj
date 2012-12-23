package org.highj.function.repo;

import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.group.Group;
import org.highj.typeclass.group.GroupAbstract;

public enum Predicates {
    ;

    public final static <A> F1<A, Boolean> True() {
        return F1.constant(true);
    }

    public final static <A> F1<A, Boolean> False() {
        return F1.constant(false);
    }

    public final static <A> F1<A, Boolean> not(final F1<A, Boolean> predicate) {
        return new F1<A, Boolean>() {

            @Override
            public Boolean $(A a) {
                return !predicate.$(a);
            }
        };
    }

    public final static <A> F1<F1<A,Boolean>, F1<A,Boolean>> not() {
        return new F1<F1<A,Boolean>,F1<A,Boolean>>() {
            @Override
            public F1<A, Boolean> $(F1<A, Boolean> x) {
                return not(x);
            }
        };
    }

    //@SafeVarargs
    public final static <A> F1<A, Boolean> and(final F1<A, Boolean>... predicates) {
        return new F1<A, Boolean>() {

            @Override
            public Boolean $(A a) {
                for (F1<A, Boolean> predicate : predicates) {
                    if (!predicate.$(a)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public final static <A> F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>> and() {
        return new F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>>() {
            @Override
            public F1<A, Boolean> $(F1<A, Boolean> x, F1<A, Boolean> y) {
                return and(x,y);
            }
        };
    }

    //@SafeVarargs
    public final static <A> F1<A, Boolean> or(final F1<A, Boolean>... predicates) {
        return new F1<A, Boolean>() {

            @Override
            public Boolean $(A a) {
                for (F1<A, Boolean> predicate : predicates) {
                    if (predicate.$(a)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public final static <A> F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>> or() {
        return new F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>>() {
            @Override
            public F1<A, Boolean> $(F1<A, Boolean> x, F1<A, Boolean> y) {
                return or(x, y);
            }
        };
    }

    public final static <A> F1<A, Boolean> xor(final F1<A, Boolean> p1, final F1<A, Boolean> p2) {
        return new F1<A, Boolean>() {

            @Override
            public Boolean $(A a) {
                return p1.$(a) ^ p2.$(a);
            }
        };
    }

    public final static <A> F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>> xor() {
        return new F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>>() {
            @Override
            public F1<A, Boolean> $(F1<A, Boolean> x, F1<A, Boolean> y) {
                return xor(x, y);
            }
        };
    }

    public final static <A> F1<A, Boolean> eq(final F1<A, Boolean> p1, final F1<A, Boolean> p2) {
        return new F1<A, Boolean>() {

            @Override
            public Boolean $(A a) {
                return p1.$(a) == p2.$(a);
            }
        };
    }

    public final static <A> F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>> eq() {
        return new F2<F1<A,Boolean>,F1<A,Boolean>,F1<A,Boolean>>() {
            @Override
            public F1<A, Boolean> $(F1<A, Boolean> x, F1<A, Boolean> y) {
                return eq(x, y);
            }
        };
    }

    public final static <A> Group<F1<A, Boolean>> andGroup() {
        return new GroupAbstract<F1<A, Boolean>>(
                Predicates.<A>and(),
                Predicates.<A>True(),
                Predicates.<A>not());
    }

    public final static <A> Group<F1<A, Boolean>> orGroup() {
        return new GroupAbstract<F1<A, Boolean>>(
                Predicates.<A>or(),
                Predicates.<A>False(),
                Predicates.<A>not());
    }

}
