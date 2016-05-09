package org.highj.data.collection.list;

import org.derive4j.hkt.__;
import org.highj.data.collection.Either;
import org.highj.data.collection.List;
import org.highj.typeclass1.monad.MonadPlus;
import org.highj.typeclass1.monad.MonadRec;
import org.highj.util.Mutable;

import java.util.Stack;
import java.util.function.Function;

public interface ListMonadPlus extends ListFunctor, MonadPlus<List.µ>, MonadRec<List.µ> {

    @Override
    default <A> List<A> pure(A a) {
        return List.of(a);
    }

    @Override
    default <A, B> List<B> ap(__<List.µ, Function<A, B>> fn, __<List.µ, A> nestedA) {
        List<Function<A, B>> listFn = List.narrow(fn);
        List<A> listA = List.narrow(nestedA);
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
        List<__<List.µ, A>> nestedList = List.narrow(nestedNestedA);
        Stack<A> stack = new Stack<>();
        for (__<List.µ, A> list : nestedList) {
            for (A a : List.narrow(list)) {
                stack.push(a);
            }
        }
        return List.buildFromStack(stack);
    }

    @Override
    default <A> List<A> mzero() {
        return List.nil();
    }

    @Override
    default <A> List<A> mplus(__<List.µ, A> one, __<List.µ, A> two) {
        List<A> listOne = List.narrow(one);
        List<A> listTwo = List.narrow(two);
        return List.append(listOne, listTwo);
    }

    @Override
    default <A, B> List<B> tailRec(Function<A, __<List.µ, Either<A, B>>> function, A startValue) {
        List<Either<A, B>> step = List.of(Either.newLeft(startValue));
        Mutable<Boolean> hasChanged = Mutable.newMutable();
        do {
            hasChanged.set(false);
            step = step.concatMap(e -> e.either(
                    left -> {
                        hasChanged.set(true);
                        return List.narrow(function.apply(left));
                    },
                    right -> List.of(e)
            ));
        } while (hasChanged.get());
        return Either.rights(step);
    }
}
