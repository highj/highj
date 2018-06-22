package org.highj.typeclass2.category;

import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.Hkt;
import org.highj.typeclass2.arrow.Category;

import static org.highj.typeclass2.category.Untyped.Eval;

public final class K<A_, B_> implements __2<K, A_, B_> {

    public final Untyped untyped;

    public K(Untyped untyped) {
        this.untyped = untyped;
    }

    public K<A_, B_> optimize() {
        return new K<>(optimizeGo(untyped));
    }

    private static Untyped optimizeGo(Untyped u) {
        return u.asCurry().map(curry -> {
            Untyped x = optimizeGo(curry.untyped);
            return x.asUncurry().map(uncurry -> uncurry.untyped).getOrElse(() -> Untyped.Curry(x));
        }).orElse(
            () -> u.asUncurry().map(uncurry -> {
                Untyped x = optimizeGo(uncurry.untyped);
                return x.asCurry().map(curry -> curry.untyped).getOrElse(() -> Untyped.Uncurry(x));
            })
        ).orElse(
            () -> u.asFork().map(fork -> {
                Untyped fst = optimizeGo(fork.first);
                Untyped snd = optimizeGo(fork.second);
                return fst.isExl() && snd.isExr() ? Untyped.Id() : Untyped.Fork(fst, snd);
            })
        ).orElse(
            () -> u.asCompose().map(compose -> {
                Untyped fst = optimizeGo(compose.first);
                Untyped snd = optimizeGo(compose.second);
                return fst.isId() ? snd
                           : snd.isId() ? fst
                                 : Untyped.Compose(fst, snd);
            })
        ).getOrElse(u);
    }

    public String prettyPrint() {
        return prettyPrintGo(0, untyped);
    }

    private static String prettyPrintGo(int d, Untyped u) {
        if (u.isExl()) {
            return "fst";
        }
        if (u.isExr()) {
            return "snd";
        }
        if (u.isId()) {
            return "id";
        }
        if (u.isEval()) {
            return "uncurry id";
        }
        return u.asCurry().map(
            curry -> parensIf(d > 10, "curry " + prettyPrintGo(11, curry.untyped))
        ).orElse(
            () -> u.asUncurry().map(
                uncurry -> parensIf(d > 10, "uncurry " + prettyPrintGo(11, uncurry.untyped)))
        ).orElse(
            () -> u.asFork().map(
                fork -> parensIf(d > 3,
                    prettyPrintGo(4, fork.first) + " &&& " + prettyPrintGo(3, fork.second)))
        ).orElse(
            () -> u.asCompose().map(
                compose -> parensIf(d > 9,
                    prettyPrintGo(10, compose.first) + " . " + prettyPrintGo(9, compose.second)))
        ).get();
    }

    private static String parensIf(boolean check, String s) {
        return check ? "(" + s + ")" : s;
    }

    public final static Category<K> category = new KCategory();

    public static <T, H, U> CCC<K, T, H, U> ccc() {
        return new KCCC<>();
    }

    private static class KCategory implements Category<K> {

        @Override
        public <B> __2<K, B, B> identity() {
            return new K<>(Untyped.Id());
        }

        @Override
        public <B, C, D> __2<K, B, D> dot(__2<K, C, D> cd, __2<K, B, C> bc) {
            K<C, D> first = Hkt.asK(cd);
            K<B, C> second = Hkt.asK(bc);
            return new K<>(Untyped.Compose(first.untyped, second.untyped));
        }
    }

    private static class KCCC<T, H, U> extends KCategory implements CCC<K, T, H, U> {

        @Override
        public <A, B> __2<K, __3<T, K, __3<H, K, A, B>, A>, B> eval() {
            return new K<>(Eval());
        }

        @Override
        public <A, B, C> __2<K, A, __3<H, K, B, C>> curry(__2<K, __3<T, K, A, B>, C> v) {
            K<__3<T, K, A, B>, C> k = Hkt.asK(v);
            return new K<>(Untyped.Curry(k.untyped));
        }

        @Override
        public <A, B, C> __2<K, __3<T, K, A, B>, C> uncurry(__2<K, A, __3<H, K, B, C>> v) {
            K<A, __3<H, K, B, C>> k = Hkt.asK(v);
            return new K<>(Untyped.Uncurry(k.untyped));
        }

        @Override
        public <A, C, D> __2<K, A, __3<T, K, C, D>> fork(__2<K, A, C> first, __2<K, A, D> second) {
            K<A, C> fst = Hkt.asK(first);
            K<A, D> snd = Hkt.asK(second);
            return new K<>(Untyped.Fork(fst.untyped, snd.untyped));
        }

        @Override
        public <A, B> __2<K, __3<T, K, A, B>, A> exl() {
            return new K<>(Untyped.Exl());
        }

        @Override
        public <A, B> __2<K, __3<T, K, A, B>, B> exr() {
            return new K<>(Untyped.Exr());
        }
    }
}

