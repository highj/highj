package org.highj.data.impl.treeMap;

import com.sun.org.apache.regexp.internal.RE;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.compare.Ordering;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.compare.Ord;

import java.util.NoSuchElementException;
import java.util.Objects;

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
public class Node<K, V> {

    enum Color {RED, BLACK}

    private final static Node<?, ?> LEAF = new Node<>(BLACK, 0, null, null, null, null);

    private final Color color;
    private final int bHeight;
    private final K key;
    private final V value;
    private final Node<K, V> left;
    private final Node<K, V> right;

    private Node(Color color, int blackHeight, K key, V value, Node<K, V> left, Node<K, V> right) {
        this.color = color;
        this.bHeight = blackHeight;
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public boolean equals(Object o) {
        return !(o == null || !(o instanceof Node)) && (this == o || this.toList().equals(((Node) o).toList()));
    }

    @SuppressWarnings("unckecked")
    public static <K, V> Node<K, V> empty() {
        return (Node<K, V>) LEAF;
    }

    public static <K, V> Node<K, V> singleton(K k, V v) {
        return new Node<>(BLACK, 1, k, v, empty(), empty());
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

    public List<T2<K, V>> toList() {
        return inorder(List.empty());
    }

    private List<T2<K, V>> inorder(List<T2<K, V>> list) {
        return isEmpty() ? list : left.inorder(right.inorder(list).plus(T2.of(key, value)));
    }

    public List<K> toKeys() {
        return inorderKey(List.empty());
    }

    private List<K> inorderKey(List<K> list) {
        return isEmpty() ? list : left.inorderKey(right.inorderKey(list).plus(key));
    }

    public List<V> toValues() {
        return inorderValue(List.empty());
    }

    private List<V> inorderValue(List<V> list) {
        return isEmpty() ? list : left.inorderValue(right.inorderValue(list).plus(value));
    }

    public Maybe<V> get(Ord<? super K> ord, K searchKey) {
        if (isEmpty()) {
            return Maybe.Nothing();
        }
        switch (ord.cmp(searchKey, key)) {
            case LT:
                return left.get(ord, searchKey);
            case GT:
                return right.get(ord, searchKey);
            case EQ:
                return Maybe.Just(value);
            default:
                throw new AssertionError();
        }
    }

    public boolean containsKey(Ord<? super K> ord, K searchKey) {
        if (isEmpty()) {
            return false;
        }
        switch (ord.cmp(searchKey, key)) {
            case LT:
                return left.containsKey(ord, searchKey);
            case GT:
                return right.containsKey(ord, searchKey);
            case EQ:
                return true;
            default:
                throw new AssertionError();
        }
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
                : List.append(left.blacks_(n + color.ordinal()), right.blacks_(n + color.ordinal()));
    }

    private boolean isRedSeparate() {
        return reds(BLACK);
    }

    private boolean reds(Color c) {
        return isEmpty() || !(c == RED && color == RED) && left.reds(color) && right.reds(color);
    }

    private boolean isOrdered(Ord<? super K> ord) {
        List<K> list = toKeys();
        return !List.<K, K, Ordering>zipWith(list, list.tail(),
                x -> y -> ord.cmp(x, y)).contains(o -> o != Ordering.LT);
    }

    private boolean blackHeight() {
        if (color == RED) {
            throw new AssertionError();
        }
        return isEmpty() || bh(bHeight);
    }

    private boolean bh(int n) {
        return isEmpty() ? n == 0 :
                n == bHeight + color.ordinal() - 1
                        && left.bh(n - color.ordinal())
                        && right.bh(n - color.ordinal());
    }

    private Node<K, V> turn(Color c) {
        if (isEmpty()) {
            throw new AssertionError();
        }
        return color == c ? this : new Node<>(c, bHeight, key, value, left, right);
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

    public String show() {
        return show("");
    }

    private String show(String padding) {
        return isEmpty() ? padding + "   L" : String.format("%s%n%s %s (%s,%s) %n%s",
                left.show(padding + "   "),
                padding, color.toString().charAt(0), key, value,
                right.show(padding + "   "));
    }

    private boolean isBlackLeftBlack() {
        return !isEmpty() && color == BLACK && left.color == BLACK;
    }

    private boolean isBlackLeftRed() {
        return !isEmpty() && color == BLACK && left.color == RED;
    }

    private boolean valid(Ord<? super K> ord) {
        return isBalanced() && isLeftLean() && blackHeight() && isOrdered(ord);
    }

    private boolean isLeftLean() {
        return isEmpty() || !(color == BLACK && right.color == RED)
                && left.isLeftLean() && right.isLeftLean();
    }

    public int size() {
        return isEmpty() ? 0 : 1 + left.size() + right.size();
    }

    public Node<K, V> insert(Ord<? super K> ord, K k, V v) {
        return insert_(ord, k, v).turn(BLACK);
    }

    private Node<K, V> insert_(Ord<? super K> ord, K k, V v) {
        if (isEmpty()) {
            return new Node<>(RED, 1, k, v, empty(), empty());
        }
        switch (ord.cmp(k, key)) {
            case LT:
                return balanceL(color, bHeight, key, value, left.insert_(ord, k, v), right);
            case GT:
                return balanceR(color, bHeight, key, value, left, right.insert_(ord, k, v));
            case EQ:
                return new Node<>(color, bHeight, k, v, left, right);
            default:
                throw new AssertionError();
        }
    }

    private static <K, V> Node<K, V> balanceL(Color c, int bh, K k, V v, Node<K, V> left, Node<K, V> right) {
        if (c == BLACK && left.color == RED && left.left.color == RED) {
            return new Node<>(RED, bh + 1, left.key, left.value, left.left.turn(BLACK),
                    new Node<>(BLACK, bh, k, v, left.right, right));
        } else {
            return new Node<>(c, bh, k, v, left, right);
        }
    }

    private static <K, V> Node<K, V> balanceR(Color c, int bh, K k, V v, Node<K, V> left, Node<K, V> right) {
        if ((c == BLACK) && left.color == RED && right.color == RED) {
            return new Node<>(RED, bh + 1, k, v, left.turn(BLACK), right.turn(BLACK));
        } else if (right.color == RED) {
            return new Node<>(c, bh, right.key, right.value,
                    new Node<>(RED, right.bHeight, k, v, left, right.left), right.right);
        } else {
            return new Node<>(c, bh, k, v, left, right);
        }
    }

    public Node<K, V> deleteMin() {
        Node<K, V> result = isEmpty() ? this : turn(RED).deleteMin_();
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<K, V> deleteMin_() {
        if (color == BLACK) {
            throw new AssertionError("deleteMin_");
        }
        if (left.isEmpty() && right.isEmpty()) {
            return empty();
        }
        if (left.color == RED) {
            return new Node<>(RED, bHeight, key, value, left.deleteMin_(), right);
        }
        if (left.isBlackLeftBlack()) {
            return right.isBlackLeftRed()
                    ? hardMin()
                    : balanceR(BLACK, bHeight - 1, key, value, left.turn(RED).deleteMin_(), right.turn(RED));
        } else {
            Node<K, V> newLeft = new Node<>(BLACK, this.left.bHeight, this.left.key, this.left.value, this.left.left.deleteMin_(), this.left.right);
            return new Node<>(RED, bHeight, key, value, newLeft, right);
        }
    }

    private Node<K, V> hardMin() {
        Node<K, V> newLeft = new Node<>(BLACK, right.bHeight, key, value, left.turn(RED).deleteMin_(), right.left.left);
        Node<K, V> newRight = new Node<>(BLACK, right.bHeight, right.key, right.value, right.left.right, right.right);
        return new Node<>(RED, bHeight, right.left.key, right.left.value, newLeft, newRight);
    }

    public Node<K, V> deleteMax() {
        Node<K, V> result = isEmpty() ? this : turn(RED).deleteMax_();
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<K, V> deleteMax_() {
        if (color == BLACK) {
            throw new AssertionError("deleteMax_");
        }
        if (left.isEmpty() && right.isEmpty()) {
            return empty();
        }
        if (left.color == RED) {
            return rotateR();
        }
        if (right.isBlackLeftBlack()) {
            return left.isBlackLeftRed()
                    ? hardMax()
                    : balanceR(BLACK, bHeight - 1, key, value, left.turn(RED), right.turn(RED).deleteMax_());
        } else {
            return new Node<>(RED, bHeight, key, value, left, right.rotateR());
        }
    }

    private Node<K, V> rotateR() {
        Node<K, V> newRight = new Node<>(RED, bHeight, key, value, left.right, right).deleteMax_();
        return balanceR(color, bHeight, left.key, left.value, left.left, newRight);
    }

    private Node<K, V> hardMax() {
        Node<K, V> newRightRight = right.turn(RED).deleteMax_();
        Node<K, V> newRight = balanceR(BLACK, left.bHeight, key, value, left.right, newRightRight);
        return new Node<>(RED, bHeight, left.key, left.value, left.left.turn(BLACK), newRight);
    }

    public Node<K, V> delete(Ord<? super K> ord, K key) {
        Node<K, V> result = isEmpty() ? this : turn(RED).delete_(ord, key);
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private  Node<K, V> delete_(Ord<? super K> ord, K k) {
       if (isEmpty()) {
           return this;
       }
       switch(ord.cmp(k, key)) {
           case LT: return deleteLT(ord, k);
           case GT: return deleteGT(ord, k);
           case EQ: return deleteEQ(ord, k);
       }
       throw new AssertionError();
    }

    private Node<K,V> deleteLT(Ord<? super K> ord, K k) {
        if(color == RED && left.isBlackLeftBlack()) {
           if (right.isBlackLeftRed()) {
               Node<K,V> newLeft = new Node<>(BLACK, right.bHeight, key, value, left.turn(RED).delete_(ord,k), right.left.left);
               Node<K,V> newRight = new Node<>(BLACK, right.bHeight, right.key, right.value, right.left.right, right.right);
               return new Node<>(RED, bHeight, right.left.key, right.left.value, newLeft, newRight);
           }
           return balanceR(BLACK, bHeight - 1, key, value, left.turn(RED).delete_(ord, k), right.turn(RED));
        }
        return new Node<>(color, bHeight, key, value, left.delete_(ord,k), right);
    }

    private Node<K,V> deleteGT(Ord<? super K> ord, K k) {
        if (! isEmpty() && left.color == RED) {
            Node<K,V> newRight = new Node<>(RED, bHeight, key, value, left.right, right).delete_(ord,k);
            return balanceR(color, bHeight, left.key, left.value, left.left, newRight);
        } else if (! isEmpty() && color == RED) {
            if (right.isBlackLeftBlack()) {
                if (left.isBlackLeftRed()) {
                    Node<K,V> newRightRight = right.turn(RED).delete_(ord,k );
                    Node<K,V> newRight = balanceR(BLACK, left.bHeight, key, value, left.right, newRightRight);
                    return new Node<>(RED, bHeight, left.key, left.value, left.left.turn(BLACK), newRight);
                }
                Node<K,V> newRight = right.turn(RED).delete_(ord, k);
                return balanceR(BLACK, bHeight -1, key, value, left.turn(RED), newRight);
            }
            return new Node<>(RED, bHeight, key, value, left, right.delete_(ord, k));
        }
        throw new AssertionError("deleteGT");
    }

    private Node<K,V> deleteEQ(Ord<? super K> ord, K k) {
        if (color == RED && left.isEmpty() && right.isEmpty()) {
            return empty();
        }
        if (! left.isEmpty() && left.color == RED) {
            Node<K,V> newRight = new Node<>(RED, bHeight, key, value, left.right, right).delete_(ord,k);
            return balanceR(color, bHeight, left.key, left.value, left.left, newRight);
        }
        if (color == RED) {
            T2<K,V> m = right.minimum();
            if(right.isBlackLeftBlack()){
                if(left.isBlackLeftRed()) {
                    Node<K,V> newRightRight = right.turn(RED).deleteMin_();
                    Node<K,V> newRight = balanceR(BLACK, left.bHeight, m._1(), m._2(), left.right, newRightRight);
                    return balanceR(RED, bHeight, left.key, left.value, left.left.turn(BLACK), newRight);
                }
                return balanceR(BLACK, bHeight-1, m._1(), m._2(), left.turn(RED), right.turn(RED).deleteMin_());
            }
            Node<K,V> newRight = new Node<>(BLACK, right.bHeight, right.key, right.value, right.left.deleteMin_(), right.right);
            return new Node<>(RED, bHeight, m._1(), m._2(), left, newRight);
        }
        throw new AssertionError("deleteEQ");
    }

}
