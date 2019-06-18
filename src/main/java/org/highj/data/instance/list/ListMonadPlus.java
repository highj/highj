package org.highj.data.instance.list;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.typeclass1.monad.MonadPlus;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.typeclass1.monad.MonadZip;
import org.highj.util.Mutable;

import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.highj.Hkt.asList;

public interface ListMonadPlus extends ListFunctor, MonadPlus<List.µ>, MonadRec<List.µ>, MonadZip<List.µ> {

    @Override
    default <A> List<A> pure(A a) {
        return List.of(a);
    }

    @Override
    default <A, B> List<B> ap(__<List.µ, Function<A, B>> fn, __<List.µ, A> nestedA) {
        List<Function<A, B>> listFn = asList(fn);
        List<A> listA = asList(nestedA);
        Stack<B> stack = new Stack<>();
        for (Function<A, B> f : listFn) {
            for (A a : listA) {
                stack.push(f.apply(a));
            }
        }
        return List.buildFromStack(stack);
    }

    @Override
    default <A> List<A> join(__<List.µ, __<List.µ, A>> nestedNestedA) {
        List<__<List.µ, A>> nestedList = asList(nestedNestedA);
        Stack<A> stack = new Stack<>();
        for (__<List.µ, A> list : nestedList) {
            for (A a : asList(list)) {
                stack.push(a);
            }
        }
        return List.buildFromStack(stack);
    }

    @Override
    default <A> List<A> mzero() {
        return List.Nil();
    }

    @Override
    default <A> List<A> mplus(__<List.µ, A> one, __<List.µ, A> two) {
        List<A> listOne = asList(one);
        List<A> listTwo = asList(two);
        return List.append(listOne, listTwo);
    }

    @Override
    default <A, B> List<B> tailRec(Function<A, __<List.µ, Either<A, B>>> function, A startValue) {
        List<Either<A, B>> step = List.of(Either.Left(startValue));
        Mutable<Boolean> hasChanged = Mutable.newMutable();
        do {
            hasChanged.set(false);
            step = step.concatMap(e -> e.either(
                    left -> {
                        hasChanged.set(true);
                        return asList(function.apply(left));
                    },
                    right -> List.of(e)
            ));
        } while (hasChanged.get());
        return Either.rights(step);
    }

    @Override
    default <A, B, C> __<List.µ, C> mzipWith(BiFunction<A, B, C> fn, __<List.µ, A> ma, __<List.µ, B> mb) {
        return List.zipWith(Hkt.asList(ma), Hkt.asList(mb), fn);
    }
}
