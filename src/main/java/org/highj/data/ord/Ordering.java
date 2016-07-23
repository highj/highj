package org.highj.data.ord;

import org.highj.data.Maybe;
import org.highj.typeclass0.group.Group;

import java.util.function.Supplier;

/**
 * The result of a comparison: LT (less than), EQ (equal) or GT (greater than)
 */
public enum Ordering {
    LT, EQ, GT;

    /**
     * Converts the {@link Ordering} to a number which can be used like the result of a
     * {@link Comparable} or {@link java.util.Comparator}.
     *
     * @return the int value
     */
    public int cmpResult() {
        return ordinal() - 1;
    }

    /**
     * Compares first by this and then (if necessary) by the given argument.
     *
     * @param that the {@link Ordering} to be used if this one is EQ
     * @return the combined result
     */
    public Ordering andThen(Ordering that) {
        return this == EQ ? that : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(boolean x, boolean y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(char x, char y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(byte x, byte y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(short x, short y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(int x, int y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(long x, long y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(float x, float y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the combined result
     */
    public Ordering andThen(double x, double y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Compares first by this and then (if necessary) by comparing the given arguments by their
     * natural order (as defined by {@link Comparable}).
     *
     * @param x   the first comparison argument
     * @param y   the second comparison argument
     * @param <T> the comparable argument type
     * @return the combined result
     */
    public <T extends Comparable<? super T>> Ordering andThen(T x, T y) {
        return this == EQ ? Ordering.compare(x, y) : this;
    }

    /**
     * Returns the inverse comparison result.
     *
     * @return the inverse to this
     */
    public Ordering inverse() {
        return this == LT ? GT :
                this == EQ ? EQ : LT;
    }

    /**
     * Returns the {@link Group} for comparisons.
     */
    public static Group<Ordering> group = Group.create(EQ, Ordering::andThen, Ordering::inverse);

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(boolean x, boolean y) {
        return x == y ? EQ : !x ? LT : GT;
    }

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(char x, char y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(byte x, byte y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(short x, short y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(int x, int y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(long x, long y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(float x, float y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    /**
     * Compares the given arguments.
     *
     * @param x the first comparison argument
     * @param y the second comparison argument
     * @return the comparison result
     */
    public static Ordering compare(double x, double y) {
        return x < y ? LT : x > y ? GT : EQ;
    }

    /**
     * Compares the given arguments by their natural order (as defined by {@link Comparable}).
     *
     * @param x   the first comparison argument
     * @param y   the second comparison argument
     * @param <T> the comparable argument type
     * @return the comparison result
     */
    public static <T extends Comparable<? super T>> Ordering compare(T x, T y) {
        return fromInt(x.compareTo(y));
    }

    /**
     * Converts a result of {@link Comparable} or {@link java.util.Comparator} to an {@link Ordering}.
     * <p>
     * Negative values are converted to LT, values equal to 0 to EQ, and positive values to GT.
     *
     * @param compareResult the int value
     * @return the {@link Ordering}
     */
    public static Ordering fromInt(int compareResult) {
        return compare(compareResult, 0);
    }

    /**
     * Starts an evaluation based on the current {@link Ordering} using a fluent interface.
     * <p>
     * Example usage:
     * <pre>
     * {@code
     * String result = Ordering.compare(x,y)
     *   .caseLT(() -> "x is smaller than y")
     *   .caseEQ(() -> "both values are equal")
     *   .caseGT(() -> "x is bigger than y");
     * }
     * </pre>
     *
     * @param lessThan result to be returned if this is a LT.
     * @param <R>      the result type
     * @return intermediate result for fluent interface
     */
    public <R> CaseLT<R> caseLT(Supplier<R> lessThan) {
        return new CaseLT<>(Maybe.JustWhenTrue(this == LT, lessThan));
    }

    /**
     * Intermediate result of an evaluation, see {@link Ordering#caseLT} for usage.
     *
     * @param <R> the result type
     */
    public class CaseLT<R> {
        private final Maybe<R> value;

        private CaseLT(Maybe<R> value) {
            this.value = value;
        }

        public CaseEQ<R> caseEQ(Supplier<R> equal) {
            return new CaseEQ<>(value.orElse(Maybe.JustWhenTrue(Ordering.this == EQ, equal)));
        }
    }

    /**
     * Intermediate result of an evaluation, see {@link Ordering#caseLT} for usage.
     *
     * @param <R> the result type
     */
    public static class CaseEQ<R> {
        private final Maybe<R> value;

        private CaseEQ(Maybe<R> value) {
            this.value = value;
        }

        public R caseGT(Supplier<R> greaterThan) {
            return value.getOrElse(greaterThan);
        }
    }

}
