/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.P;
import fj.P2;
import highj._;
import highj.__;
import highj.typeclasses.category.Comonad;
import highj.typeclasses.category2.Arrow;
import highj.typeclasses.category2.ArrowAbstract;

/**
 *
 * @author dgronau
 */
public class CokleisliArrow<M> extends ArrowAbstract<Cokleisli<M>> implements Arrow<Cokleisli<M>> {

    private final Comonad<M> comonad;
    
    public CokleisliArrow(Comonad<M> comonad) {
        this.comonad = comonad;
    }
    
    @Override
    public <B, C> __<Cokleisli<M>, B, C> arr(F<B, C> fn) {
        //arr f = Cokleisli (f . extract)
        return Cokleisli.wrap(fn.o(comonad.<B>extract()));
    }

    @Override
    public <B, C, D> __<Cokleisli<M>, P2<B, D>, P2<C, D>> first(final __<Cokleisli<M>, B, C> arrow) {
        final F<_<M, P2<B, D>>,_<M,B>> f1 = comonad.lift(this.<B, D>fst());
        final F<_<M, P2<B, D>>,_<M,D>> f2 = comonad.lift(this.<B, D>snd());
        F<_<M, P2<B, D>>, P2<C, D>> fn = new F<_<M, P2<B, D>>, P2<C, D>>(){

            @Override
            public P2<C, D> f(_<M, P2<B, D>> bd) {
                return P.p(Cokleisli.apply(arrow, f1.f(bd)),comonad.extract(f2.f(bd)));
            }
            
        };
        return Cokleisli.wrap(fn);
    }
    
    @Override
    public <B, C, D> __<Cokleisli<M>, P2<D,B>, P2<D,C>> second(final __<Cokleisli<M>,B,C> arrow) {
        final F<_<M, P2<D, B>>,_<M,D>> f1 = comonad.lift(this.<D, B>fst());
        final F<_<M, P2<D, B>>,_<M,B>> f2 = comonad.lift(this.<D, B>snd());
        F<_<M, P2<D, B>>, P2<D, C>> fn = new F<_<M, P2<D, B>>, P2<D, C>>(){

            @Override
            public P2<D, C> f(_<M, P2<D, B>> db) {
                return P.p(comonad.extract(f1.f(db)), Cokleisli.apply(arrow, f2.f(db)));
            }
            
        };
        return Cokleisli.wrap(fn);
    }

    private <A,B> F<P2<A,B>,A> fst() {
        return new F<P2<A,B>,A>() {
            @Override
            public A f(P2<A, B> pair) {
                return pair._1();
            }
        };
    }

    private <A,B> F<P2<A,B>,B> snd() {
        return new F<P2<A,B>,B>() {
            @Override
            public B f(P2<A, B> pair) {
                return pair._2();
            }
        };
    }

    @Override
    public <B, C, D> __<Cokleisli<M>, B, D> o(__<Cokleisli<M>, C, D> cd, __<Cokleisli<M>, B, C> bc) {
        //Cokleisli f . Cokleisli g = Cokleisli (f =<= g)
        return Cokleisli.wrap(comonad.cokleisli(Cokleisli.unwrap(bc), Cokleisli.unwrap(cd)));
    }

    @Override
    public <B> __<Cokleisli<M>, B, B> id() {
        //id = Cokleisli extract
        return Cokleisli.wrap(comonad.<B>extract());
    }
    
}
