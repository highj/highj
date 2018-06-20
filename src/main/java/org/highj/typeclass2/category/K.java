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
        return new K<>(go(untyped));
    }

    private static Untyped go(Untyped u) {
        return u.asCurry().map(curry -> {
            Untyped x = go(curry.untyped);
            return x.asUncurry().map(uncurry -> uncurry.untyped).getOrElse(() -> Untyped.Curry(x));
        }).orElse(
            u.asUncurry().map(uncurry -> {
                Untyped x = go(uncurry.untyped);
                return x.asCurry().map(curry -> curry.untyped).getOrElse(() -> Untyped.Uncurry(x));
            })
        ).orElse(
            u.asFork().map(fork -> {
                Untyped fst = go(fork.first);
                Untyped snd = go(fork.second);
                return fst.isExl() && snd.isExr() ? Untyped.Id() : Untyped.Fork(fst, snd);
            })
        ).orElse(
            u.asCompose().map(compose -> {
                Untyped fst = go(compose.first);
                Untyped snd = go(compose.second);
                return fst.isId() ? snd
                           : snd.isId() ? fst
                                 : Untyped.Compose(fst, snd);
            })
        ).getOrElse(u);
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

