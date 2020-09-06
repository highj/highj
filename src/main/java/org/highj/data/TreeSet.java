package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.impl.treeSet.Node;
import org.highj.data.ord.Ord;
import org.highj.function.Strings;
import org.highj.typeclass0.group.Monoid;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A sorted set, backed by a left leaning RBTree implementation.
 * <p>
 * {@link TreeSet} works strict.
 *
 * @param <E> element type
 */
public class TreeSet<E> implements __<TreeSet.µ, E>, Predicate<E>, Iterable<E> {

    public interface µ {}

    private final Ord<? super E> ord;

    private final Node<E> root;

    @SuppressWarnings("unchecked")
    private TreeSet(Ord<? super E> ord, Node<E> root) {
        this.root = root;
        this.ord = ord;
    }

    /**
     * Creates an empty set, using the natural order of the element type.
     *
     * @param <E> element type
     * @return the set
     */
    public static <E extends Comparable<? super E>> TreeSet<E> empty() {
        return new TreeSet<>(Ord.fromComparable(), Node.<E>empty());
    }

    /**
     * Creates an empty set, using the given {@link Ord}.
     *
     * @param ord the {@link Ord} instance
     * @param <E> element type
     * @return the set
     */
    public static <E> TreeSet<E> empty(Ord<? super E> ord) {
        return new TreeSet<>(ord, Node.empty());
    }

    /**
     * Creates a set with one entry, using the natural order of the element type.
     *
     * @param <E>   element type
     * @param value   the element
     * @return the set
     */
    public static <E extends Comparable<? super E>> TreeSet<E> singleton(E value) {
        return new TreeSet<>(Ord.fromComparable(), Node.singleton(value));
    }

    /**
     * Creates a set with one entry, using the given {@link Ord}.
     *
     * @param <E>   element type
     * @param ord   the {@link Ord} instance
     * @param value   the element
     * @return the set
     */
    public static <E> TreeSet<E> singleton(Ord<? super E> ord, E value) {
        return new TreeSet<>(ord, Node.singleton(value));
    }

    /**
     * Creates a set from an {@link Iterable}, using the natural order of the element type.
     *
     * @param <E>      element type
     * @param iterable the {@link Iterable}
     * @return the set
     */
    public static <E extends Comparable<? super E>> TreeSet<E> fromIterable(Iterable<E> iterable) {
        Ord<? super E> ord = Ord.fromComparable();
        return new TreeSet<>(Ord.fromComparable(), Node.fromIterable(ord, iterable));
    }

    /**
     * Creates a set from varargs, using the natural order of the element type.
     *
     * @param <E>      element type
     * @param elements the varargs
     * @return the set
     */
    @SafeVarargs
    public static <E extends Comparable<? super E>> TreeSet<E> of(E ... elements) {
        Ord<? super E> ord = Ord.fromComparable();
        return new TreeSet<>(Ord.fromComparable(), Node.fromIterable(ord, Arrays.asList(elements)));
    }

    /**
     * Creates a set from an {@link Iterable}, using the given {@link Ord}.
     *
     * @param <E>      element type
     * @param ord      the {@link Ord} instance
     * @param iterable the {@link Iterable}
     * @return the set
     */
    public static <E> TreeSet<E> fromIterable(Ord<E> ord, Iterable<E> iterable) {
        return new TreeSet<>(ord, Node.fromIterable(ord, iterable));
    }

    /**
     * Creates a set from varargs, using the given {@link Ord}.
     *
     * @param <E>      element type
     * @param ord      the {@link Ord} instance
     * @param elements the varargs
     * @return the set
     */
    @SafeVarargs
    public static <E> TreeSet<E> of(Ord<E> ord, E ... elements) {
        return new TreeSet<>(ord, Node.fromIterable(ord, Arrays.asList(elements)));
    }

    /**
     * Calculates the size of the set.
     *
     * @return the size
     */
    public int size() {
        return root.size();
    }

    /**
     * Checks whether this set is empty.
     * Note that using this method might be much faster than testing if {@link TreeSet#size} is equal to 0.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return root.isEmpty();
    }

    /**
     * Constructs a set from the current one, but also containing the given entry.
     *
     * @param value the element
     * @return the new set
     */
    public TreeSet<E> insert(E value) {
        return new TreeSet<>(ord, root.insert(ord, value));
    }

    /**
     * Constructs a set from the current one, but also containing the given entries.
     * Note that using this method might be slightly faster than using {@link TreeSet#insert} multiple times.
     *
     * @param iterable the entries, given as {@link Iterable}
     * @return the new set
     */
    public TreeSet<E> insertAll(Iterable<E> iterable) {
        return new TreeSet<>(ord, root.insertAll(ord, iterable));
    }

    /**
     * Constructs a set from the current one, but without the specified element.
     *
     * @param value the element
     * @return the new set
     */
    public TreeSet<E> delete(E value) {
        return new TreeSet<>(ord, root.delete(ord, value));
    }

    /**
     * Constructs a set from the current one, but without the smallest element.
     * Calling this method on an empty set returns an empty set.
     *
     * @return the new set
     */
    public TreeSet<E> deleteMin() {
        return new TreeSet<>(ord, root.deleteMin());
    }

    /**
     * Constructs a set from the current one, but without the largest element.
     * Calling this method on an empty set returns an empty set.
     *
     * @return the new set
     */
    public TreeSet<E> deleteMax() {
        return new TreeSet<>(ord, root.deleteMax());
    }

    /**
     * Tests if the set contains the given value
     *
     * @param value the element
     * @return true if the set contains the value
     */
    @Override
    public boolean test(E value) {
        return root.test(ord, value);
    }

    /**
     * Retrieves the smallest element.
     *
     * @return the smallest element
     * @throws NoSuchElementException when called on an empty set
     */
    public E minimum() throws NoSuchElementException {
        return root.minimum();
    }

    /**
     * Retrieves the largest element.
     *
     * @return the largest element
     * @throws NoSuchElementException when called on an empty set
     */
    public E maximum() {
        return root.maximum();
    }

    /**
     * Retrieves an ordered list of all elements of the set.
     *
     * @return the list
     */
    public List<E> toList() {
        return root.toList(e -> e);
    }

    /**
     * Retrieves a list of all transformed entries of the set, ordered by elements.
     *
     * @param function the transformation {@link Function}
     * @param <R>      result type
     * @return the list
     */
    public <R> List<R> toList(Function<? super E, ? extends R> function) {
        return root.toList(function);
    }


    @Override
    public Iterator<E> iterator() {
        return toList().iterator();
    }

    /**
     * Tests whether this set contains the same entries in the same order as another set.
     * Note that both sets can still rely on different {@link Ord} instances. As a consequence, e.g.
     * <code>m1.equals(m2)</code> doesn't imply <code>m1.insert(x,y).equals(m2.insert(x,y))</code>.
     *
     * @param o the {@link Object} to compare with
     * @return true if both sets contain the same entries in the same order
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TreeSet)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        TreeSet<?> that = (TreeSet<?>) o;
        return this.toList().equals(that.toList());
    }

    @Override
    public int hashCode() {
        return 257 + toList().hashCode();
    }

    @Override
    public String toString() {
        return Strings.mkEnclosed("Set(", ",", ")", toList());
    }

    /**
     * Calculates the union of two {@link TreeSet}s, so that the result contains all of their elements.
     * Note that both {@link TreeSet}s <strong>must</strong> use the same {@link Ord}.
     *
     * @param first  the first {@link TreeSet}
     * @param second the second {@link TreeSet}
     * @param <E>    element type
     * @return the union set
     */
    public static <E> TreeSet<E> union(TreeSet<E> first, TreeSet<E> second) {
        return new TreeSet<>(first.ord, Node.union(first.ord, first.root, second.root));
    }

    /**
     * Calculates the intersection of two {@link TreeSet}s, so that the result contains their common elements.
     * Note that both {@link TreeSet}s <strong>must</strong> use the same {@link Ord}.
     *
     * @param first  the first {@link TreeSet}
     * @param second the second {@link TreeSet}
     * @param <E>    element type
     * @return the intersection set
     */
    public static <E> TreeSet<E> intersection(TreeSet<E> first, TreeSet<E> second) {
        return new TreeSet<>(first.ord, Node.intersection(first.ord, first.root, second.root));
    }

    /**
     * Calculates the difference of two {@link TreeSet}s, so that the result contains only entries from the first set,
     * whose elements are not in the second set.
     * Note that both {@link TreeSet}s <strong>must</strong> use the same {@link Ord}.
     *
     * @param first  the first {@link TreeSet}
     * @param second the second {@link TreeSet}
     * @param <E>    element type
     * @return the intersection set
     */
    public static <E> TreeSet<E> difference(TreeSet<E> first, TreeSet<E> second) {
        return new TreeSet<>(first.ord, Node.difference(first.ord, first.root, second.root));
    }

    /**
     * Constructs the set monoid.
     *
     * @param <A> the element type
     * @return the monoid
     */
    public static <A extends Comparable<? super A>> Monoid<TreeSet<A>> monoid() {
        return Monoid.create(TreeSet.<A>empty(), TreeSet::union);
    }
}
