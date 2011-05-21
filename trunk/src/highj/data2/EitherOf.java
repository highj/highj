/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.data.Either;
import highj.CL;
import highj.CR;
import highj._;
import highj.__;

/**
 *
 * @author DGronau
 */
public class EitherOf {
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

    public static <A, B> _<CL<EitherOf, A>, B> wrapCL(Either<A, B> either) {
        return CL.curry(wrap(either));
    }

    public static <A, B> _<CR<EitherOf, B>, A> wrapCR(Either<A, B> either) {
        return CR.curry(wrap(either));
    }
    
    public static <A, B> Either<A, B> unwrapCL(_<CL<EitherOf, A>, B> curried) {
        return unwrap(CL.uncurry(curried));
    }

    public static <A, B> Either<A, B> unwrapCR(_<CR<EitherOf, B>, A> curried) {
        return unwrap(CR.uncurry(curried));
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
