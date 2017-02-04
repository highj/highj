package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple heap based PriorityQueue.
 *
 * I thought "Hey, I think heaps can be balanced wayyy easier than BSTs", and this is the result.
 * As a rule of thumb, if I think some code was "easy to write", it is usually either too slow or wrong.
 *
 * @param <A> element type
 */
public class PriorityQueue<A> implements __<PriorityQueue.µ,A>, Iterable<A> {

    public interface µ{}

    public enum QueueType {MIN, MAX}

    private final int size;
    private final Heap<A> root;
    private final Comparator<? super A> comparator;
    private final QueueType queueType;

    private PriorityQueue(Comparator<? super A> comparator, int size, Heap<A> root, QueueType queueType) {
        this.comparator = comparator;
        this.size = size;
        this.root = root;
        this.queueType = queueType;
    }

    @Override
    public Iterator<A> iterator() {
        return new Iterator<A>() {
            Heap<A> current = root;
            int sz = size;

            @Override
            public boolean hasNext() {
                return !current.isEmpty();
            }

            @Override
            public A next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                A result = current.value;
                current =  current.pop(comparator, sz--);
                return result;
            }
        };
    }

    /**
     * The size of the collection.
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Tests if the collection is empty.
     * @return true if empty
     */
    public boolean isEmpty() {
        return root.isEmpty();
    }

    /**
     * Creates a priority queue which pops its elements in ascending order, according to the given {@link Comparator}.
     * @param cmp the {@link Comparator}
     * @param as the elements of the queue
     * @param <A> the element type
     * @return the priority queue
     */
    @SafeVarargs
    public static <A> PriorityQueue<A> minQueueCmp(Comparator<? super A> cmp, A ... as) {
        return new PriorityQueue<A>(cmp, 0, Heap.empty(), QueueType.MIN).plus(as);
    }

    /**
     * Creates a priority queue which pops its elements in descending order, according to the given {@link Comparator}.
     * @param cmp the {@link Comparator}
     * @param as the elements of the queue
     * @param <A> the element type
     * @return the priority queue
     */
    @SafeVarargs
    public static <A> PriorityQueue<A> maxQueueCmp(Comparator<? super A> cmp, A ... as) {
        return new PriorityQueue<A>(cmp.reversed(), 0, Heap.empty(), QueueType.MAX).plus(as);
    }

    /**
     * Creates a priority queue which pops its elements in their natural order.
     * @param as the elements of the queue
     * @param <A> the element type
     * @return the priority queue
     */
    @SafeVarargs
    public static <A extends Comparable<? super A>> PriorityQueue<A> minQueue(A ... as) {
        return new PriorityQueue<>(Comparator.<A>naturalOrder(), 0, Heap.empty(), QueueType.MIN).plus(as);
    }

    /**
     * Creates a priority queue which pops its elements in their reversed natural order.
     * @param <A> the element type
     * @param as the elements of the queue
     * @return the priority queue
     */
    @SafeVarargs
    public static <A extends Comparable<? super A>> PriorityQueue<A> maxQueue(A ... as) {
        return new PriorityQueue<>(Comparator.<A>reverseOrder(), 0, Heap.empty(), QueueType.MAX).plus(as);
    }

    /**
     * The type of the priority queue.
     * @return MIN for a minimum queue, MAX for a maximum queue.
     */
    public QueueType queueType() {
        return queueType;
    }

    /**
     * Returns the current top value.
     * For a min queue, the returned value will be the smallest element in the collection, for a max queue the largest.
     * @return the next value
     */
    public A peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return root.value;
    }

    /**
     * Creates a new priority queue containing both the current elements and the given arguments.
     * @param as the new elements
     * @return a new priority queue
     */
    @SafeVarargs
    public final PriorityQueue<A> plus(A ... as) {
        Heap<A> result = root;
        int sz = size;
        for(A a : as) {
            result = result.plus(comparator, sz++, a);
        }
        return new PriorityQueue<>(comparator, sz, result, queueType);
    }

    /**
     * Creates a new priority queue, which doesn't contain the current top element.
     * @return a new priority queue
     */
    public PriorityQueue<A> drop() {
        return new PriorityQueue<>(comparator, size - 1, root.pop(comparator, size), queueType);
    }

    /**
     * Gives back a pair of the current top element, and a new priority queue not containing that element.
     * For a min queue, the returned value will be the smallest element in the collection, for a max queue the largest.
     * @return the tuple
     */
    public T2<A, PriorityQueue<A>> pop() {
       return T2.of(peek(), drop());
    }

    /**
     * Constructs an ordered list from the elements of the priority queue.
     * @return the list
     */
    public List<A> toList() {
        Heap<A> current = root;
        List<A> list = List.empty();
        int sz = size;
        while (! current.isEmpty()) {
           list = list.plus(current.value);
            current = current.pop(comparator, sz--);
        }
        return list.reverse();
    }

    //only for testing, expensive
    boolean balanced() {
        return root.balanced();
    }

    /* The idea is to keep the heap always balanced, which means the left sub-heap has the same size
     * or one element more than the right sub-heap. Under this assumption, the size variable of the
     * outer class holds all necessary information about the "shape" of the heap, allowing to keep
     * it balanced.
     */
    private static class Heap<A> {

        final static Heap<?> EMPTY = new Heap<>(null, null, null);

        private final A value;
        private final Heap<A> left;
        private final Heap<A> right;

        Heap(A value, Heap<A> left, Heap<A> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        boolean isEmpty() {
            return this == EMPTY;
        }

        @SuppressWarnings("unchecked")
        static <A> Heap<A> empty() {
            return (Heap<A>) EMPTY;
        }

        Heap<A> plus(Comparator<? super A> cmp, int size, A a) {
            if (isEmpty()) {
                return new Heap<>(a, empty(), empty());
            }
            boolean keepValue = cmp.compare(value, a) < 0;
            A newValue = keepValue ? value : a;
            A downValue = keepValue ? a : value;
            if (l(size) == r(size)) {
                return new Heap<>(newValue, left.plus(cmp, l(size), downValue), right);
            } else {
                return new Heap<>(newValue, left, right.plus(cmp, r(size), downValue));
            }
        }

        Heap<A> pop(Comparator<? super A> cmp, int size) {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
            return merge(cmp, size - 1, left, right);
        }

        private static <A> Heap<A> merge(Comparator<? super A> cmp, int size, Heap<A> left, Heap<A> right) {
            if (left.isEmpty()) {
                return right;
            } else if (right.isEmpty()) {
                return left;
            }
            boolean leftIsSmaller = cmp.compare(left.value, right.value) < 0;
            if (l(size) == r(size)) {
                return leftIsSmaller
                        ? new Heap<>(left.value, merge(cmp, l(size), left.left, left.right), right)
                        : new Heap<>(right.value, merge(cmp, r(size), right.left, right.right), left);
            } else {
                return leftIsSmaller
                        ? new Heap<>(left.value, right, merge(cmp, l(size), left.left, left.right))
                        : new Heap<>(right.value, left, merge(cmp, r(size), right.left, right.right));
            }
        }

        static int l(int size) {
            return size / 2;
        }

        static int r(int size) {
            return (size-1) / 2;
        }

        //only for testing, expensive
        boolean balanced() {
            if (isEmpty()) {
                return true;
            }
            int l = left.count();
            int r = right.count();
            return (l == r || l == r+1) & left.balanced() && right.balanced();
        }

        //only for testing, expensive
        int count() {
            if (isEmpty()) {
                return 0;
            }
            return 1 + left.count() + right.count();
        }
    }
}
