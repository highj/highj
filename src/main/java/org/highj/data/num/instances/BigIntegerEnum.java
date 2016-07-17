package org.highj.data.num.instances;

import org.highj.data.List;
import org.highj.data.Stream;
import org.highj.data.num.BigIntegers;
import org.highj.data.predicates.Pred;

import java.math.BigInteger;
import java.util.function.Predicate;

public interface BigIntegerEnum extends org.highj.typeclass0.num.Enum<BigInteger> {

    @Override
    default BigInteger toEnum(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    default int fromEnum(BigInteger a) {
        return a.intValue();
    }

    @Override
    default BigInteger succ(BigInteger a) {
        return BigIntegers.succ.apply(a);
    }

    @Override
    default BigInteger pred(BigInteger a) {
        return BigIntegers.pred.apply(a);
    }

    @Override
    default Stream<BigInteger> enumFrom(BigInteger a) {
        return Stream.unfold(this :: succ, a);
    }

    @Override
    default Stream<BigInteger> enumFromThen(BigInteger a, BigInteger b) {
        BigInteger delta = b.subtract(a);
        return Stream.unfold(x -> x.add(delta), a);
    }

    @Override
    default List<BigInteger> enumFromTo(BigInteger a, BigInteger b) {
        Pred<BigInteger> test = x -> x.compareTo(b) <= 0;
        return enumFrom(a).takeWhile(test);
    }

    @Override
    default List<BigInteger> enumFromThenTo(BigInteger a, BigInteger b, BigInteger c) {
        Pred<BigInteger> test = a.compareTo(b) <= 0
                ? x -> x.compareTo(c) <= 0
                : x -> x.compareTo(c) >= 0;
        return enumFromThen(a,b).takeWhile(test);
    }
}
