package org.highj.typeclass2.category;

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
    }

    public static class Curry extends Untyped {
        public final Untyped untyped;

        private Curry(Untyped untyped) {
            this.untyped = untyped;
        }
    }

    public static class Uncurry extends Untyped {
        public final Untyped untyped;

        private Uncurry(Untyped untyped) {
            this.untyped = untyped;
        }
    }

    public static class Fork extends Untyped {
        public final Untyped first;
        public final Untyped second;

        private Fork(Untyped first, Untyped second) {
            this.first = first;
            this.second = second;
        }
    }

    public static class Exl extends Untyped {
        private static final Exl EXL = new Exl();

        private Exl() {
        }
    }

    public static class Exr extends Untyped {
        private static final Exr EXR = new Exr();

        private Exr() {
        }
    }


    public static class Id extends Untyped {
        private static final Id ID = new Id();

        private Id() {
        }
    }


    public static class Compose extends Untyped {
        public final Untyped first;
        public final Untyped second;

        private Compose(Untyped first, Untyped second) {
            this.first = first;
            this.second = second;
        }
    }

}

