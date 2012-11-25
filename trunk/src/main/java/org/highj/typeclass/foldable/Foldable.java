package org.highj.typeclass.foldable;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.data.collection.List;
import org.highj.function.F1;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.group.Semigroup;

public interface Foldable<µ> {

    public <A> A fold(Monoid<A> ma, _<µ, A> nestedA);

    public <A,B> B foldMap(Monoid<B> mb, F1<A,B> fn, _<µ, A> nestedA);

    public <A,B> B foldr(F1<A,F1<B,B>> fn, B b, _<µ, A> nestedA);

    public <A,B> A foldl(F1<A,F1<B,A>> fn, A a, _<µ, B> nestedB);

    public <A> A fold1(Semigroup<A> sa, _<µ, A> nestedA);

    public <A,B> B foldMap1(Semigroup<B> sa, F1<A,B> fn, _<µ, A> nestedA);

    public <A> Maybe<A> foldr1(F1<A, F1<A, A>> fn, _<µ, A> nestedA);

    public <A> Maybe<A> foldl1(F1<A, F1<A, A>> fn, _<µ, A> nestedA);

    public <A> List<A> toList(_<µ, A> nestedA);
}