package org.highj.util;


import java.util.*;

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
    @SafeVarargs
    public static <A> List<A> asModifiableList(A ... as) {
        List<A> result = new ArrayList<>(as.length);
        Collections.addAll(result, as);
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
    @SafeVarargs
    public static <A> Set<A> asSet(A ... as) {
        Set<A> result = new HashSet<>(as.length);
        Collections.addAll(result, as);
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


    @SafeVarargs
    public static <A extends Comparable<? super A>> SortedSet<A> asSortedSet(A ... as) {
        SortedSet<A> result = new TreeSet<A>();
        Collections.addAll(result, as);
        return result;
    }

    /*
     * Constructs sets from primitive arrays. Note that we don't need varargs, they work already for the "normal",
     * generic version (in fact introducing them here makes the call ambiguous).
     */

    public static SortedSet<Boolean> asSortedSet(boolean[] as) {
        return asSortedSet(box(as));
    }

    public static SortedSet<Byte> asSortedSet(byte[] as) {
        return asSortedSet(box(as));
    }

    public static SortedSet<Character> asSortedSet(char[] as) {
        return asSortedSet(box(as));
    }

    public static SortedSet<Short> asSortedSet(short[] as) {
        return asSortedSet(box(as));
    }

    public static SortedSet<Integer> asSortedSet(int[] as) {
        return asSortedSet(box(as));
    }

    public static SortedSet<Long> asSortedSet(long[] as) {
        return asSortedSet(box(as));
    }

    public static SortedSet<Float> asSortedSet(float[] as) {
        return asSortedSet(box(as));
    }

    public static SortedSet<Double> asSortedSet(double[] as) {
        return asSortedSet(box(as));
    }

    /*
     *in-place reverse methods
     */
    public static <A> void reverse(A[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            A tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(boolean[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            boolean tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(char[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            char tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(byte[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            byte tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(short[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            short tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(int[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            int tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(long[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            long tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(float[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            float tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static void reverse(double[] as) {
        for(int i = 0, j = as.length - 1; i < j; i++, j--) {
            double tmp = as[i];
            as[i] = as[j];
            as[j] = tmp;
        }
    }

    public static <A> Iterable<A> reverseIterable(A ... as) {
        return () -> new ReadOnlyIterator<A>() {

            private int i = as.length - 1;

            @Override
            public boolean hasNext() {
                return i >= 0;
            }

            @Override
            public A next() {
                if (! hasNext()) throw new NoSuchElementException();
                return as[i--];
            }
        };
    }
}
