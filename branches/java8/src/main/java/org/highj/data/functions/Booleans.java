package org.highj.data.functions;

import org.highj.typeclass0.group.*;

public enum Booleans {
    ;

    public final static Group<Boolean> andGroup = new Group<Boolean>(){
        @Override
        public Boolean inverse(Boolean x) {
            return !x;
        }

        @Override
        public Boolean identity() {
            return true;
        }

        @Override
        public Boolean dot(Boolean x, Boolean y) {
            return x && y;
        }
    };

    public final static Group<Boolean> orGroup = new Group<Boolean>(){
        @Override
        public Boolean inverse(Boolean x) {
            return !x;
        }

        @Override
        public Boolean identity() {
            return false;
        }

        @Override
        public Boolean dot(Boolean x, Boolean y) {
            return x || y;
        }
    };

    public final static Semigroup<Boolean> xorSemigroup = (x,y) -> x ^ y;
}
