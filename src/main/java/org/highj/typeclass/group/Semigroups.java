package org.highj.typeclass.group;

import org.highj.function.F2;

public enum Semigroups {
    ;

    public static <A> Semigroup<A> first() {
      return new SemigroupAbstract<A>(new F2<A, A, A>() {
          @Override
          public A $(A x, A y) {
              return x;
          }
      });
    };

    public static <A> Semigroup<A> last() {
        return new SemigroupAbstract<A>(new F2<A, A, A>() {
            @Override
            public A $(A x, A y) {
                return y;
            }
        });
    };

    public static <A> Semigroup<A> dual(final Semigroup<A> semigroup) {
        return new SemigroupAbstract<A>(semigroup.dot().flip());
    };

    public static <A extends Comparable<A>> Semigroup<A> min() {
        return new SemigroupAbstract<A>(new F2<A, A, A>() {
            @Override
            public A $(A x, A y) {
                return x.compareTo(y) <= 0 ? x : y;
            }
        });
    };

    public static <A extends Comparable<A>> Semigroup<A> max() {
        return new SemigroupAbstract<A>(new F2<A, A, A>() {
            @Override
            public A $(A x, A y) {
                return x.compareTo(y) >= 0 ? x : y;
            }
        });
    };

}
