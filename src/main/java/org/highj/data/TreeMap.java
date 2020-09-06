package org.highj.data;

import org.derive4j.hkt.__2;
import org.highj.data.impl.treeMap.Node;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;
import org.highj.data.ord.Ord;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * A sorted map, backed by a left leaning RBTree implementation.
 * <p>
 * {@link TreeMap} works strict.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class TreeMap<K, V> implements __2<TreeMap.µ, K, V>, Function<K, Maybe<V>>, Iterable<T2<K, V>> {

    public interface µ {
    }

    private final Ord<? super K> ord;

    private final Node<K, V> root;

    @SuppressWarnings("unchecked")
    private TreeMap(Ord<? super K> ord, Node<K, V> root) {
        this.root = root;
        this.ord = ord;
    }

    /**
     * Creates an empty map, using the natural order of the key type.
     *
     * @param <K> key type
     * @param <V> value type
     * @return the map
     */
    public static <K extends Comparable<? super K>, V> TreeMap<K, V> empty() {
        return new TreeMap<>(Ord.fromComparable(), Node.<K, V>empty());
    }

    /**
     * Creates an empty map, using the given {@link Ord}.
     *
     * @param ord the {@link Ord} instance
     * @param <K> key type
     * @param <V> value type
     * @return the map
     */
    public static <K, V> TreeMap<K, V> empty(Ord<? super K> ord) {
        return new TreeMap<>(ord, Node.empty());
    }

    /**
     * Creates a map with one entry, using the natural order of the key type.
     *
     * @param key   the key
     * @param value the value
     * @param <K>   key type
     * @param <V>   value type
     * @return the map
     */
    public static <K extends Comparable<? super K>, V> TreeMap<K, V> of(K key, V value) {
        return new TreeMap<>(Ord.fromComparable(), Node.singleton(key, value));
    }

    /**
     * Creates a map with one entry, using the given {@link Ord}.
     *
     * @param ord   the {@link Ord} instance
     * @param key   the key
     * @param value the value
     * @param <K>   key type
     * @param <V>   value type
     * @return the map
     */
    public static <K, V> TreeMap<K, V> of(Ord<? super K> ord, K key, V value) {
        return new TreeMap<>(ord, Node.singleton(key, value));
    }

    /**
     * Creates a map from an {@link Iterable} of key-value pairs, using the natural order of the key type.
     *
     * @param iterable the {@link Iterable}
     * @param <K>      key type
     * @param <V>      value type
     * @return the map
     */
    public static <K extends Comparable<? super K>, V> TreeMap<K, V> of(Iterable<T2<K, V>> iterable) {
        Ord<? super K> ord = Ord.fromComparable();
        return new TreeMap<>(Ord.fromComparable(), Node.fromIterable(ord, iterable));
    }

    /**
     * Creates a map from an {@link Iterable} of key-value pairs, using the given {@link Ord}.
     *
     * @param ord      the {@link Ord} instance
     * @param iterable the {@link Iterable}
     * @param <K>      key type
     * @param <V>      value type
     * @return the map
     */
    public static <K, V> TreeMap<K, V> of(Ord<K> ord, Iterable<T2<K, V>> iterable) {
        return new TreeMap<>(ord, Node.fromIterable(ord, iterable));
    }

    /**
     * Creates a map from the given keys and a function to calculate values from these keys, using the natural order of the key type.
     *
     * @param getValue the {@link Function} to calculate a value to a given key
     * @param keys the keys
     * @param <K> key type
     * @param <V> value type
     * @return the map
     */
    public static <K extends Comparable<? super K>, V> TreeMap<K,V> fromKeys(Function<? super K, ? extends V> getValue, K ... keys) {
        Ord<? super K> ord = Ord.fromComparable();
        return new TreeMap<>(ord, Node.fromIterable(ord, List.of(keys).map(k -> T2.of(k, getValue.apply(k)))));
    }

    /**
     * Creates a map from the given keys and a function to calculate values from these keys, using the given {@link Ord}.
     *
     * @param ord the {@link Ord} instance
     * @param getValue the {@link Function} to calculate a value to a given key
     * @param keys the keys
     * @param <K> key type
     * @param <V> value type
     * @return the map
     */
    public static <K, V> TreeMap<K,V> fromKeys(Ord<K> ord, Function<? super K, ? extends V> getValue, K ... keys) {
        return new TreeMap<>(ord, Node.fromIterable(ord, List.of(keys).map(k -> T2.of(k, getValue.apply(k)))));
    }

    /**
     * Creates a map from the given keys and a function to calculate values from these keys, using the natural order of the key type.
     *
     * @param getValue the {@link Function} to calculate a value to a given key
     * @param keys the keys
     * @param <K> key type
     * @param <V> value type
     * @return the map
     */
    public static <K extends Comparable<? super K>, V> TreeMap<K,V> fromKeys(Function<? super K, ? extends V> getValue, Iterable<K> keys) {
        Ord<? super K> ord = Ord.fromComparable();
        return new TreeMap<K,V>(ord, Node.fromIterable(ord, List.fromIterable(keys).map(k -> T2.of(k, getValue.apply(k)))));
    }

    /**
     * Creates a map from the given keys and a function to calculate values from these keys, using the given {@link Ord}.
     *
     * @param ord the {@link Ord} instance
     * @param getValue the {@link Function} to calculate a value to a given key
     * @param keys the keys
     * @param <K> key type
     * @param <V> value type
     * @return the map
     */
    public static <K, V> TreeMap<K,V> fromKeys(Ord<K> ord, Function<? super K, ? extends V> getValue, Iterable<K> keys) {
        return new TreeMap<>(ord, Node.fromIterable(ord, List.fromIterable(keys).map(k -> T2.of(k, getValue.apply(k)))));
    }


    /**
     * Calculates the size of the map.
     *
     * @return the size
     */
    public int size() {
        return root.size();
    }

    /**
     * Checks whether this map is empty.
     * Note that using this method might be much faster than testing if {@link TreeMap#size} is equal to 0.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return root.isEmpty();
    }

    /**
     * Constructs a map from the current one, but also containing the given entry.
     *
     * @param key   the key
     * @param value the value
     * @return the new map
     */
    public TreeMap<K, V> insert(K key, V value) {
        return new TreeMap<>(ord, root.insert(ord, key, value));
    }

    /**
     * Constructs a map from the current one, but also containing the given entries.
     * Note that using this method might be slightly faster than using {@link TreeMap#insert} multiple times.
     *
     * @param iterable the entries, given as {@link Iterable} of key-value pairs.
     * @return the new map
     */
    public TreeMap<K, V> insertAll(Iterable<T2<K, V>> iterable) {
        return new TreeMap<>(ord, root.insertAll(ord, iterable));
    }

    /**
     * Constructs a map from the current one, but without the entry specified by the given key.
     *
     * @param key the key
     * @return the new map
     */
    public TreeMap<K, V> delete(K key) {
        return new TreeMap<>(ord, root.delete(ord, key));
    }

    /**
     * Constructs a map from the current one, but without the entry with the smallest key.
     * Calling this method on an empty map returns an empty map.
     *
     * @return the new map
     */
    public TreeMap<K, V> deleteMin() {
        return new TreeMap<>(ord, root.deleteMin());
    }

    /**
     * Constructs a map from the current one, but without the entry with the largest key.
     * Calling this method on an empty map returns an empty map.
     *
     * @return the new map
     */
    public TreeMap<K, V> deleteMax() {
        return new TreeMap<>(ord, root.deleteMax());
    }

    /**
     * Retrieves a value from the map, if possible
     *
     * @param key the key
     * @return the value wrapped in a {@link Maybe}, if there is any
     */
    @Override
    public Maybe<V> apply(K key) {
        return root.get(ord, key);
    }

    /**
     * Retrieves a value from the map.
     *
     * @param key the key
     * @return the value
     * @throws NoSuchElementException when no entry was found
     */
    public V get(K key) throws NoSuchElementException {
        return root.get(ord, key).getOrException(NoSuchElementException.class);
    }

    /**
     * Checks whether a certain entry exists.
     *
     * @param key the key
     * @return true if an entry for the given key is present
     */
    public boolean containsKey(K key) {
        return root.containsKey(ord, key);
    }

    /**
     * Retrieves the entry with the smallest key.
     *
     * @return the entry
     * @throws NoSuchElementException when called on an empty map
     */
    public T2<K, V> minimum() throws NoSuchElementException {
        return root.minimum();
    }

    /**
     * Retrieves the entry with the largest key.
     *
     * @return the entry
     * @throws NoSuchElementException when called on an empty map
     */
    public T2<K, V> maximum() {
        return root.maximum();
    }

    /**
     * Retrieves the smallest key of the map.
     *
     * @return the key
     * @throws NoSuchElementException when called on an empty map
     */
    public K minimumKey() {
        return root.minimum()._1();
    }

    /**
     * Retrieves the largest key of the map.
     *
     * @return the key
     * @throws NoSuchElementException when called on an empty map
     */
    public K maximumKey() {
        return root.maximum()._1();
    }

    /**
     * Retrieves an ordered list of all keys of the map.
     *
     * @return the list
     */
    public List<K> toKeys() {
        return root.toList((k, v) -> k);
    }

    /**
     * Retrieves a list of all entries of the map, ordered by keys.
     *
     * @return the list
     */
    public List<T2<K, V>> toList() {
        return root.toList(T2::of);
    }

    /**
     * Retrieves a list of all transformed entries of the map, ordered by keys.
     *
     * @param function the transformation {@link BiFunction}
     * @param <R>      result type
     * @return the list
     */
    public <R> List<R> toList(BiFunction<? super K, ? super V, ? extends R> function) {
        return root.toList(function);
    }

    /**
     * Retrieves a list of all values of the map, ordered by their keys.
     *
     * @return the list
     */
    public List<V> toValues() {
        return root.toList((k, v) -> v);
    }

    @Override
    public Iterator<T2<K, V>> iterator() {
        return toList().iterator();
    }

    /**
     * Transforms the value to a given key, if possible.
     * If the entry wasn't found, the unmodified map is returned.
     *
     * @param key      the key
     * @param function the transformation {@link Function}
     * @return the new map
     */
    public TreeMap<K, V> mapValue(K key, Function<? super V, ? extends V> function) {
        return apply(key).map(v -> insert(key, function.apply(v))).getOrElse(this);
    }

    /**
     * Transforms all values of the map.
     *
     * @param function the transformation {@link Function}
     * @param <V1>     the new value type
     * @return the new map
     */
    public <V1> TreeMap<K, V1> mapValues(Function<? super V, ? extends V1> function) {
        return new TreeMap<>(ord, root.mapValues(function));
    }

    /**
     * Tests whether this map contains the same entries in the same order as another map.
     * Note that both maps can still rely on different {@link Ord} instances. As a consequence, e.g.
     * <code>m1.equals(m2)</code> doesn't imply <code>m1.insert(x,y).equals(m2.insert(x,y))</code>.
     *
     * @param o the {@link Object} to compare with
     * @return true if both maps contain the same entries in the same order
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TreeMap)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        TreeMap<?, ?> that = (TreeMap<?, ?>) o;
        return this.toList().equals(that.toList());
    }

    @Override
    public int hashCode() {
        return 4711 + toList().hashCode();
    }

    @Override
    public String toString() {
        return Strings.mkEnclosed("Map(", ",", ")", toList((k, v) -> k + "->" + v));
    }

    /**
     * Calculates the union of two {@link TreeMap}s, so that the result contains all of their keys.
     * If a key is found in both {@link TreeMap}s, the value of the resulting entry will be calculated
     * from the existing values using the given {@link BinaryOperator}.
     * Note that both {@link TreeMap}s <strong>must</strong> use the same {@link Ord}.
     *
     * @param op     {@link BinaryOperator} for merging values
     * @param first  the first {@link TreeMap}
     * @param second the second {@link TreeMap}
     * @param <K>    key type
     * @param <V>    value type
     * @return the union map
     */
    public static <K, V> TreeMap<K, V> union(BinaryOperator<V> op, TreeMap<K, V> first, TreeMap<K, V> second) {
        return new TreeMap<>(first.ord, Node.union(first.ord, op, first.root, second.root));
    }

    /**
     * Calculates the intersection of two {@link TreeMap}s, so that the result contains their common keys.
     * The values of the resulting entries will be calculated from the existing values using the given
     * {@link BinaryOperator}.
     * Note that both {@link TreeMap}s <strong>must</strong> use the same {@link Ord}.
     *
     * @param op     {@link BinaryOperator} for merging values
     * @param first  the first {@link TreeMap}
     * @param second the second {@link TreeMap}
     * @param <K>    key type
     * @param <V>    value type
     * @return the intersection map
     */
    public static <K, V> TreeMap<K, V> intersection(BinaryOperator<V> op, TreeMap<K, V> first, TreeMap<K, V> second) {
        return new TreeMap<>(first.ord, Node.intersection(first.ord, op, first.root, second.root));
    }

    /**
     * Calculates the difference of two {@link TreeMap}s, so that the result contains only entries from the first map,
     * whose keys are not in the second map.
     * Note that both {@link TreeMap}s <strong>must</strong> use the same {@link Ord}.
     *
     * @param first  the first {@link TreeMap}
     * @param second the second {@link TreeMap}
     * @param <K>    key type
     * @param <V>    value type
     * @return the intersection map
     */
    public static <K, V> TreeMap<K, V> difference(TreeMap<K, V> first, TreeMap<K, V> second) {
        return new TreeMap<>(first.ord, Node.difference(first.ord, first.root, second.root));
    }
}
