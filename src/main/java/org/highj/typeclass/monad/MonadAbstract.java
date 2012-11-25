package org.highj.typeclass.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Maybe;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.group.MonoidAbstract;
import org.highj.util.Mutable;

//We have to duplicate code from ApplicativeAbstract here, as we have no multiple inheritance
public abstract class MonadAbstract<mu> extends BindAbstract<mu> implements Monad<mu> {


    // pure (Data.Pointed, Control.Applicative)
    //duplicated in ApplicativeAbstract
    public abstract <A> _<mu, A> pure(A a);

    // curried version of pure
    //duplicated in ApplicativeAbstract
    public <A> F1<A, _<mu, A>> pure() {
        return new F1<A, _<mu, A>>() {
            @Override
            public _<mu, A> $(A a) {
                return pure(a);
            }
        };
    }

    // mapM (Control.Monad)
    public <A, B> F1<List<A>, _<mu, List<B>>> mapM(final F1<A, _<mu, B>> fn) {
        return new F1<List<A>, _<mu, List<B>>>() {
            @Override
            public _<mu, List<B>> $(List<A> list) {
                return sequence(list.map(fn));
            }
        };
    }

    // mapM_ (Control.Monad)
    public <A, B> F1<List<A>, _<mu, T0>> mapM_(final F1<A, _<mu, B>> fn) {
        return new F1<List<A>, _<mu, T0>>() {
            @Override
            public _<mu, T0> $(List<A> list) {
                return sequence_(list.map(fn));
            }
        };
    }

    //foldM (Control.Monad)
    public <A, B> F2<A, List<B>, _<mu, A>> foldM(final F1<A, F1<B, _<mu, A>>> fn) {
        return new F2<A, List<B>, _<mu, A>>() {
            @Override
            public _<mu, A> $(A a, List<B> listB) {
                _<mu, A> result = pure(a);
                final Mutable<B> b = new Mutable<B>();
                F1<A, _<mu, A>> fnBind = new F1<A, _<mu, A>>() {
                    @Override
                    public _<mu, A> $(A a) {
                        return fn.$(a).$(b.get());
                    }
                };
                while (!listB.isEmpty()) {
                    b.set(listB.head());
                    listB = listB.tail();
                    result = bind(result, fnBind);
                }
                return result;
            }
        };
    }

    //foldM_ (Control.Monad)
    public <A, B> F2<A, List<B>, _<mu, T0>> foldM_(final F1<A, F1<B, _<mu, A>>> fn) {
        return new F2<A, List<B>, _<mu, T0>>() {
            @Override
            public _<mu, T0> $(A a, List<B> listB) {
                _<mu, A> result = pure(a);
                final Mutable<B> b = Mutable.Mutable();
                F1<A, _<mu, A>> fnBind = new F1<A, _<mu, A>>() {
                    @Override
                    public _<mu, A> $(A a) {
                        return fn.$(a).$(b.get());
                    }
                };
                while (!listB.isEmpty()) {
                    b.set(listB.head());
                    listB = listB.tail();
                    result = bind(result, fnBind);
                }
                return pure(T0.unit);
            }
        };
    }

    //replicateM (Control.Monad)
    public <A> _<mu, List<A>> replicateM(int n, _<mu, A> nestedA) {
        return sequence(List.replicate(n, nestedA));
    }

    //replicateM_ (Control.Monad)
    public <A> _<mu, T0> replicateM_(int n, _<mu, A> nestedA) {
        return sequence_(List.replicate(n, nestedA));
    }

    //sequence (Control.Monad)
    public <A> _<mu, List<A>> sequence(List<_<mu, A>> list) {
        //  sequence ms = foldr (liftM2 (:)) (return []) ms
        F2<_<mu, A>, _<mu, List<A>>, _<mu, List<A>>> f2 = lift2(List.<A>cons());
        return list.foldr(f2, pure(List.<A>Nil()));
    }

    //sequence_ (Control.Monad)
    public <A> _<mu, T0> sequence_(List<_<mu, A>> list) {
        sequence(list);
        return pure(T0.unit);
    }

    //MonadPlus.mzero (Control.Monad)
    //Override this method if you want to implement MonadZero.
    public <A> _<mu, A> mzero() {
        throw new UnsupportedOperationException();
    }

    //guard (Control.Monad)
    //for MonadZero
    public _<mu, T0> guard(boolean condition) {
        if (condition) {
            return pure(T0.unit);
        } else {
            return mzero();
        }
    }

    //mfilter (Control.Monad)
    //for MonadZero
    public <A> _<mu, A> mfilter(F1<A, Boolean> condition, final _<mu, A> target) {
        _<mu, Boolean> result = map(condition, target);
        return bind(result, new F1<Boolean, _<mu, A>>(){
            @Override
            public _<mu, A> $(Boolean b) {
                if (b) {
                    return target;
                } else {
                    return mzero();
                }
            }
        });
    }

    //MonadPlus.(++) (Control.Monad)
    //Override this method and mzero() if you want to implement MonadPlus.
    public <A> _<mu, A> mplus(_<mu, A> one, _<mu, A> two) {
        throw new UnsupportedOperationException();
    }

    //MonadPlus.(++) (Control.Monad)
    public <A> F2<_<mu, A>,_<mu, A>,_<mu, A>> mplus() {
        return new F2<_<mu, A>, _<mu, A>, _<mu, A>>() {
            @Override
            public _<mu, A> $(_<mu, A> one, _<mu, A> two) {
                return mplus(one, two);
            }
        };
    }

    //msum (Control.Monad)
    //for MonadPlus
    public <A> _<mu, A> msum(_<List.µ, _<mu, A>> list) {
        List<_<mu, A>> as = List.narrow(list);
        _<mu, A> zero = mzero();
        return as.foldr(new F2<_<mu, A>,_<mu, A>,_<mu, A>>(){
            @Override
            public _<mu, A> $(_<mu, A> one, _<mu, A> two) {
                return mplus(one, two);
            }
        }, zero);
    }

    //for MonadPlus
    public <A> _<mu, Maybe<A>> optional(_<mu, A> nestedA) {
        return mplus(map(Maybe.<A>just(), nestedA), pure(Maybe.<A>Nothing()));
    }

    //for MonadPlus
    public <A> Monoid<_<mu, A>> asMonoid() {
        return new MonoidAbstract<_<mu, A>>(this.<A>mplus(), this.<A>mzero());
    }

}
