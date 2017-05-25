package org.highj.data.impl.treeMap;

import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.ord.Ord;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static org.highj.data.impl.treeMap.Node.Color.BLACK;
import static org.highj.data.impl.treeMap.Node.Color.RED;

/*
 Adapted from:

 Purely functional left-leaning red-black trees.
   * Robert Sedgewick, \"Left-Leaning Red-Black Trees\",
   Data structures seminar at Dagstuhl, Feb 2008.
   <http://www.cs.princeton.edu/~rs/talks/LLRB/LLRB.pdf>
   * Robert Sedgewick, \"Left-Leaning Red-Black Trees\",
   Analysis of Algorithms meeting at Maresias, Apr 2008
   <http://www.cs.princeton.edu/~rs/talks/LLRB/RedBlack.pdf>
*/
public abstract class Node<K, V> {

    private static final Ord<Integer> INTEGER_ORD = Ord.fromComparable();

    enum Color {RED, BLACK}

    private final static Node<?, ?> LEAF = black(0, null, null, null, null);

    private final int bHeight;
    private final K key;
    private final V value;
    private final Node<K, V> left;
    private final Node<K, V> right;

    private Node(int blackHeight, K key, V value, Node<K, V> left, Node<K, V> right) {
        this.bHeight = blackHeight;
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    abstract Color color();

    private boolean isRed() {
        return color() == RED;
    }

    private boolean isBlack() {
        return color() == BLACK;
    }

    private static <K, V> Node<K, V> black(int blackHeight, K key, V value, Node<K, V> left, Node<K, V> right) {
        return new Node<K, V>(blackHeight, key, value, left, right) {
            @Override
            Color color() {
                return BLACK;
            }
        };
    }

    private static <K, V> Node<K, V> red(int blackHeight, K key, V value, Node<K, V> left, Node<K, V> right) {
        return new Node<K, V>(blackHeight, key, value, left, right) {
            @Override
            Color color() {
                return RED;
            }
        };
    }

    private static <K, V> Node<K, V> node(Color color, int bHeight, K key, V value, Node<K, V> left, Node<K, V> right) {
        return color == RED
                ? red(bHeight, key, value, left, right)
                : black(bHeight, key, value, left, right);
    }


    @SuppressWarnings("unchecked")
    public static <K, V> Node<K, V> empty() {
        return (Node<K, V>) LEAF;
    }

    public static <K, V> Node<K, V> singleton(K k, V v) {
        return black(1, k, v, empty(), empty());
    }

    public boolean isEmpty() {
        return this == LEAF;
    }

    public static <K, V> Node<K, V> fromIterable(Ord<? super K> ord, Iterable<T2<K, V>> list) {
        return Node.<K, V>empty().insertAll(ord, list);
    }

    public Node<K, V> insertAll(Ord<? super K> ord, Iterable<T2<K, V>> iterable) {
        Node<K, V> result = this;
        for (T2<K, V> t2 : iterable) {
            result = result.insert(ord, t2._1(), t2._2());
        }
        return result;
    }

    public <R> List<R> toList(BiFunction<? super K, ? super V, ? extends R> function) {
        return inorder(function, List.empty());
    }

    private <R> List<R> inorder(BiFunction<? super K, ? super V, ? extends R> fn, List<R> list) {
        return isEmpty() ? list
                : left.inorder(fn, right.inorder(fn, list).plus(fn.apply(key, value)));
    }

    public Maybe<V> get(Ord<? super K> ord, K searchKey) {
        return isEmpty()
                ? Maybe.Nothing()
                : ord.cmp(searchKey, key)
                .caseLT(() -> left.get(ord, searchKey))
                .caseEQ(() -> Maybe.Just(value))
                .caseGT(() -> right.get(ord, searchKey));
    }

    public boolean containsKey(Ord<? super K> ord, K searchKey) {
        return isEmpty()
                ? false
                : ord.cmp(searchKey, key)
                .caseLT(() -> left.containsKey(ord, searchKey))
                .caseEQ(() -> true)
                .caseGT(() -> right.containsKey(ord, searchKey));
    }

    private boolean isBalanced() {
        return isBlackSame() && isRedSeparate();
    }

    private boolean isBlackSame() {
        List<Integer> list = blacks();
        return !list.tail().contains(x -> !Objects.equals(x, list.head()));
    }

    private List<Integer> blacks() {
        return blacks_(0);
    }

    private List<Integer> blacks_(int n) {
        return isEmpty() ? List.of(n + 1)
                : List.append(left.blacks_(n + color().ordinal()), right.blacks_(n + color().ordinal()));
    }

    private boolean isRedSeparate() {
        return reds(BLACK);
    }

    private boolean reds(Color c) {
        return isEmpty() || !(c == RED && isRed()) && left.reds(color()) && right.reds(color());
    }

    private boolean isOrdered(Ord<? super K> ord) {
        List<K> list = toList((k, v) -> k);
        return ! List.zipWith(list, list.tail(), ord::cmp).contains(o -> o != Ordering.LT);
    }

    private boolean blackHeight() {
        if (isRed()) {
            throw new AssertionError();
        }
        return isEmpty() || bh(bHeight);
    }

    private boolean bh(int n) {
        return isEmpty() ? n == 0 :
                n == bHeight + color().ordinal() - 1
                        && left.bh(n - color().ordinal())
                        && right.bh(n - color().ordinal());
    }

    private Node<K, V> turn(Color c) {
        if (isEmpty()) {
            throw new AssertionError();
        } else if (color() == c) {
            return this;
        }
        return node(color(), bHeight, key, value, left, right);
    }

    private Node<K, V> turnB_() {
        return isEmpty() ? this : turn(BLACK);
    }

    public T2<K, V> minimum() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return left.isEmpty() ? T2.of(key, value) : left.minimum();
        }
    }

    public T2<K, V> maximum() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return right.isEmpty() ? T2.of(key, value) : right.maximum();
        }
    }

    //for testing only
    String show() {
        return show("");
    }

    private String show(String padding) {
        return isEmpty() ? padding + "   L" : String.format("%s%n%s %s (%s,%s) %n%s",
                left.show(padding + "   "),
                padding, color().toString().charAt(0), key, value,
                right.show(padding + "   "));
    }

    private boolean isBlackLeftBlack() {
        return !isEmpty() && isBlack() && left.isBlack();
    }

    private boolean isBlackLeftRed() {
        return !isEmpty() && isBlack() && left.isRed();
    }

    private boolean valid(Ord<? super K> ord) {
        return isBalanced() && isLeftLean() && blackHeight() && isOrdered(ord);
    }

    private boolean isLeftLean() {
        return isEmpty() || !(isBlack() && right.isRed())
                && left.isLeftLean() && right.isLeftLean();
    }

    public int size() {
        return isEmpty() ? 0 : 1 + left.size() + right.size();
    }

    public Node<K, V> insert(Ord<? super K> ord, K k, V v) {
        return insert_(ord, k, v).turn(BLACK);
    }

    private Node<K, V> insert_(Ord<? super K> ord, K k, V v) {
        return isEmpty()
                ? red(1, k, v, empty(), empty())
                : ord.cmp(k, key)
                .caseLT(() -> balanceL(color(), bHeight, key, value, left.insert_(ord, k, v), right))
                .caseEQ(() -> node(color(), bHeight, k, v, left, right))
                .caseGT(() -> balanceR(color(), bHeight, key, value, left, right.insert_(ord, k, v)));
    }

    private static <K, V> Node<K, V> balanceL(Color c, int bh, K k, V v, Node<K, V> left, Node<K, V> right) {
        if (c == BLACK && left.isRed() && left.left.isRed()) {
            Node<K, V> newRight = black(bh, k, v, left.right, right);
            return red(bh + 1, left.key, left.value, left.left.turn(BLACK), newRight);
        } else {
            return node(c, bh, k, v, left, right);
        }
    }

    private static <K, V> Node<K, V> balanceR(Color c, int bh, K k, V v, Node<K, V> left, Node<K, V> right) {
        if ((c == BLACK) && left.isRed() && right.isRed()) {
            return red(bh + 1, k, v, left.turn(BLACK), right.turn(BLACK));
        } else if (right.isRed()) {
            Node<K, V> newLeft = red(right.bHeight, k, v, left, right.left);
            return node(c, bh, right.key, right.value, newLeft, right.right);
        } else {
            return node(c, bh, k, v, left, right);
        }
    }

    public Node<K, V> deleteMin() {
        Node<K, V> result = isEmpty() ? this : turn(RED).deleteMin_();
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<K, V> deleteMin_() {
        if (isBlack()) {
            throw new AssertionError("deleteMin_");
        } else if (left.isEmpty() && right.isEmpty()) {
            return empty();
        } else if (left.isRed()) {
            return red(bHeight, key, value, left.deleteMin_(), right);
        } else if (left.isBlackLeftBlack()) {
            return right.isBlackLeftRed()
                    ? hardMin()
                    : balanceR(BLACK, bHeight - 1, key, value, left.turn(RED).deleteMin_(), right.turn(RED));
        } else {
            Node<K, V> newLeft = black(this.left.bHeight, this.left.key, this.left.value, this.left.left.deleteMin_(), this.left.right);
            return red(bHeight, key, value, newLeft, right);
        }
    }

    private Node<K, V> hardMin() {
        Node<K, V> newLeft = black(right.bHeight, key, value, left.turn(RED).deleteMin_(), right.left.left);
        Node<K, V> newRight = black(right.bHeight, right.key, right.value, right.left.right, right.right);
        return red(bHeight, right.left.key, right.left.value, newLeft, newRight);
    }

    public Node<K, V> deleteMax() {
        Node<K, V> result = isEmpty() ? this : turn(RED).deleteMax_();
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<K, V> deleteMax_() {
        if (isBlack()) {
            throw new AssertionError("deleteMax_");
        } else if (left.isEmpty() && right.isEmpty()) {
            return empty();
        } else if (left.isRed()) {
            return rotateR();
        } else if (right.isBlackLeftBlack()) {
            return left.isBlackLeftRed() ? hardMax()
                    : balanceR(BLACK, bHeight - 1, key, value, left.turn(RED), right.turn(RED).deleteMax_());
        } else {
            return red(bHeight, key, value, left, right.rotateR());
        }
    }

    private Node<K, V> rotateR() {
        Node<K, V> newRight = red(bHeight, key, value, left.right, right).deleteMax_();
        return balanceR(color(), bHeight, left.key, left.value, left.left, newRight);
    }

    private Node<K, V> hardMax() {
        Node<K, V> newRightRight = right.turn(RED).deleteMax_();
        Node<K, V> newRight = balanceR(BLACK, left.bHeight, key, value, left.right, newRightRight);
        return red(bHeight, left.key, left.value, left.left.turn(BLACK), newRight);
    }

    public Node<K, V> delete(Ord<? super K> ord, K key) {
        Node<K, V> result = isEmpty() ? this : turn(RED).delete_(ord, key);
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<K, V> delete_(Ord<? super K> ord, K k) {
        return isEmpty()
                ? this
                : ord.cmp(k, key)
                .caseLT(() -> deleteLT(ord, k))
                .caseEQ(() -> deleteEQ(ord, k))
                .caseGT(() -> deleteGT(ord, k));
    }

    private Node<K, V> deleteLT(Ord<? super K> ord, K k) {
        if (isRed() && left.isBlackLeftBlack()) {
            if (right.isBlackLeftRed()) {
                Node<K, V> newLeft = black(right.bHeight, key, value, left.turn(RED).delete_(ord, k), right.left.left);
                Node<K, V> newRight = black(right.bHeight, right.key, right.value, right.left.right, right.right);
                return red(bHeight, right.left.key, right.left.value, newLeft, newRight);
            }
            return balanceR(BLACK, bHeight - 1, key, value, left.turn(RED).delete_(ord, k), right.turn(RED));
        }
        return node(color(), bHeight, key, value, left.delete_(ord, k), right);
    }

    private Node<K, V> deleteGT(Ord<? super K> ord, K k) {
        if (!isEmpty() && left.isRed()) {
            Node<K, V> newRight = red(bHeight, key, value, left.right, right).delete_(ord, k);
            return balanceR(color(), bHeight, left.key, left.value, left.left, newRight);
        } else if (!isEmpty() && isRed()) {
            if (right.isBlackLeftBlack()) {
                if (left.isBlackLeftRed()) {
                    Node<K, V> newRightRight = right.turn(RED).delete_(ord, k);
                    Node<K, V> newRight = balanceR(BLACK, left.bHeight, key, value, left.right, newRightRight);
                    return red(bHeight, left.key, left.value, left.left.turn(BLACK), newRight);
                }
                Node<K, V> newRight = right.turn(RED).delete_(ord, k);
                return balanceR(BLACK, bHeight - 1, key, value, left.turn(RED), newRight);
            }
            return red(bHeight, key, value, left, right.delete_(ord, k));
        }
        throw new AssertionError("deleteGT");
    }

    private Node<K, V> deleteEQ(Ord<? super K> ord, K k) {
        if (isRed() && left.isEmpty() && right.isEmpty()) {
            return empty();
        } else if (!left.isEmpty() && left.isRed()) {
            Node<K, V> newRight = red(bHeight, key, value, left.right, right).delete_(ord, k);
            return balanceR(color(), bHeight, left.key, left.value, left.left, newRight);
        } else if (isRed()) {
            T2<K, V> m = right.minimum();
            if (right.isBlackLeftBlack()) {
                if (left.isBlackLeftRed()) {
                    Node<K, V> newRightRight = right.turn(RED).deleteMin_();
                    Node<K, V> newRight = balanceR(BLACK, left.bHeight, m._1(), m._2(), left.right, newRightRight);
                    return balanceR(RED, bHeight, left.key, left.value, left.left.turn(BLACK), newRight);
                }
                return balanceR(BLACK, bHeight - 1, m._1(), m._2(), left.turn(RED), right.turn(RED).deleteMin_());
            }
            Node<K, V> newRight = black(right.bHeight, right.key, right.value, right.left.deleteMin_(), right.right);
            return red(bHeight, m._1(), m._2(), left, newRight);
        }
        throw new AssertionError("deleteEQ");
    }

    public <V1> Node<K, V1> mapValues(Function<? super V, ? extends V1> function) {
        return isEmpty()
                ? empty()
                : node(color(), bHeight, key, function.apply(value),
                left.mapValues(function), right.mapValues(function));
    }

    private static <K, V> Node<K, V> join(Ord<? super K> ord, K k, V v, Node<K, V> t1, Node<K, V> t2) {
        if (t1.isEmpty()) {
            return t2.insert(ord, k, v);
        } else if (t2.isEmpty()) {
            return t1.insert(ord, k, v);
        }
        return INTEGER_ORD.cmp(t1.bHeight, t2.bHeight)
                .caseLT(() -> joinLT(k, v, t1, t2))
                .caseEQ(() -> black(t1.bHeight + 1, k, v, t1, t2))
                .caseGT(() -> joinGT(k, v, t1, t2));
    }

    private static <K, V> Node<K, V> joinLT(K k, V v, Node<K, V> t1, Node<K, V> t2) {
        if (t1.bHeight == t2.bHeight) {
            return red(t1.bHeight + 1, k, v, t1, t2);
        } else {
            Node<K, V> newLeft = joinLT(k, v, t1, t2.left);
            return balanceL(t2.color(), t2.bHeight, t2.key, t2.value, newLeft, t2.right);
        }
    }

    private static <K, V> Node<K, V> joinGT(K k, V v, Node<K, V> t1, Node<K, V> t2) {
        if (t1.bHeight == t2.bHeight) {
            return red(t1.bHeight + 1, k, v, t1, t2);
        } else {
            Node<K, V> newRight = joinGT(k, v, t1.right, t2);
            return balanceR(t1.color(), t1.bHeight, t1.key, t1.value, t1.left, newRight);
        }
    }

    private static <K, V> Node<K, V> merge(Node<K, V> t1, Node<K, V> t2) {
        return t1.isEmpty() ? t2
                : t2.isEmpty() ? t1
                : INTEGER_ORD.cmp(t1.bHeight, t2.bHeight)
                .caseLT(() -> mergeLT(t1, t2, t1.bHeight))
                .caseEQ(() -> mergeEQ(t1, t2))
                .caseGT(() -> mergeGT(t1, t2, t2.bHeight))
                .turn(BLACK);
    }

    private static <K, V> Node<K, V> mergeLT(Node<K, V> t1, Node<K, V> t2, int h1) {
        return h1 == t2.bHeight
                ? mergeEQ(t1, t2)
                : balanceL(t2.color(), t2.bHeight, t2.key, t2.value, mergeLT(t1, t2.left, h1), t2.right);
    }

    private static <K, V> Node<K, V> mergeGT(Node<K, V> t1, Node<K, V> t2, int h2) {
        return t1.bHeight == h2
                ? mergeEQ(t1, t2)
                : balanceR(t1.color(), t1.bHeight, t1.key, t1.value, t1.left, mergeGT(t1.right, t2, h2));
    }

    private static <K, V> Node<K, V> mergeEQ(Node<K, V> t1, Node<K, V> t2) {
        if (t1.isEmpty() && t2.isEmpty()) {
            return empty();
        }
        T2<K, V> m = t2.minimum();
        Node<K, V> t2_ = t2.deleteMin();
        if (t1.bHeight == t2.bHeight) {
            return red(t1.bHeight + 1, m._1(), m._2(), t1, t2_);
        } else if (t1.left.isRed()) {
            Node<K, V> newRight = black(t1.bHeight, m._1(), m._2(), t1.right, t2_);
            return red(t1.bHeight + 1, t1.key, t1.value, t1.left.turn(BLACK), newRight);
        } else {
            return black(t1.bHeight, m._1(), m._2(), t1.turn(RED), t2_);
        }
    }

    private T3<Node<K, V>, Maybe<V>, Node<K, V>> split(Ord<? super K> ord, K k) {
        return isEmpty()
                ? T3.of(empty(), Maybe.Nothing(), empty())
                : ord.cmp(k, key)
                .caseLT(() -> left.split(ord, k).map_3(gt -> join(ord, key, value, gt, right)))
                .caseEQ(() -> T3.of(left.turnB_(), Maybe.Just(value), right))
                .caseGT(() -> right.split(ord, k).map_1(lt -> join(ord, key, value, left, lt)));
    }

    public static <K, V> Node<K, V> union(Ord<? super K> ord, BinaryOperator<V> op, Node<K, V> t1, Node<K, V> t2) {
        if (t2.isEmpty()) {
            return t1;
        } else if (t1.isEmpty()) {
            return t2.turnB_();
        } else {
            T3<Node<K, V>, Maybe<V>, Node<K, V>> splitted = t1.split(ord, t2.key);
            return join(ord, t2.key, splitted._2().map(v -> op.apply(v, t2.value)).getOrElse(t2.value),
                    union(ord, op, splitted._1(), t2.left),
                    union(ord, op, splitted._3(), t2.right));
        }
    }

    public static <K, V> Node<K, V> intersection(Ord<? super K> ord, BinaryOperator<V> op, Node<K, V> t1, Node<K, V> t2) {
        if (t1.isEmpty() || t2.isEmpty()) {
            return empty();
        }
        T3<Node<K, V>, Maybe<V>, Node<K, V>> splitted = t1.split(ord, t2.key);
        Node<K, V> newLeft = intersection(ord, op, splitted._1(), t2.left);
        Node<K, V> newRight = intersection(ord, op, splitted._3(), t2.right);
        return splitted._2()
                .map(v -> join(ord, t2.key, op.apply(v, t2.value), newLeft, newRight))
                .getOrElse(() -> merge(newLeft, newRight));
    }

    public static <K, V> Node<K, V> difference(Ord<? super K> ord, Node<K, V> t1, Node<K, V> t2) {
        if (t1.isEmpty()) {
            return empty();
        } else if (t2.isEmpty()) {
            return t1;
        }
        T3<Node<K, V>, Maybe<V>, Node<K, V>> splitted = t1.split(ord, t2.key);
        return merge(difference(ord, splitted._1(), t2.left),
                difference(ord, splitted._3(), t2.right));
    }
}
