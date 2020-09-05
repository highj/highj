package org.highj.data;

import org.highj.data.tuple.T2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;


public class MapBenchmark {

    @Disabled
    @Test
    public void benchmark() {
        List<String> list = List.empty();
        String[] parts = "A beginning is the time for taking the most delicate care that the balances are correct".split("\\s");
        for (String a : parts) {
            for (String b : parts) {
                for (String c : parts) {
                    for (String d : parts) {
                        list = list.plus(a + b + c + d);
                    }
                }
            }
        }
        List<String> finalList = list;

        T2<Map<String, Integer>, Long> mapT2 = time(() -> mapFill(finalList, String::length));
        System.err.println("filling Map    : " + mapT2._2());
        T2<HashMap<String, Integer>, Long> hashMapT2 = time(() -> hashMapFill(finalList, String::length));
        System.err.println("filling HashMap: " + hashMapT2._2());
        assertThat(mapT2._1().size()).isEqualTo(hashMapT2._1().size());

        T2<List<Integer>, Long> intsT2 = time(() -> mapLookup(finalList, mapT2._1()));
        System.err.println("lookup Map     : " + mapT2._2());
        T2<List<Integer>, Long> hashIntsT2 = time(() -> hashMapLookup(finalList, hashMapT2._1()));
        System.err.println("lookup HashMap : " + hashMapT2._2());
        assertThat(intsT2._1()).containsExactlyElementsOf(hashIntsT2._1());

        java.util.List<String> shuffledJList = finalList.toJList();
        Collections.shuffle(shuffledJList);
        List<String> shuffled = List.fromJavaList(shuffledJList);

        T2<Map<String, Integer>, Long> delMapT2 = time(() -> mapDelete(shuffled, mapT2._1()));
        System.err.println("delete Map     : " + delMapT2._2());
        T2<HashMap<String, Integer>, Long> delHashMapT2 = time(() -> hashMapDelete(shuffled, hashMapT2._1()));
        System.err.println("delete HashMap : " + delHashMapT2._2());
        assertThat(delMapT2._1().isEmpty()).isTrue();
        assertThat(delHashMapT2._1().isEmpty()).isTrue();
    }

    private static <T> T2<T, Long> time(Supplier<T> supplier) {
        long start = System.nanoTime();
        T t = supplier.get();
        long end = System.nanoTime();
        return T2.of(t, end - start);
    }

    private static Map<String, Integer> mapFill(List<String> keys, Function<String, Integer> values) {
        Map<String, Integer> map = Map.empty();
        for (String key : keys) {
            map = map.plus(key, values.apply(key));
        }
        return map;
    }

    private static HashMap<String, Integer> hashMapFill(List<String> keys, Function<String, Integer> values) {
        HashMap<String, Integer> map = HashMap.empty();
        for (String key : keys) {
            map = map.insert(key, values.apply(key));
        }
        return map;
    }

    private static List<Integer> mapLookup(List<String> keys, Map<String, Integer> map) {
        List<Integer> ints = List.empty();
        for (String key : keys) {
            ints = ints.plus(map.get(key));
        }
        return ints;
    }

    private static List<Integer> hashMapLookup(List<String> keys, HashMap<String, Integer> map) {
        List<Integer> ints = List.empty();
        for (String key : keys) {
            ints = ints.plus(map.lookup(key).get());
        }
        return ints;
    }

    private static Map<String, Integer> mapDelete(List<String> keys, Map<String, Integer> map) {
        for (String key : keys) {
            map = map.minus(key);
        }
        return map;
    }

    private static HashMap<String, Integer> hashMapDelete(List<String> keys, HashMap<String, Integer> map) {
        for (String key : keys) {
            map = map.delete(key);
        }
        return map;
    }
}
