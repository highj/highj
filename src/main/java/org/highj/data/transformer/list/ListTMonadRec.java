package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;

import java.util.function.Function;

import static org.highj.Hkt.asListT;

public interface ListTMonadRec<M> extends ListTMonad<M>, MonadRec<__<ListT.µ, M>> {
    @Override
    Monad<M> get();

    @Override
    default <A, B> __<__<ListT.µ, M>, B> tailRec(Function<A, __<__<ListT.µ, M>, Either<A, B>>> function, A startValue) {
        return bind(function.apply(startValue),
                e -> e.either(
                        a2 -> ListT.wrapLazy(get(), () -> asListT(tailRec(function, a2))),
                        this::pure));
    }
}
