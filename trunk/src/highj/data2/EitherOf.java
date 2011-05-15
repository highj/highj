/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.data.Either;
import highj.TC2;
import highj.__;
import highj.__.Accessor2;

/**
 *
 * @author DGronau
 */
public class EitherOf implements TC2<EitherOf> {
       private Accessor2<EitherOf> accessor;
    
   public EitherOf() {
       __.register(this);
   }
    
    public <A, B> __<EitherOf, A, B> left(A a) {
        return wrap(Either.<A,B>left(a));
    }

    public <A, B> __<EitherOf, A, B> right(B b) {
        return wrap(Either.<A,B>right(b));
    }

    
    public <A, B> __<EitherOf, A, B> wrap(Either<A, B> either) {
        return accessor.make(either);
    }

    public <A, B> Either<A, B> unwrap(__<EitherOf, A, B> wrapped) {
        return (Either<A, B>) accessor.read(wrapped);
    }

    public boolean isLeft(__<EitherOf, ?, ?> wrapped) {
        return unwrap(wrapped).isLeft();
    }

    public boolean isRight(__<EitherOf, ?, ?> wrapped) {
        return unwrap(wrapped).isLeft();
    }

    public String toString(__<EitherOf, ?, ?> wrapped) {
        Either<?, ?> either = unwrap(wrapped);
        if (either.isLeft()) {
            return "Left(" + either.left().value() + ")";
        } else {
            return "Right(" + either.right().value() + ")";
        }
    }
    
    private static final EitherOf INSTANCE = new EitherOf();

    public static EitherOf getInstance() {
        return INSTANCE;
    }

    @Override
    public void setAccessor(Accessor2<EitherOf> accessor) {
        assert this.accessor == null;
        this.accessor = accessor;
    }

}
