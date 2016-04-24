package org.highj.data.collection.list;

import org.highj._;
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
    default <A, B> List<B> ap(_<List.µ, Function<A, B>> fn, _<List.µ, A> nestedA) {
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
    default <A> List<A> join(_<List.µ, _<List.µ, A>> nestedNestedA) {
        List<_<List.µ, A>> nestedList = List.narrow(nestedNestedA);
        Stack<A> stack = new Stack<>();
        for (_<List.µ, A> list : nestedList) {
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
    default <A> List<A> mplus(_<List.µ, A> one, _<List.µ, A> two) {
        List<A> listOne = List.narrow(one);
        List<A> listTwo = List.narrow(two);
        return List.append(listOne, listTwo);
    }

    @Override
    default <A, B> List<B> tailRec(Function<A, _<List.µ, Either<A, B>>> function, A startValue) {
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
