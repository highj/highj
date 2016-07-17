package org.highj.data.num.instances;

public interface IntegerEnum extends org.highj.typeclass0.num.Enum<Integer> {
    @Override
    default Integer toEnum(int a) {
        return a;
    }

    @Override
    default int fromEnum(Integer a) {
        return a;
    }

    @Override
    default Integer succ(Integer a) {
        return a + 1;
    }

    @Override
    default Integer pred(Integer a) {
        return a - 1;
    }
}
