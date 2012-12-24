package org.highj.function.repo;

import org.highj.function.F0;
import org.highj.function.F1;
import org.highj.function.F2;
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

    public final static Group<Boolean> andGroup = new Group<Boolean>(){
        @Override
        public F1<Boolean, Boolean> inverse() {
            return not;
        }

        @Override
        public Boolean identity() {
            return true;
        }

        @Override
        public F2<Boolean, Boolean, Boolean> dot() {
            return and;
        }
    };

    public final static Group<Boolean> orGroup = new Group<Boolean>(){
        @Override
        public F1<Boolean, Boolean> inverse() {
            return not;
        }

        @Override
        public Boolean identity() {
            return false;
        }

        @Override
        public F2<Boolean, Boolean, Boolean> dot() {
            return or;
        }
    };

    public final static Semigroup<Boolean> xorSemigroup = () -> xor;
}
