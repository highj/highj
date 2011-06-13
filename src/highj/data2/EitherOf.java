/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.data.Either;
import highj.LC;
import highj.RC;
import highj._;
import highj.__;

/**
 *
 * @author DGronau
 */
public final class EitherOf {
   private static final EitherOf hidden = new EitherOf();
    
   private EitherOf() {
   }
    
    public static <A, B> __<EitherOf, A, B> left(A a) {
        return wrap(Either.<A,B>left(a));
    }

    public static <A, B> __<EitherOf, A, B> right(B b) {
        return wrap(Either.<A,B>right(b));
    }

    
    public static <A, B> __<EitherOf, A, B> wrap(Either<A, B> either) {
        return new __<EitherOf, A, B>(hidden, either);
    }

    public static <A, B> Either<A, B> unwrap(__<EitherOf, A, B> wrapped) {
        return (Either<A, B>) wrapped.read(hidden);
    }

    public static <A, B> _<LC<EitherOf, A>, B> wrapLC(Either<A, B> either) {
        return wrap(either).leftCurry();
    }

    public static <A, B> _<RC<EitherOf, B>, A> wrapRC(Either<A, B> either) {
        return wrap(either).rightCurry();
    }
    
    public static <A, B> Either<A, B> unwrapLC(_<LC<EitherOf, A>, B> curried) {
        return unwrap(LC.uncurry(curried));
    }

    public static <A, B> Either<A, B> unwrapRC(_<RC<EitherOf, B>, A> curried) {
        return unwrap(RC.uncurry(curried));
    }    

    public static boolean isLeft(__<EitherOf, ?, ?> wrapped) {
        return unwrap(wrapped).isLeft();
    }

    public static boolean isRight(__<EitherOf, ?, ?> wrapped) {
        return unwrap(wrapped).isLeft();
    }

    public static String toString(__<EitherOf, ?, ?> wrapped) {
        Either<?, ?> either = unwrap(wrapped);
        if (either.isLeft()) {
            return "Left(" + either.left().value() + ")";
        } else {
            return "Right(" + either.right().value() + ")";
        }
    }
    
}
