package org.highj.typeclass2.category;

import org.derive4j.hkt.TypeEq;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.Hkt;
import org.highj.data.Maybe;
import org.highj.function.NF;

public abstract class LCData<Repr,Hom,A> implements __3<LCData.µ,Repr,Hom,A> {
    public static final class µ {}

    public interface Cases<R,Repr,Hom,A> {
        R lam(Lam<Repr,Hom,?,?,A> lam);
        R ap(Ap<Repr,Hom,?,A> ap);
        R lift(Lift<Repr,Hom,?,?,A> lift);
        R var(int id);
    }

    public static class CasesAdapter<R,Repr,Hom,A> implements Cases<R,Repr,Hom,A> {
        private final R default_;
        public CasesAdapter(R default_) {
            this.default_ = default_;
        }
        @Override
        public R lam(Lam<Repr, Hom, ?, ?, A> lam) {
            return default_;
        }
        @Override
        public R ap(Ap<Repr, Hom, ?, A> ap) {
            return default_;
        }
        @Override
        public R lift(Lift<Repr, Hom, ?, ?, A> lift) {
            return default_;
        }
        @Override
        public R var(int id) {
            return default_;
        }
    }

    public abstract <R> R match(Cases<R,Repr,Hom,A> cases);

    public static <Repr,Hom,A,B> LCData<Repr,Hom,__3<Hom,Repr,A,B>> lam(int paramIdent, LCData<Repr,Hom,B> body) {
        return new LCData<Repr,Hom,__3<Hom,Repr,A,B>>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, __3<Hom, Repr, A, B>> cases) {
                return cases.lam(new LCData.Lam<>(TypeEq.refl(), paramIdent, body));
            }
        };
    }

    public static <Repr,Hom,A,B> LCData<Repr,Hom,B> ap(LCData<Repr,Hom,__3<Hom,Repr,A,B>> f, LCData<Repr,Hom,A> a) {
        return new LCData<Repr,Hom,B>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, B> cases) {
                return cases.ap(new Ap<>(f, a));
            }
        };
    }

    public static <Repr,Hom,A,B> LCData<Repr,Hom,__3<Hom,Repr,A,B>> lift(__2<Repr,A,B> f) {
        return new LCData<Repr,Hom,__3<Hom,Repr,A,B>>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, __3<Hom, Repr, A, B>> cases) {
                return cases.lift(new Lift<>(TypeEq.refl(), f));
            }
        };
    }

    public static <Repr,Hom,A> LCData<Repr,Hom,A> var(int id) {
        return new LCData<Repr,Hom,A>() {
            @Override
            public <R> R match(Cases<R, Repr, Hom, A> cases) {
                return cases.var(id);
            }
        };
    }

    public static class Lam<Repr,Hom,A,B,C> {
        private final TypeEq<C,__3<Hom,Repr,A,B>> _leibniz;
        private final int _paramIdent;
        private final LCData<Repr,Hom,B> _body;

        public Lam(TypeEq<C,__3<Hom,Repr,A,B>> leibniz, int paramIdent, LCData<Repr,Hom,B> body) {
            this._leibniz = leibniz;
            this._paramIdent = paramIdent;
            this._body = body;
        }

        public TypeEq<C, __3<Hom, Repr, A, B>> leibniz() {
            return _leibniz;
        }

        public int paramIdent() {
            return _paramIdent;
        }

        public LCData<Repr, Hom, B> body() {
            return _body;
        }
    }

    public static class Ap<Repr,Hom,A,B> {
        private final LCData<Repr,Hom,__3<Hom,Repr,A,B>> _f;
        private final LCData<Repr,Hom,A> _a;

        public Ap(LCData<Repr,Hom,__3<Hom,Repr,A,B>> f, LCData<Repr,Hom,A> a) {
            this._f = f;
            this._a = a;
        }

        public LCData<Repr, Hom, __3<Hom, Repr, A, B>> f() {
            return _f;
        }

        public LCData<Repr, Hom, A> a() {
            return _a;
        }
    }

    public static class Lift<Repr,Hom,A,B,C> {
        private final TypeEq<C,__3<Hom,Repr,A,B>> _leibniz;
        private final __2<Repr,A,B> _f;

        public Lift(TypeEq<C,__3<Hom,Repr,A,B>> leibniz, __2<Repr,A,B> f) {
            this._leibniz = leibniz;
            this._f = f;
        }

        public TypeEq<C, __3<Hom, Repr, A, B>> leibniz() {
            return _leibniz;
        }

        public __2<Repr, A, B> f() {
            return _f;
        }
    }

    private LCData<Repr,Hom,A> substDepthFirst(NF<__<__<µ,Repr>,Hom>,__<__<µ,Repr>,Hom>> nf) {
        return Hkt.asLCData(nf.apply(match(
            new Cases<__<__<__<µ,Repr>,Hom>,A>,Repr,Hom,A>() {
                @Override
                public __<__<__<µ, Repr>, Hom>, A> lam(Lam<Repr, Hom, ?, ?, A> lam) {
                    return substDepthFirstLam(nf, lam);
                }
                @Override
                public __<__<__<µ, Repr>, Hom>, A> ap(Ap<Repr, Hom, ?, A> ap) {
                    return substDepthFirstAp(nf, ap);
                }
                @Override
                public __<__<__<µ, Repr>, Hom>, A> lift(Lift<Repr, Hom, ?, ?, A> lift) {
                    return LCData.this;
                }
                @Override
                public __<__<__<µ, Repr>, Hom>, A> var(int id) {
                    return LCData.this;
                }
            }
        )));
    }

    private static <Repr,Hom,A,B,C> __<__<__<µ,Repr>,Hom>,C> substDepthFirstLam(NF<__<__<µ,Repr>,Hom>,__<__<µ,Repr>,Hom>> nf, Lam<Repr,Hom,A,B,C> lam) {
        return lam.leibniz().symm().subst(LCData.lam(lam.paramIdent(), lam.body().substDepthFirst(nf)));
    }

    private static <Repr,Hom,A,B> __<__<__<µ,Repr>,Hom>,B> substDepthFirstAp(NF<__<__<µ,Repr>,Hom>,__<__<µ,Repr>,Hom>> nf, Ap<Repr,Hom,A,B> ap) {
        return LCData.ap(ap.f().substDepthFirst(nf), ap.a().substDepthFirst(nf));
    }

    public static <K, Tensor, Hom, U, A, B> Maybe<__2<K,A,B>> toCCC(CCC<K,Tensor,Hom,U> ccc, LCData<K,Hom,__3<Hom,K,A,B>> lcData) {
        return lcData.match(new LCData.Cases<Maybe<__2<K,A,B>>,K,Hom,__3<Hom,K,A,B>>() {
            @Override
            public Maybe<__2<K, A, B>> lam(Lam<K, Hom, ?, ?, __3<Hom,K,A,B>> lam) {
                return lamToCCC(ccc, lam).map(x -> {
                    //noinspection unchecked,UnnecessaryLocalVariable
                    __2<K,A,B> x2 = (__2)x;
                    return x2;
                });
            }
            @Override
            public Maybe<__2<K, A, B>> ap(Ap<K, Hom, ?, __3<Hom,K,A,B>> ap) {
                return Maybe.Nothing();
            }
            @Override
            public Maybe<__2<K, A, B>> lift(Lift<K, Hom, ?, ?, __3<Hom,K,A,B>> lift) {
                //noinspection unchecked
                __2<K,A,B> x = (__2)lift.f();
                return Maybe.Just(x);
            }
            @Override
            public Maybe<__2<K, A, B>> var(int id) {
                return Maybe.Nothing();
            }
        });
    }

    private static <K,Tensor,Hom,U,A,B,C> Maybe<__2<K,A,B>> lamToCCC(CCC<K,Tensor,Hom,U> ccc, Lam<K,Hom,A,B,C> lam) {
        int paramIdent = lam.paramIdent();
        return lam.body().match(new LCData.Cases<Maybe<__2<K,A,B>>,K,Hom,B>() {
            @Override
            public Maybe<__2<K, A, B>> lam(Lam<K, Hom, ?, ?, B> lam) {
                return lamLamToCCC(ccc, paramIdent, lam);
            }
            @Override
            public Maybe<__2<K, A, B>> ap(Ap<K, Hom, ?, B> ap) {
                return lamApToCee(ccc, paramIdent, ap);
            }
            @Override
            public Maybe<__2<K, A, B>> lift(Lift<K, Hom, ?, ?, B> lift) {
                return lamLiftToCCC(ccc, lift);
            }
            @Override
            public Maybe<__2<K, A, B>> var(int id) {
                if (id == paramIdent) {
                    //noinspection unchecked
                    __2<K,A,B> x = (__2)ccc.identity();
                    return Maybe.Just(x);
                }
                return Maybe.Nothing();
            }
        });
    }

    private static <K,Tensor,Hom,U,A,B,C,D> Maybe<__2<K,D,C>> lamLamToCCC(CCC<K,Tensor,Hom,U> ccc, int paramIdent, Lam<K,Hom,A,B,C> lam) {
        int paramIdent2 = lam.paramIdent();
        //noinspection unchecked
        return (Maybe)toCCC(ccc, LCData.lam(paramIdent, lamLamSubtFstSnd(ccc, paramIdent, paramIdent2, lam.body())));
    }

    private static <K,Tensor,Hom,U,A> LCData<K,Hom,A> lamLamSubtFstSnd(CCC<K,Tensor,Hom,U> ccc, int paramIdent1, int paramIdent2, LCData<K,Hom,A> body) {
        return body.substDepthFirst(lamLamSubFstSndNF(ccc, paramIdent1, paramIdent2));
    }

    private static <K,Tensor,Hom,U> NF<__<__<µ, K>,Hom>,__<__<µ, K>,Hom>> lamLamSubFstSndNF(CCC<K,Tensor,Hom,U> ccc, int paramIdent1, int paramIdent2) {
        return new NF<__<__<µ, K>,Hom>,__<__<µ, K>,Hom>>() {
            @Override
            public <A> __<__<__<LCData.µ, K>, Hom>, A> apply(__<__<__<LCData.µ, K>, Hom>, A> this_) {
                return Hkt.asLCData(this_).match(new CasesAdapter<__<__<__<LCData.µ, K>, Hom>, A>, K,Hom,A>(this_) {
                    @Override
                    public __<__<__<LCData.µ, K>, Hom>, A> var(int id) {
                        if (id == paramIdent1) {
                            return LCData.ap(fst(ccc), LCData.var(paramIdent1));
                        } else if (id == paramIdent2) {
                            return LCData.ap(snd(ccc), LCData.var(paramIdent1));
                        }
                        return this_;
                    }
                });
            }
        };
    }

    private static <K,Tensor,Hom,U,A,B> LCData<K,Hom,__3<Hom, K, __3<Tensor, K, A, B>, A>> fst(CCC<K,Tensor,Hom,U> ccc) {
        return LCData.lift(ccc.exl());
    }

    private static <K,Tensor,Hom,U,A,B> LCData<K,Hom,__3<Hom, K, __3<Tensor, K, A, B>, B>> snd(CCC<K,Tensor,Hom,U> ccc) {
        return LCData.lift(ccc.exr());
    }

    private static <K,Tensor,Hom,U,A,B,C> Maybe<__2<K,C,B>> lamApToCee(CCC<K,Tensor,Hom,U> ccc, int paramIdent, Ap<K,Hom,A,B> ap) {
        return Hkt.asMaybe(Maybe.monad.apply2(
            (__2<K, C, __3<Hom, K, A, B>> x1) -> (__2<K, C, A> x2) ->
                ccc.dot(ccc.eval(), ccc.fork(x1, x2)),
            toCCC(ccc, LCData.<K, Hom, C, __3<Hom, K, A, B>>lam(paramIdent, ap.f())),
            toCCC(ccc, LCData.<K, Hom, C, A>lam(paramIdent, ap.a()))
        ));
    }

    private static <K,Tensor,Hom,U,A,B,C,D> Maybe<__2<K,D,C>> lamLiftToCCC(CCC<K,Tensor,Hom,U> ccc, Lift<K,Hom,A,B,C> lift) {
        __<__<K, D>, C> x = lift.leibniz().symm().subst(ccc.curry(ccc.dot(lift.f(), ccc.exr())));
        // HigherKinded.uncurry2 could be used here!, but no longer available.
        return Maybe.Just((__2<K,D,C>)x);
    }
}
