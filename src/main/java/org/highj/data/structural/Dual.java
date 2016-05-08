package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.data.structural.dual.DualCategory;
import org.highj.typeclass2.arrow.Category;

public class Dual<M, A, B> implements __3<Dual.µ, M, A, B> {

    public static class µ {}

    private final __2<M, B, A> value;

    public Dual(__2<M, B, A> value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static <M, A, B> Dual<M, A, B> narrow(__<__<__<µ, M>, A>, B> dual) {
        return (Dual) dual;
    }

    public __2<M, B, A> get() {
        return value;
    }

    public static <M,A,B> __2<M, B, A> get(__<__<__<µ, M>, A>, B> dual) {
        return narrow(dual).get();
    }

    public static <M> DualCategory<M> category(final Category<M> category) {
        return () -> category;
    }

}
