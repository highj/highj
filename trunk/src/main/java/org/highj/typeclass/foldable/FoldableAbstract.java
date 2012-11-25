package org.highj.typeclass.foldable;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.data.collection.List;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.group.Semigroup;

//FoldableAbstract is abstract because one of foldr mplus foldMap MUST be overwritten
public abstract class FoldableAbstract<µ> implements Foldable<µ> {

    @Override
    public <A> A fold(Monoid<A> ma, _<µ, A> nestedA) {
        return foldMap(ma, F1.<A>id(), nestedA);
    }

    @Override
    public <A, B> B foldMap(final Monoid<B> mb, final F1<A, B> fn, _<µ, A> nestedA) {
        return foldr(new F2<A,B,B>(){
            @Override
            public B $(A a, B b) {
                return mb.dot(fn.$(a),b);
            }
        }, mb.identity(), nestedA);
    }

    @Override
    public <A, B> B foldr(final F1<A, F1<B, B>> fn, B b, _<µ, A> nestedA){
        //foldr f z t = appEndo (foldMap (Endo . f) t) z
        return F1.narrow(foldMap(F1.<B>endoMonoid(), F2.widen2(fn), nestedA)).$(b);
    }

    @Override
    //This is very inefficient, please override if possible.
    public <A, B> A foldl(final F1<A, F1<B, A>> fn, A a, _<µ, B> nestedB) {
        //foldl f a bs = foldr (\b h -> \a ->h (f a b)) id bs a
        return foldr(new F2<B,F1<A,A>,F1<A,A>>(){
            @Override
            public F1<A, A> $(final B b, final F1<A, A> h) {
                return new F1<A,A>(){
                    @Override
                    public A $(A a) {
                        return h.$(fn.$(a).$(b));
                    }
                };
            }
        }, F1.<A>id(), nestedB).$(a);
    }

    @Override
    public <A> A fold1(Semigroup<A> sa, _<µ, A> nestedA){
        return foldMap1(sa, F1.<A>id(), nestedA);
    }

    @Override
    public <A,B> B foldMap1(Semigroup<B> sa, F1<A,B> fn, _<µ, A> nestedA){
        Maybe<B> result = foldMap(Maybe.<B>monoid(sa), fn.andThen(Maybe.<B>just()), nestedA);
        return result.getOrError("foldMap1 on mzero data structure");
    }

    @Override
    public <A> Maybe<A> foldr1(final F1<A, F1<A, A>> fn, _<µ, A> nestedA) {
        return foldr(new F2<A, Maybe<A>, Maybe<A>>() {
            @Override
            public Maybe<A> $(A one, Maybe<A> maybeTwo) {
                for (A two : maybeTwo) {
                    return Maybe.Just(fn.$(one).$(two));
                }
                return Maybe.Just(one);
            }
        }, Maybe.<A>Nothing(), nestedA);
    }

    @Override
    public <A> Maybe<A> foldl1(final F1<A, F1<A, A>> fn, _<µ, A> nestedA) {
        return foldl(new F2<Maybe<A>, A, Maybe<A>>() {
            @Override
            public Maybe<A> $(Maybe<A> maybeOne, A two) {
                for (A one : maybeOne) {
                    return Maybe.Just(fn.$(one).$(two));
                }
                return Maybe.Just(two);
            }
        }, Maybe.<A>Nothing(), nestedA);
    }

    @Override
    public <A> List<A> toList(_<µ, A> nestedA){
        return foldr(List.<A>cons(), List.<A>Nil(), nestedA);
    }
}