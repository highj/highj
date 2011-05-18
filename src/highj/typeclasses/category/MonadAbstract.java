/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Function;
import fj.Unit;
import fj.data.List;
import highj.TC;
import highj._;
import highj.data.ListMonadPlus;
import highj.data.ListOf;

/**
 *
 * @author DGronau
 */
public abstract class MonadAbstract<Ctor extends TC<Ctor>> extends ApplicativeAbstract<Ctor> implements Monad<Ctor> {

    
    @Override
    // (>>=) (Control.Monad)
    public abstract <A, B> _<Ctor, B> bind(_<Ctor, A> nestedA, F<A, _<Ctor, B>> fn);

    @Override
    // (>>) (Control.Monad)
    public <A, B> _<Ctor, B> semicolon(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return bind(nestedA, Function.<A, _<Ctor, B>>constant().f(nestedB));
    }
    
    @Override
    // join (Control.Monad)
    public <A> _<Ctor,A> join(_<Ctor, _<Ctor,A>> nestedNestedA) {
        return bind(nestedNestedA, Function.<_<Ctor,A>>identity());
    }

    @Override
    // liftM (Control.Monad)
    public <A, B> _<Ctor, B> liftM(F<A, B> fn, _<Ctor, A> nestedA) {
        return liftA(fn, nestedA);
    }

    @Override
    // liftM2 (Control.Monad)
    public <A, B, C> _<Ctor, C> liftM2(F<A, F<B, C>> f, _<Ctor, A> ta, _<Ctor, B> tb) {
        return liftA2(f, ta, tb);
    }

    @Override
    // liftM3 (Control.Monad)
    public <A, B, C, D> _<Ctor, D> liftM3(F<A, F<B, F<C, D>>> f, _<Ctor, A> ta, _<Ctor, B> tb, _<Ctor, C> tc) {
        return liftA3(f, ta, tb, tc);
    }

    @Override
    // return (Control.Monad)
    public <A> _<Ctor, A> returnM(A a) {
        return pure(a);
    }

    @Override
    // sequence (Control.Monad)
    public <A> _<Ctor, _<ListOf, A>> sequence(_<ListOf, _<Ctor, A>> ms) {
       final ListOf listOf = ListOf.getInstance(); 
       return fmap(new F<List<A>,_<ListOf, A>>(){
            @Override
            public _<ListOf, A> f(List<A> a) {
                return listOf.wrap(a);
            }
         },sequenceFlat(listOf.unwrap(ms)));
    }
    
    @Override
    // "flat" version of sequence
    public <A> _<Ctor, List<A>> sequenceFlat(List<_<Ctor, A>> list) {
      //  sequence ms = foldr k (return []) ms
      //      where k m m' = do { x <- m; xs <- m'; return (x:xs) }
      return list.foldRight(new F2<_<Ctor, A>,_<Ctor, List<A>>,_<Ctor, List<A>>>(){
            @Override
            public _<Ctor, List<A>> f(_<Ctor, A> m, final _<Ctor, List<A>> m_) {
                return bind(m, new F<A, _<Ctor, List<A>>>(){
                    @Override
                    public _<Ctor, List<A>> f(final A a) {
                        return bind(m_, new F<List<A>, _<Ctor, List<A>>>(){
                            @Override
                            public _<Ctor, List<A>> f(List<A> listA) {
                                return returnM(listA.cons(a));
                            }
                        });
                    }
                });
            }
        }, returnM(List.<A>nil()));
    }
    
    // sequence_ (Control.Monad) 
    @Override
    public <A> _<Ctor, Unit> sequence_(_<ListOf, _<Ctor, A>> list){
        return sequence_Flat(ListOf.getInstance().unwrap(list));
    }
    
    // "flat" version of sequence_ 
    @Override
    public <A> _<Ctor, Unit> sequence_Flat(List<_<Ctor, A>> list) {
        return list.foldRight(new F2<_<Ctor, A>, _<Ctor, Unit>, _<Ctor, Unit>>(){
            @Override
            public _<Ctor, Unit> f(_<Ctor, A> a, _<Ctor, Unit> b) {
                return semicolon(a,b);
            }
        }, returnM(Unit.unit()));
    }

    @Override
    // mapM (Control.Monad)
    public <A,B> _<Ctor,_<ListOf,B>> mapM(F<A,_<Ctor, B>> fn, _<ListOf,A> list) {
        return sequence(ListMonadPlus.getInstance().fmap(fn,list));
    }

    @Override
    // "flat" version of mapM
    public <A,B> _<Ctor, List<B>> mapMFlat(F<A,_<Ctor, B>> fn, List<A> list) {
        return sequenceFlat(list.map(fn));
    }
   
    @Override
    // (>=>) (Control.Monad) left-to-right Kleisli composition of monads
    public <A,B,C> F<A, _<Ctor,C>> kleisli(final F<A, _<Ctor,B>> f, final F<B, _<Ctor,C>> g) {
        return new F<A, _<Ctor,C>>() {
            @Override
            public _<Ctor, C> f(A a) {
                return MonadAbstract.this.bind(f.f(a), g);
            }
        };
    }

    @Override
    // ap (Control.Monad)
    public <A, B> _<Ctor, B> ap(_<Ctor, F<A, B>> nestedFn, _<Ctor, A> nestedA) {
        return liftM2(Function.<F<A,B>>identity(), nestedFn, nestedA);
    }
}
