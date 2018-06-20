package org.highj.typeclass2.category;

import org.highj.data.Maybe;

public abstract class Untyped {


    private Untyped() {
    }

    public static Eval Eval() {
        return Eval.EVAL;
    }

    public static Curry Curry(Untyped untyped) {
        return new Curry(untyped);
    }

    public static Uncurry Uncurry(Untyped untyped) {
        return new Uncurry(untyped);
    }

    public static Fork Fork(Untyped first, Untyped second) {
        return new Fork(first, second);
    }

    public static Exl Exl() {
        return Exl.EXL;
    }

    public static Exr Exr() {
        return Exr.EXR;
    }

    public static Id Id() {
        return Id.ID;
    }

    public static Compose Compose(Untyped first, Untyped second) {
        return new Compose(first, second);
    }


    public static class Eval extends Untyped {
        private static final Eval EVAL = new Eval();

        private Eval() {
        }

        @Override
        public boolean isEval() {
            return true;
        }
    }

    public static class Curry extends Untyped {
        public final Untyped untyped;

        private Curry(Untyped untyped) {
            this.untyped = untyped;
        }

        @Override
        public Maybe<Curry> asCurry() {
            return Maybe.Just(this);
        }
    }

    public static class Uncurry extends Untyped {
        public final Untyped untyped;

        private Uncurry(Untyped untyped) {
            this.untyped = untyped;
        }

        @Override
        public Maybe<Uncurry> asUncurry() {
            return Maybe.Just(this);
        }
    }

    public static class Fork extends Untyped {
        public final Untyped first;
        public final Untyped second;

        private Fork(Untyped first, Untyped second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public Maybe<Fork> asFork() {
            return Maybe.Just(this);
        }

    }

    public static class Exl extends Untyped {
        private static final Exl EXL = new Exl();

        private Exl() {
        }

        public boolean isExl() {
            return true;
        }

    }

    public static class Exr extends Untyped {
        private static final Exr EXR = new Exr();

        private Exr() {
        }

        public boolean isExr() {
            return true;
        }
    }


    public static class Id extends Untyped {
        private static final Id ID = new Id();

        private Id() {
        }

        public boolean isId() {
            return true;
        }
    }


    public static class Compose extends Untyped {
        public final Untyped first;
        public final Untyped second;

        private Compose(Untyped first, Untyped second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public Maybe<Compose> asCompose() {
            return Maybe.Just(this);
        }
    }

    public boolean isEval() {
        return false;
    }

    public Maybe<Curry> asCurry() {
        return Maybe.Nothing();
    }

    public Maybe<Uncurry> asUncurry() {
        return Maybe.Nothing();
    }

    public Maybe<Fork> asFork() {
        return Maybe.Nothing();
    }

    public boolean isExl() {
        return false;
    }

    public boolean isExr() {
        return false;
    }

    public boolean isId() {
        return false;
    }

    public Maybe<Compose> asCompose() {
        return Maybe.Nothing();
    }
}

