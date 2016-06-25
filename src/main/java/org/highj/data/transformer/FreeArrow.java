package org.highj.data.transformer;

import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Flavour;
import org.derive4j.Visibility;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__4;
import org.highj.data.transformer.free_arrow.FreeArrowCategory;
import org.highj.data.transformer.free_arrow.FreeArrowSemigroupoid;
import org.highj.data.tuple.T2;
import org.highj.function.F1;
import org.highj.function.NF2;
import org.highj.typeclass2.arrow.Arrow;

@Data(value=@Derive(inClass = "FreeArrowImpl", withVisibility = Visibility.Package), flavour = Flavour.HighJ)
public abstract class FreeArrow<F,ARR,B,C> implements __4<FreeArrow.µ,F,ARR,B,C> {
    public static class µ {}
    
    public static <F,ARR,B,C> FreeArrow<F,ARR,B,C> narrow(__<__<__<__<FreeArrow.µ,F>,ARR>,B>,C> a) {
        return (FreeArrow<F,ARR,B,C>)a;
    }
    
    public interface Cases<R,F,ARR,B,C> {
        R Id(F1<B,C> id);
        R Compose(Compose<F,ARR,B,?,C> compose);
        R Arr(F1<B,C> arrF);
        R First(First<F,ARR,?,?,?,B,C> first);
        R LiftF(__2<F,B,C> liftFf);
        R LiftA(__2<ARR,B,C> liftAArr);
    }
    
    public abstract <R> R match(Cases<R,F,ARR,B,C> cases);
    
    public static class Compose<F,ARR,B,C,D> {
        private final FreeArrow<F,ARR,C,D> _a1;
        private final FreeArrow<F,ARR,B,C> _a2;
        
        private Compose(FreeArrow<F,ARR,C,D> a1, FreeArrow<F,ARR,B,C> a2) {
            this._a1 = a1;
            this._a2 = a2;
        }
        
        public static <F,ARR,B,C,D> Compose<F,ARR,B,C,D> create(FreeArrow<F,ARR,C,D> a1, FreeArrow<F,ARR,B,C> a2) {
            return new Compose<>(a1, a2);
        }
        
        public FreeArrow<F,ARR,C,D> a1() {
            return _a1;
        }
        
        public FreeArrow<F,ARR,B,C> a2() {
            return _a2;
        }
    }
    
    public static class First<F,ARR,B,C,D,E,G> {
        private final FreeArrow<F,ARR,B,C> _a;
        private final F1<T2<B,D>,E> _f;
        private final F1<T2<C,D>,G> _g;
        
        private First(FreeArrow<F,ARR,B,C> a, F1<T2<B,D>,E> f, F1<T2<C,D>,G> g) {
            this._a = a;
            this._f = f;
            this._g = g;
        }
        
        public static <F,ARR,B,C,D,E,G> First<F,ARR,B,C,D,E,G> create(FreeArrow<F,ARR,B,C> a, F1<T2<B,D>,E> f, F1<T2<C,D>,G> g) {
            return new First<>(a, f, g);
        }
        
        public FreeArrow<F,ARR,B,C> a() {
            return _a;
        }
        
        public F1<T2<B,D>,E> f() {
            return _f;
        }
        
        public F1<T2<C,D>,G> g() {
            return _g;
        }
    }
    
    public static <F,ARR,B> FreeArrow<F,ARR,B,B> id() {
        return FreeArrowImpl.Id(F1.id());
    }
    
    public static <F,ARR,B,C> FreeArrow<F,ARR,B,C> arr(F1<B,C> f) {
        return FreeArrowImpl.Arr(f);
    }
    
    public static <F,ARR,B,C,D> FreeArrow<F,ARR,B,D> compose(FreeArrow<F,ARR,C,D> a1, FreeArrow<F,ARR,B,C> a2) {
        return FreeArrowImpl.Compose(Compose.create(a1, a2));
    }
    
    public static <F,ARR,B,C,D> FreeArrow<F,ARR,T2<B,D>,T2<C,D>> first(FreeArrow<F,ARR,B,C> a) {
        return FreeArrowImpl.First(First.<F,ARR,B,C,D,T2<B,D>,T2<C,D>>create(a, F1.id(), F1.id()));
    }
    
    public static <F,ARR,B,C> FreeArrow<F,ARR,B,C> liftF(__2<F,B,C> f) {
        return FreeArrowImpl.LiftF(f);
    }
    
    public static <F,ARR,B,C> FreeArrow<F,ARR,B,C> liftArr(__2<ARR,B,C> arr) {
        return FreeArrowImpl.LiftA(arr);
    }
    
    // FIXME: Need an abstraction that can avoid stack overflows.
    // (Like MonadRec, but for Arrows)
    public __2<ARR,B,C> runFree(Arrow<ARR> arrow, NF2<F,ARR> interp) {
        return FreeArrowImpl
            .<F,ARR,B,C>cases()
            .Id((F1<B,C> id) -> arrow.dot(arrow.arr(id), arrow.<B>identity()))
            .Compose((Compose<F,ARR,B,?,C> compose) -> runCompose(compose))
            .Arr((F1<B,C> f) -> arrow.arr(f))
            .First((First<F,ARR,?,?,?,B,C> first) -> runFirst(first))
            .LiftF((__2<F,B,C> f) -> interp.apply(f))
            .LiftA(F1.id())
            .apply(this);
    }
    
    private static <F,ARR,B,C,D> __2<ARR,B,D> runCompose(Compose<F,ARR,B,C,D> compose) {
        // TODO: Finish this.
        throw new UnsupportedOperationException();
    }
    
    private static <F,ARR,B,C,D,E,G> __2<ARR,E,G> runFirst(First<F,ARR,B,C,D,E,G> first) {
        // TODO: Finish this.
        throw new UnsupportedOperationException();
    }

    public static <F,ARR> FreeArrowSemigroupoid<F,ARR> semigroupoid() {
        return new FreeArrowSemigroupoid<F,ARR>() {};
    }
    
    public static <F,ARR> FreeArrowCategory<F,ARR> category() {
        return new FreeArrowCategory<F,ARR>() {};
    }
}
