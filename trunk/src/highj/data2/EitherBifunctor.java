/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.F;
import fj.data.Either;
import highj.__;
import highj.typeclasses.category2.BifunctorAbstract;

/**
 *
 * @author DGronau
 */
public class EitherBifunctor extends BifunctorAbstract<EitherOf> {
    
    private static EitherBifunctor INSTANCE = new EitherBifunctor();

    @Override
    // bimap (Data.Bifunctor)
    public <A, B, C, D> __<EitherOf, B, D> bimap(F<A, B> fn1, F<C, D> fn2, __<EitherOf, A, C> nestedAC) {
        Either<A, C> either = EitherOf.unwrap(nestedAC);
        if(either.isLeft()) {
            return EitherOf.<B,D>wrap(Either.<B,D>left(fn1.f(either.left().value())));
        } else {
            return EitherOf.<B,D>wrap(Either.<B,D>right(fn2.f(either.right().value())));
        }
    }

    public static EitherBifunctor getInstance() {
        return INSTANCE;
    }
 
}
