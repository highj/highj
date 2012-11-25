package org.highj.data.tuple;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.function.F1;
import org.highj.function.Objects;

public abstract class T4<A, B, C, D> extends ____<T4.µ, A, B, C, D> {
    private static final µ hidden = new µ();

    public static class µ {
        private µ() {
        }
    }

    T4() {
        super(hidden);
    }

    public abstract A _1();

    public abstract B _2();

    public abstract C _3();

    public abstract D _4();

    @SuppressWarnings("unchecked")
    public static <A, B, C, D> T4<A, B, C, D> narrow(_<__.µ<___.µ<____.µ<µ, A>, B>, C>, D> value) {
        return (T4) value;
    }

    public <AA> T4<AA,B,C,D> map_1(F1<A,AA> fn) {
        return Tuple.of(fn.$(_1()), _2(),_3(),_4());
    }

    public <BB> T4<A,BB,C,D> map_2(F1<B,BB> fn) {
        return Tuple.of(_1(), fn.$(_2()),_3(),_4());
    }

    public <CC> T4<A,B,CC,D> map_3(F1<C,CC> fn) {
        return Tuple.of(_1(), _2(), fn.$(_3()),_4());
    }

    public <DD> T4<A,B,C,DD> map_4(F1<D,DD> fn) {
        return Tuple.of(_1(), _2(), _3(), fn.$(_4()));
    }

    @Override
    public int hashCode(){
        return 31*_1().hashCode() + 37*_2().hashCode() + 41*_3().hashCode() + 43*_4().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof T4) {
            T4<?,?,?,?> that = (T4) o;
            return this._1().equals(that._1()) && this._2().equals(that._2()) &&
                   this._3().equals(that._3()) && this._4().equals(that._4());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s,%s)", _1(), _2(), _3(), _4());
    }
}