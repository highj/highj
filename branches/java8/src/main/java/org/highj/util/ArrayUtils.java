package org.highj.util;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum ArrayUtils {
    ;

    /*
     * Autoboxing works only for primitives, not for arrays of primitives.
     */

    public static Boolean[] box(boolean ... xs) {
        Boolean[] result = new Boolean[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static Byte[] box(byte ... xs) {
        Byte[] result = new Byte[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static Character[] box(char ... xs) {
        Character[] result = new Character[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static Short[] box(short ... xs) {
        Short[] result = new Short[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static Integer[] box(int  ... xs) {
        Integer[] result = new Integer[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static Long[] box(long ... xs) {
        Long[] result = new Long[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static Float[] box(float ... xs) {
        Float[] result = new Float[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static Double[] box(double ... xs) {
        Double[] result = new Double[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    /*
     * Autounboxing works only for primitive wrappers, not for arrays of primitive wrappers.
     */

    public static boolean[] unbox(Boolean ... xs) {
        boolean[] result = new boolean[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static byte[] unbox(Byte ... xs) {
        byte[] result = new byte[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static char[] unbox(Character ... xs) {
        char[] result = new char[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static short[] unbox(Short ... xs) {
        short[] result = new short[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static int[] unbox(Integer  ... xs) {
        int[] result = new int[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static long[] unbox(Long ... xs) {
        long[] result = new long[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static float[] unbox(Float ... xs) {
        float[] result = new float[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    public static double[] box(Double ... xs) {
        double[] result = new double[xs.length];
        for(int i = 0; i < xs.length; i++) {
            result[i] = xs[i];
        }
        return result;
    }

    /*A version of Arrays.asList() that returns a modifiable list, renamed in order to avoid name clashes.*/
    public static <A> List<A> asModifiableList(A ... as) {
        List<A> result = new ArrayList<A>(as.length);
        for(A a:as) {
            result.add(a);
        }
        return result;
    }

    /*
     * Arrays.asList for primitive arrays. Note that we don't need varargs, they work already for the "normal" version
     * (in fact introducing them here makes the call ambiguous).
     */

    public static List<Boolean> asList(boolean[] as) {
        return asModifiableList(box(as));
    }

    public static List<Byte> asList(byte[] as) {
        return asModifiableList(box(as));
    }

    public static List<Character> asList(char[] as) {
        return asModifiableList(box(as));
    }

    public static List<Short> asList(short[] as) {
        return asModifiableList(box(as));
    }

    public static List<Integer> asList(int[] as) {
        return asModifiableList(box(as));
    }

    public static List<Long> asList(long[] as) {
        return asModifiableList(box(as));
    }

    public static List<Float> asList(float[] as) {
        return asModifiableList(box(as));
    }

    public static List<Double> asList(double[] as) {
        return asModifiableList(box(as));
    }

    /* Strange enough this method is missing in Arrays */
    public static <A> Set<A> asSet(A ... as) {
        Set<A> result = new HashSet<A>(as.length);
        for(A a:as) {
            result.add(a);
        }
        return result;
    }

    /*
     * Constructs sets from primitive arrays. Note that we don't need varargs, they work already for the "normal",
     * generic version (in fact introducing them here makes the call ambiguous).
     */

    public static Set<Boolean> asSet(boolean[] as) {
        return asSet(box(as));
    }

    public static Set<Byte> asSet(byte[] as) {
        return asSet(box(as));
    }

    public static Set<Character> asSet(char[] as) {
        return asSet(box(as));
    }

    public static Set<Short> asSet(short[] as) {
        return asSet(box(as));
    }

    public static Set<Integer> asSet(int[] as) {
        return asSet(box(as));
    }

    public static Set<Long> asSet(long[] as) {
        return asSet(box(as));
    }

    public static Set<Float> asSet(float[] as) {
        return asSet(box(as));
    }

    public static Set<Double> asSet(double[] as) {
        return asSet(box(as));
    }

}
