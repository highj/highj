package org.highj.function;

import org.highj.typeclass.group.*;

public enum Booleans {
    ;

    public final static F0<Boolean> True = F0.constant(true);

    public final static F0<Boolean> False = F0.constant(false);

    public final static F1<Boolean, Boolean> not = new F1<Boolean, Boolean>() {
        @Override
        public Boolean $(Boolean value) {
            return !value;
        }
    };

    public final static F2<Boolean, Boolean, Boolean> and = new F2<Boolean, Boolean, Boolean>() {

        @Override
        public Boolean $(Boolean one, Boolean two) {
            return one && two;
        }
    };

    public final static F2<Boolean, Boolean, Boolean> or = new F2<Boolean, Boolean, Boolean>() {

        @Override
        public Boolean $(Boolean one, Boolean two) {
            return one || two;
        }
    };

    public final static F2<Boolean, Boolean, Boolean> xor = new F2<Boolean, Boolean, Boolean>() {

        @Override
        public Boolean $(Boolean one, Boolean two) {
            return one ^ two;
        }
    };

    public final static F2<Boolean, Boolean, Boolean> eq = new F2<Boolean, Boolean, Boolean>() {

        @Override
        public Boolean $(Boolean one, Boolean two) {
            return one.equals(two);
        }
    };

    public final static Group<Boolean> andGroup = new GroupAbstract<Boolean>(and, true, not);

    public final static Group<Boolean> orGroup = new GroupAbstract<Boolean>(or, false, not);

    public final static Semigroup<Boolean> xorSemigroup = new SemigroupAbstract<Boolean>(xor);
}
