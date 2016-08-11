package org.highj.data.transformer.list;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.transformer.ListT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.util.Mutable;

import java.util.function.Function;

public interface ListTMonadRec<M> extends ListTMonad<M>, MonadRec<__<ListT.µ, M>> {
    @Override
    Monad<M> get();

    @Override
    default <A, B> __<__<ListT.µ, M>, B> tailRec(Function<A, __<__<ListT.µ, M>, Either<A, B>>> function, A startValue) {
        ListT<M,Either<A, B>> step = ListT.singleton(get(), Either.Left(startValue));
        Mutable<Boolean> hasChanged = Mutable.newMutable();
        do {
            hasChanged.set(false);
            step = bind(step, e -> e.either(
                    left -> {
                        hasChanged.set(true);
                        return ListT.narrow(function.apply(left));
                    },
                    right -> ListT.singleton(get(), e)
            ));
        } while (hasChanged.get());
        return step.mapMaybe(get(), Either::maybeRight);
    }
}
