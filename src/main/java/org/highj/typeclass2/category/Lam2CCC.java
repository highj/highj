package org.highj.typeclass2.category;

import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.function.F1;

public class Lam2CCC<K,Tensor,Hom,Unit> {
    private final CCC<K,Tensor,Hom,Unit> ccc;

    private Lam2CCC(CCC<K,Tensor,Hom,Unit> ccc) {
        this.ccc = ccc;
    }

    public static <K,Tensor,Hom,Unit> Lam2CCC<K,Tensor,Hom,Unit> create(CCC<K,Tensor,Hom,Unit> ccc) {
        return new Lam2CCC<>(ccc);
    }

    /*
    lam :: forall k i a b. CCC k => ((forall x. Cast k x (Tensor k i a) => k x a) -> k (Tensor k i a) b) -> k i (Hom k a b)
    lam f = curry (f exr_) where
      exr_ :: forall x. Cast k x (Tensor k i a) => k x a
      exr_ = exr . (cast :: k x (Tensor k i a))
    */
    /* ??? (Requires Cast but if we use it, then the end-user needs to keep supplying it. It would not be implicit).
    public <I,X,A,B> __2<K,I,__3<Hom,K,A,B>> lam(F1<__2<K,__3<X,A>,__2<K,__3<Tensor,K,I,A>,B>> f) {}
    */

    public <I,A,B> __2<K,I,__3<Hom,K,A,B>> lam_1(F1<__2<K,__3<Tensor,K,I,A>,A>,__2<K,__3<Tensor,K,I,A>,B>> f) {
        return ccc.curry(f.apply(ccc.exr()));
    }

    public <I,X,A,B> __2<K,I,__3<Hom,K,A,B>> lam_2(F1<__2<K,__3<Tensor,K,X,__3<Tensor,K,I,A>>,A>,__2<K,__3<Tensor,K,I,A>,B>> f) {
        return ccc.curry(f.apply(ccc.dot(ccc.exr(), ccc.exr())));
    }

    public <I,X1,X2,A,B> __2<K,I,__3<Hom,K,A,B>> lam_3(F1<__2<K,__3<Tensor,K,X1,__3<Tensor,K,X2,__3<Tensor,K,I,A>>>,A>,__2<K,__3<Tensor,K,I,A>,B>> f) {
        return ccc.curry(f.apply(ccc.dot(ccc.exr(), ccc.dot(ccc.exr(), ccc.exr()))));
    }

    // ($) :: forall k i a b. CCC k => k i (Hom k a b) -> k i a -> k i b
    // ($) f x = eval <<< fork f x
    public <I,A,B> __2<K,I,B> apply(__2<K,I,__3<Hom,K,A,B>> f, __2<K,I,A> a) {
        return ccc.dot(ccc.eval(), ccc.fork(f, a));
    }
}
