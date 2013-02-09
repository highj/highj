package org.highj.data;

import org.highj._;
import org.highj.__;
import org.highj.typeclass2.arrow.Category;

public class Dual<M, A, B> implements __<_<Dual.µ, M>, A, B> {

    public static class µ {}

    private final __<M, B, A> value;

    public Dual(__<M, B, A> value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <M, A, B> Dual<M, A, B> narrow(__<_<Dual.µ, M>, A, B> value) {
        return (Dual) value;
    }

    public __<M, B, A> get() {
        return value;
    }

    public static <M> Category<_<µ, M>> category(final Category<M> categoryM) {
        return new Category<_<µ, M>>() {
            @Override
            public <A> __<_<µ, M>, A, A> identity() {
                return new Dual<>(categoryM.<A>identity());
            }

            @Override
            public <A, B, C> __<_<µ, M>, A, C> dot(__<_<µ, M>, B, C> bc, __<_<µ, M>, A, B> ab) {
                Dual<M, B, C> bcDual = narrow(bc);
                Dual<M, A, B> abDual = narrow(ab);
                return new Dual<>(categoryM.dot(abDual.get(), bcDual.get()));
            }
        };
    }

}
