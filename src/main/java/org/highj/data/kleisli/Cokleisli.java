package org.highj.data.kleisli;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.kleisli.cokleisli.CokleisliArrow;
import org.highj.data.kleisli.cokleisli.CokleisliFunctor;
import org.highj.typeclass1.comonad.Comonad;

import java.util.function.Function;

public class Cokleisli<W, A, B> implements ___<Cokleisli.µ, W, A, B>, Function<_<W, A>, B> {
    public static interface µ {
    }

    private final Function<_<W, A>, B> fun;

    public Cokleisli(Function<_<W, A>, B> fun) {
        this.fun = fun;
    }

    @Override
    public B apply(_<W, A> a) {
        return fun.apply(a);
    }

    @SuppressWarnings("unchecked")
    public static <W, A, B> Cokleisli<W, A, B> narrow(_<__.µ<___.µ<µ, W>, A>, B> nested) {
        return (Cokleisli) nested;
    }

    public static <W> CokleisliArrow<W> arrow(Comonad<W> comonad) {
        return new CokleisliArrow<W>(comonad);
    }

    public static <W, S> CokleisliFunctor<W, S> functor() {
        return new CokleisliFunctor<>();
    }

}
