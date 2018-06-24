package org.highj.typeclass2.category;

import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.function.F3;

public class Lam2CCC<K,Tensor,Hom,Unit> {
    private final CCC<K,Tensor,Hom,Unit> ccc;

    private Lam2CCC(CCC<K,Tensor,Hom,Unit> ccc) {
        this.ccc = ccc;
    }

    public static <K,Tensor,Hom,Unit> Lam2CCC<K,Tensor,Hom,Unit> create(CCC<K,Tensor,Hom,Unit> ccc) {
        return new Lam2CCC<>(ccc);
    }

    public <I,A,B> __2<K,I,__3<Hom,K,A,B>> lam1(F1<__2<K,__3<Tensor,K,I,A>,A>,__2<K,__3<Tensor,K,I,A>,B>> f) {
        return ccc.curry(f.apply(ccc.exr()));
    }

    public <I,A,B,C> __2<K,I,__3<Hom,K,A,__3<Hom,K,B,C>>> lam2(F2<__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,A>,__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,B>,__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>> f) {
        return ccc.curry(ccc.curry(f.apply(ccc.dot(ccc.exr(), ccc.exl()), ccc.exr())));
    }

    public <I,A,B,C,D> __2<K,I,__3<Hom,K,A,__3<Hom,K,B,__3<Hom,K,C,D>>>> lam3(F3<__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,A>,__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,B>,__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,C>,__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,D>> f) {
        return ccc.curry(ccc.curry(ccc.curry(f.apply(ccc.dot(ccc.exr(), ccc.dot(ccc.exl(), ccc.exl())), ccc.dot(ccc.exr(), ccc.exl()), ccc.exr()))));
    }

    public <I,A,B,C> __2<K,I,__3<Hom,K,A,__3<Hom,K,B,C>>> lam2Curried(F1<__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,A>,F1<__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,B>,__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>>> f) {
        return lam2((x, y) -> f.apply(x).apply(y));
    }

    public <I,A,B,C,D> __2<K,I,__3<Hom,K,A,__3<Hom,K,B,__3<Hom,K,C,D>>>> lam3Curried(F1<__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,A>,F1<__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,B>,F1<__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,C>,__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,D>>>> f) {
        return lam3((x, y, z) -> f.apply(x).apply(y).apply(z));
    }

    // lam :: forall k i a b. CCC k => ((forall x. Cast k x (Tensor k i a) => k x a) -> k (Tensor k i a) b) -> k i (Hom k a b)
    // lam f = curry (f exr_) where
    //   exr_ :: forall x. Cast k x (Tensor k i a) => k x a
    //   exr_ = exr . (cast :: k x (Tensor k i a))
    public <X,I,A,B> __2<K,I,__3<Hom,K,A,B>> lam(__2<K,X,__3<Tensor,K,I,A>> cast, F1<__2<K,X,A>,__2<K,__3<Tensor,K,I,A>,B>> f) {
        return ccc.curry(f.apply(ccc.dot(ccc.exr(),cast)));
    }

    public <X,I,A,B,C> __2<K, I, __3<Hom, K, A, __3<Hom, K, B, C>>> lam2Curried(__2<K,X,__3<Tensor,K,I,A>> cast, F1<__2<K,X,A>,F1<__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,B>,__2<K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>>> f) {
        return lam(cast, (__2<K, X, A> x) -> lam(ccc.identity(), f.apply(x)));
    }

    public <X,I,A,B,C,D> __2<K,I,__3<Hom,K,A,__3<Hom,K,B,__3<Hom,K,C,D>>>> lam3Curried(__2<K,X,__3<Tensor,K,I,A>> cast, F1<__2<K,X,A>,F1<__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,B>,F1<__2<K, __3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>, C>, __2<K, __3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>, D>>>> f) {
        return this.lam(
            cast,
            (__2<K, X, A> x) ->
                lam(
                    ccc.exl(),
                    (__2<K,__3<Tensor,K,__3<Tensor,K,__3<Tensor,K,I,A>,B>,C>,B> x2) ->
                        lam(ccc.identity(), f.apply(x).apply(x2))
                )
        );
    }

    private void test() {
        // type checking
        apply(lam2Curried(x -> y -> x), null);
        apply(lam2Curried(ccc.exl(), x -> y -> x), null);
        apply(apply(lam3Curried(ccc.dot(ccc.exl(), ccc.exl()), x -> y -> z -> y), null), null);
    }

    // ($) :: forall k i a b. CCC k => k i (Hom k a b) -> k i a -> k i b
    // ($) f x = eval <<< fork f x
    public <I,A,B> __2<K,I,B> apply(__2<K,I,__3<Hom,K,A,B>> f, __2<K,I,A> a) {
        return ccc.dot(ccc.eval(), ccc.fork(f, a));
    }

    // liftCCC :: forall k i a b. CCC k => k a b -> k i a -> k i b
    // liftCCC = (.)
    public <I,A,B> __2<K,I,B> liftCCC(__2<K,A,B> ab, __2<K,I,A> a) {
        return ccc.dot(ab, a);
    }
}
