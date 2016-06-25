package org.highj.data;

import org.highj.data.impl.treeMap.Node;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.compare.Ord;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class TreeMap<K, V> implements Function<K, Maybe<V>> {

    private final Ord<? super K> ord;

    private final Node<K, V> root;

    @SuppressWarnings("unchecked")
    private TreeMap(Comparator<? super K> comparator, Node<K, V> root) {
        this.root = root;
        this.ord = comparator instanceof Ord
                ? (Ord<? super K>) comparator
                : Ord.fromComparator(comparator);
    }

    public static <K extends Comparable<? super K>, V> TreeMap<K, V> empty() {
        return new TreeMap<>(Comparator.naturalOrder(), Node.<K, V>empty());
    }

    public static <K, V> TreeMap<K, V> empty(Comparator<K> comparator) {
        return new TreeMap<>(comparator, Node.empty());
    }

    public static <K extends Comparable<? super K>, V> TreeMap<K, V> of(K k, V v) {
        return new TreeMap<>(Comparator.naturalOrder(), Node.singleton(k, v));
    }

    public static <K, V> TreeMap<K, V> of(Comparator<K> comparator, K k, V v) {
        return new TreeMap<>(comparator, Node.singleton(k, v));
    }

    public static <K extends Comparable<? super K>, V> TreeMap<K, V> of(Iterable<T2<K, V>> list) {
        Ord<? super K> ord = Ord.fromComparable();
        return new TreeMap<>(ord, Node.fromIterable(ord, list));
    }

    public static <K, V> TreeMap<K, V> of(Comparator<K> comparator, Iterable<T2<K, V>> list) {
        Ord<? super K> ord = Ord.fromComparator(comparator);
        return new TreeMap<>(ord, Node.fromIterable(ord, list));
    }

    public int size() {
        return root.size();
    }

    public boolean isEmpty() {
        return root.isEmpty();
    }

    public TreeMap<K, V> insert(K k, V v) {
        return new TreeMap<>(ord, root.insert(ord, k, v));
    }

    public TreeMap<K, V> insertAll(List<T2<K, V>> list) {
        return new TreeMap<>(ord, root.insertAll(ord, list));
    }

    public TreeMap<K, V> delete(K key) {
        return new TreeMap<>(ord, root.delete(ord, key));
    }

    public TreeMap<K, V> deleteMin() {
        return new TreeMap<>(ord, root.deleteMin());
    }

    public TreeMap<K, V> deleteMax() {
        return new TreeMap<>(ord, root.deleteMax());
    }

    public Maybe<V> apply(K key) {
        return root.get(ord, key);
    }

    public V get(K key) throws NoSuchElementException {
        return root.get(ord, key).getOrException(NoSuchElementException.class);
    }

    public boolean containsKey(K key) {
        return root.containsKey(ord, key);
    }

    public T2<K, V> minimum() {
        return root.minimum();
    }

    public T2<K, V> maximum() {
        return root.maximum();
    }

    public K minimumKey() {
        return root.minimum()._1();
    }

    public K maximumKey() {
        return root.maximum()._1();
    }

    public List<K> toKeys() {
        return root.toKeys();
    }

    public List<T2<K, V>> toList() {
        return root.toList();
    }

    public List<V> toValues() {
        return root.toValues();
    }

}
