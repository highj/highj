package org.highj.typeclass2.category;

import org.derive4j.hkt.TypeEq;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.Hkt;
import org.highj.data.Maybe;
import org.highj.data.tuple.T2;

import java.util.function.Function;

public abstract class LC<Repr,Hom,A> {
    public interface Cases<R,Repr,Hom,A> {
        R lam(Lam<Repr,Hom,?,?,A> lam);
        R ap(Ap<Repr,Hom,?,A> ap);
        R lift(Lift<Repr,Hom,?,?,A> lift);
        R var(int id);
    }

    public abstract <R> R match(Cases<R,Repr,Hom,A> cases);

    public static <Repr,Hom,A,B> LC<Repr,Hom,__3<Hom,Repr,A,B>> lam(Function<LC<Repr,Hom,A>,LC<Repr,Hom,B>> f) {
        return new LC<Repr,Hom,__3<Hom,Repr,A,B>>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, __3<Hom, Repr, A, B>> cases) {
                return cases.lam(new Lam<>(TypeEq.refl(), f));
            }
        };
    }

    public static <Repr,Hom,A,B> LC<Repr,Hom,B> ap(LC<Repr,Hom,__3<Hom,Repr,A,B>> f, LC<Repr,Hom,A> a) {
        return new LC<Repr,Hom,B>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, B> cases) {
                return cases.ap(new Ap<>(f, a));
            }
        };
    }

    public static <Repr,Hom,A,B> LC<Repr,Hom,__3<Hom,Repr,A,B>> lift(__2<Repr,A,B> f) {
        return new LC<Repr,Hom,__3<Hom,Repr,A,B>>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, __3<Hom, Repr, A, B>> cases) {
                return cases.lift(new Lift<>(TypeEq.refl(), f));
            }
        };
    }

    public static <Repr,Hom,A> LC<Repr,Hom,A> var(int id) {
        return new LC<Repr,Hom,A>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, A> cases) {
                return cases.var(id);
            }
        };
    }

    public static class Lam<Repr,Hom,A,B,C> {
        private TypeEq<C,__3<Hom,Repr,A,B>> _typeEq;
        private Function<LC<Repr,Hom,A>,LC<Repr,Hom,B>> _f;

        public Lam(TypeEq<C,__3<Hom,Repr,A,B>> typeEq, Function<LC<Repr,Hom,A>,LC<Repr,Hom,B>> f) {
            this._typeEq = typeEq;
            this._f = f;
        }

        public TypeEq<C, __3<Hom, Repr, A, B>> typeEq() {
            return _typeEq;
        }

        public Function<LC<Repr, Hom, A>, LC<Repr, Hom, B>> f() {
            return _f;
        }
    }

    public static class Ap<Repr,Hom,A,B> {
        private LC<Repr,Hom,__3<Hom,Repr,A,B>> _f;
        private LC<Repr,Hom,A> _a;

        public Ap(LC<Repr,Hom,__3<Hom,Repr,A,B>> f, LC<Repr,Hom,A> a) {
            this._f = f;
            this._a = a;
        }

        public LC<Repr, Hom, __3<Hom, Repr, A, B>> f() {
            return _f;
        }

        public LC<Repr, Hom, A> a() {
            return _a;
        }
    }

    public static class Lift<Repr,Hom,A,B,C> {
        private TypeEq<C,__3<Hom,Repr,A,B>> _typeEq;
        private __2<Repr,A,B> _f;

        public Lift(TypeEq<C,__3<Hom,Repr,A,B>> typeEq, __2<Repr,A,B> f) {
            this._typeEq = typeEq;
            this._f = f;
        }

        public TypeEq<C, __3<Hom, Repr, A, B>> typeEq() {
            return _typeEq;
        }

        public __2<Repr, A, B> f() {
            return _f;
        }
    }

    public LCData<Repr,Hom,A> toLCData() {
        return toLCData(0)._1();
    }
    
    private T2<LCData<Repr,Hom,A>,Integer> toLCData(int nextId) {
        return match(new Cases<T2<LCData<Repr,Hom,A>,Integer>,Repr,Hom,A>() {
            @Override
            public T2<LCData<Repr, Hom, A>, Integer> lam(Lam<Repr, Hom, ?, ?, A> lam) {
                return lamToLCData(nextId, lam);
            }
            @Override
            public T2<LCData<Repr, Hom, A>, Integer> ap(Ap<Repr, Hom, ?, A> ap) {
                return apToLCData(nextId, ap);
            }
            @Override
            public T2<LCData<Repr, Hom, A>, Integer> lift(Lift<Repr, Hom, ?, ?, A> lift) {
                return liftToLCData(nextId, lift);
            }
            @Override
            public T2<LCData<Repr, Hom, A>, Integer> var(int id) {
                return T2.of(LCData.var(id), nextId);
            }
        });
    }

    private static <Repr,Hom,A,B,C> T2<LCData<Repr,Hom,C>,Integer> lamToLCData(int nextId, Lam<Repr, Hom, A, B, C> lam) {
        int paramId = nextId;
        int nextId2 = nextId + 1;
        T2<LCData<Repr,Hom,B>,Integer> body = lam.f().apply(LC.var(paramId)).toLCData(nextId2);
        int nextId3 = body._2();
        return T2.of(Hkt.asLCData(lam.typeEq().symm().subst(LCData.lam(paramId, body._1()))), nextId3);
    }

    private static <Repr,Hom,A,B> T2<LCData<Repr,Hom,B>,Integer> apToLCData(int nextId, Ap<Repr, Hom, A, B> ap) {
        T2<LCData<Repr,Hom,__3<Hom,Repr,A,B>>,Integer> f = ap.f().toLCData(nextId);
        int nextId2 = f._2();
        T2<LCData<Repr,Hom,A>,Integer> a = ap.a().toLCData(nextId2);
        int nextId3 = a._2();
        return T2.of(LCData.ap(f._1(), a._1()), nextId3);
    }

    private static <Repr,Hom,A,B,C> T2<LCData<Repr,Hom,C>,Integer> liftToLCData(int nextId, Lift<Repr,Hom,A,B,C> lift) {
        return T2.of(Hkt.asLCData(lift.typeEq().symm().subst(LCData.lift(lift.f()))), nextId);
    }

    @Override
    public String toString() {
        return toString(0)._1();
    }

    private T2<String,Integer> toString(int nextId) {
        return match(new Cases<T2<String,Integer>,Repr,Hom,A>() {
            @Override
            public T2<String,Integer> lam(Lam<Repr, Hom, ?, ?, A> lam) {
                int id = nextId;
                int nextId2 = nextId + 1;
                T2<String,Integer> body = lam.f().apply(LC.var(id)).toString(nextId2);
                int nextId3 = body._2();
                return T2.of("(\\v" + nextId + " -> " + body._1() + ")", nextId3);
            }
            @Override
            public T2<String,Integer> ap(Ap<Repr, Hom, ?, A> ap) {
                T2<String,Integer> f = ap.f().toString(nextId);
                int nextId2 = f._2();
                T2<String,Integer> a = ap.a().toString(nextId2);
                int nextId3 = a._2();
                return T2.of("(" + f._1() + " " + a._1() + ")", nextId3);
            }
            @Override
            public T2<String,Integer> lift(Lift<Repr, Hom, ?, ?, A> lift) {
                return T2.of(lift.f().toString(), nextId);
            }
            @Override
            public T2<String,Integer> var(int id) {
                return T2.of("v" + id, nextId);
            }
        });
    }

    public static <K, Tensor, Hom, U, A, B> Maybe<__2<K,A,B>> toCCC(CCC<K,Tensor,Hom,U> ccc, LC<K,Hom,__3<Hom,K,A,B>> lc) {
        return LCData.toCCC(ccc, lc.toLCData());
    }
}
