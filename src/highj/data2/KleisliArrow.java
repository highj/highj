/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.Function;
import fj.P;
import fj.P2;
import highj._;
import highj.__;
import highj.typeclasses.category.Monad;
import highj.typeclasses.category2.Arrow;
import highj.typeclasses.category2.ArrowAbstract;

/**
 *
 * @author dgronau
 */
public class KleisliArrow<M> extends ArrowAbstract<Kleisli<M>> implements Arrow<Kleisli<M>> {
    
    private final Monad<M> monad;
    
    public KleisliArrow(Monad<M> monad) {
        this.monad = monad;
    }

    @Override
    public <B, C> __<Kleisli<M>, B, C> arr(final F<B, C> fn) {
        return Kleisli.wrap(new F<B,_<M,C>>(){
            @Override
            public _<M, C> f(B b) {
                return monad.pure(fn.f(b));
            }
        });
    }

    @Override
    public <B, C, D> __<Kleisli<M>, P2<B, D>, P2<C, D>> first(final __<Kleisli<M>, B, C> f) {
        return Kleisli.wrap(new F<P2<B,D>,_<M, P2<C,D>>>(){
            @Override
            public _<M,P2<C, D>> f(final P2<B, D> bd) {
                return monad.bind(Kleisli.apply(f, bd._1()), new F<C, _<M,P2<C,D>>>(){
                    @Override
                    public _<M, P2<C, D>> f(C c) {
                        return monad.pure(P.p(c, bd._2()));
                    }
                });
            }
        });
    }
    
    @Override
    //overwriting ArrowAbstract implementation for better performance
    public <B,C,D> __<Kleisli<M>, P2<D,B>, P2<D,C>> second(final __<Kleisli<M>,B,C> f){
        
        return Kleisli.wrap(new F<P2<D,B>,_<M, P2<D,C>>>(){
            @Override
            public _<M,P2<D, C>> f(final P2<D, B> db) {
                return monad.bind(Kleisli.apply(f, db._2()), new F<C, _<M, P2<D,C>>>(){
                    @Override
                    public _<M, P2<D, C>> f(C c) {
                        return monad.pure(P.p(db._1(), c));
                    }
                });
            }
        });
    }

    @Override
    public <B> __<Kleisli<M>, B, B> id() {
        return arr(Function.<B>identity());
    }

    @Override
    public <B, C, D> __<Kleisli<M>, B, D> o(final __<Kleisli<M>, C, D> f, final __<Kleisli<M>, B, C> g) {
        return Kleisli.wrap(new F<B,_<M,D>>(){
            @Override
            public _<M, D> f(B b) {
                return monad.bind(Kleisli.apply(g, b), Kleisli.unwrap(f));
            }
        });
    }
    
}
