package org.highj.util;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum ArrayUtils {
    ;

    public static List<Boolean> asList(boolean ... as) {
        List<Boolean> result = new ArrayList<Boolean>(as.length);
        for(boolean a:as) {
            result.add(a);
        }
        return result;
    }

    public static List<Byte> asList(byte ... as) {
        List<Byte> result = new ArrayList<Byte>(as.length);
        for(byte a:as) {
            result.add(a);
        }
        return result;
    }

    public static List<Character> asList(char ... as) {
        List<Character> result = new ArrayList<Character>(as.length);
        for(char a:as) {
            result.add(a);
        }
        return result;
    }

    public static List<Short> asList(short ... as) {
        List<Short> result = new ArrayList<Short>(as.length);
        for(short a:as) {
            result.add(a);
        }
        return result;
    }

    public static List<Integer> asList(int ... as) {
        List<Integer> result = new ArrayList<Integer>(as.length);
        for(int a:as) {
            result.add(a);
        }
        return result;
    }

    public static List<Long> asList(long ... as) {
        List<Long> result = new ArrayList<Long>(as.length);
        for(long a:as) {
            result.add(a);
        }
        return result;
    }

    public static List<Float> asList(float ... as) {
        List<Float> result = new ArrayList<Float>(as.length);
        for(float a:as) {
            result.add(a);
        }
        return result;
    }

    public static List<Double> asList(double ... as) {
        List<Double> result = new ArrayList<Double>(as.length);
        for(double a:as) {
            result.add(a);
        }
        return result;
    }

    public static <A> Set<A> asSet(A ... as) {
        Set<A> result = new HashSet<A>(as.length);
        for(A a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Boolean> asSet(boolean ... as) {
        Set<Boolean> result = new HashSet<Boolean>(as.length);
        for(boolean a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Byte> asSet(byte ... as) {
        Set<Byte> result = new HashSet<Byte>(as.length);
        for(byte a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Character> asSet(char ... as) {
        Set<Character> result = new HashSet<Character>(as.length);
        for(char a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Short> asSet(short ... as) {
        Set<Short> result = new HashSet<Short>(as.length);
        for(short a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Integer> asSet(int ... as) {
        Set<Integer> result = new HashSet<Integer>(as.length);
        for(int a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Long> asSet(long ... as) {
        Set<Long> result = new HashSet<Long>(as.length);
        for(long a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Float> asSet(float ... as) {
        Set<Float> result = new HashSet<Float>(as.length);
        for(float a:as) {
            result.add(a);
        }
        return result;
    }

    public static Set<Double> asSet(double ... as) {
        Set<Double> result = new HashSet<Double>(as.length);
        for(double a:as) {
            result.add(a);
        }
        return result;
    }

}
