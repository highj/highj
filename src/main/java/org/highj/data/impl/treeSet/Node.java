package org.highj.data.impl.treeSet;

import org.highj.data.List;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T2;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

import static org.highj.data.impl.treeSet.Node.Color.BLACK;
import static org.highj.data.impl.treeSet.Node.Color.RED;

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
public abstract class Node<E> {

    private static final Ord<Integer> INTEGER_ORD = Ord.fromComparable();

    enum Color {RED, BLACK}

    private final static Node<?> LEAF = black(0, null, null, null);

    private final int bHeight;
    private final E element;
    private final Node<E> left;
    private final Node<E> right;

    private Node(int blackHeight, E element, Node<E> left, Node<E> right) {
        this.bHeight = blackHeight;
        this.element = element;
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

    private static <E> Node<E> black(int blackHeight, E element, Node<E> left, Node<E> right) {
        return new Node<E>(blackHeight, element, left, right) {
            @Override
            Node.Color color() {
                return BLACK;
            }
        };
    }

    private static <E> Node<E> red(int blackHeight, E element, Node<E> left, Node<E> right) {
        return new Node<E>(blackHeight, element, left, right) {
            @Override
            Node.Color color() {
                return RED;
            }
        };
    }

    private static <E> Node<E> node(Color color, int bHeight, E element, Node<E> left, Node<E> right) {
        return color == RED
                ? red(bHeight, element, left, right)
                : black(bHeight, element, left, right);
    }


    @SuppressWarnings("unchecked")
    public static <E> Node<E> empty() {
        return (Node<E>) LEAF;
    }

    public static <E> Node<E> singleton(E element) {
        return black(1, element, empty(), empty());
    }

    public boolean isEmpty() {
        return this == LEAF;
    }

    public static <E> Node<E> fromIterable(Ord<? super E> ord, Iterable<E> iterable) {
        return Node.<E>empty().insertAll(ord, iterable);
    }

    public Node<E> insertAll(Ord<? super E> ord, Iterable<E> iterable) {
        Node<E> result = this;
        for (E e : iterable) {
            result = result.insert(ord, e);
        }
        return result;
    }

    public <R> List<R> toList(Function<? super E, ? extends R> function) {
        return inorder(function, List.empty());
    }

    private <R> List<R> inorder(Function<? super E, ? extends R> fn, List<R> list) {
        return isEmpty() ? list
                : left.inorder(fn, right.inorder(fn, list).plus(fn.apply(element)));
    }

    public boolean test(Ord<? super E> ord, E searchElement) {
        return isEmpty()
                ? false
                : ord.cmp(searchElement, element)
                .caseLT(() -> left.test(ord, searchElement))
                .caseEQ(() -> true)
                .caseGT(() -> right.test(ord, searchElement));
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

    private boolean isOrdered(Ord<? super E> ord) {
        List<E> list = toList(e -> e);
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

    private Node<E> turn(Color c) {
        if (isEmpty()) {
            throw new AssertionError();
        } else if (color() == c) {
            return this;
        }
        return node(color(), bHeight, element, left, right);
    }

    private Node<E> turnB_() {
        return isEmpty() ? this : turn(BLACK);
    }

    public E minimum() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return left.isEmpty() ? element : left.minimum();
        }
    }

    public E maximum() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return right.isEmpty() ? element : right.maximum();
        }
    }

    //for testing only
    String show() {
        return show("");
    }

    private String show(String padding) {
        return isEmpty() ? padding + "   L" : String.format("%s%n%s %s (%s) %n%s",
                left.show(padding + "   "),
                padding, color().toString().charAt(0), element,
                right.show(padding + "   "));
    }

    private boolean isBlackLeftBlack() {
        return !isEmpty() && isBlack() && left.isBlack();
    }

    private boolean isBlackLeftRed() {
        return !isEmpty() && isBlack() && left.isRed();
    }

    private boolean valid(Ord<? super E> ord) {
        return isBalanced() && isLeftLean() && blackHeight() && isOrdered(ord);
    }

    private boolean isLeftLean() {
        return isEmpty() || !(isBlack() && right.isRed())
                && left.isLeftLean() && right.isLeftLean();
    }

    public int size() {
        return isEmpty() ? 0 : 1 + left.size() + right.size();
    }

    public Node<E> insert(Ord<? super E> ord, E element) {
        return insert_(ord, element).turn(BLACK);
    }

    private Node<E> insert_(Ord<? super E> ord, E newElement) {
        return isEmpty()
                ? red(1, newElement, empty(), empty())
                : ord.cmp(newElement, element)
                .caseLT(() -> balanceL(color(), bHeight, element, left.insert_(ord, newElement), right))
                .caseEQ(() -> this)
                .caseGT(() -> balanceR(color(), bHeight, element, left, right.insert_(ord, newElement)));
    }

    private static <E> Node<E> balanceL(Color c, int bh, E newElement, Node<E> left, Node<E> right) {
        if (c == BLACK && left.isRed() && left.left.isRed()) {
            Node<E> newRight = black(bh, newElement, left.right, right);
            return red(bh + 1, left.element, left.left.turn(BLACK), newRight);
        } else {
            return node(c, bh, newElement, left, right);
        }
    }

    private static <E> Node<E> balanceR(Color c, int bh, E newElement, Node<E> left, Node<E> right) {
        if ((c == BLACK) && left.isRed() && right.isRed()) {
            return red(bh + 1, newElement, left.turn(BLACK), right.turn(BLACK));
        } else if (right.isRed()) {
            Node<E> newLeft = red(right.bHeight, newElement, left, right.left);
            return node(c, bh, right.element, newLeft, right.right);
        } else {
            return node(c, bh, newElement, left, right);
        }
    }

    public Node<E> deleteMin() {
        Node<E> result = isEmpty() ? this : turn(RED).deleteMin_();
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<E> deleteMin_() {
        if (isBlack()) {
            throw new AssertionError("deleteMin_");
        } else if (left.isEmpty() && right.isEmpty()) {
            return empty();
        } else if (left.isRed()) {
            return red(bHeight, element, left.deleteMin_(), right);
        } else if (left.isBlackLeftBlack()) {
            return right.isBlackLeftRed()
                    ? hardMin()
                    : balanceR(BLACK, bHeight - 1, element, left.turn(RED).deleteMin_(), right.turn(RED));
        } else {
            Node<E> newLeft = black(this.left.bHeight, this.left.element, this.left.left.deleteMin_(), this.left.right);
            return red(bHeight, element, newLeft, right);
        }
    }

    private Node<E> hardMin() {
        Node<E> newLeft = black(right.bHeight, element, left.turn(RED).deleteMin_(), right.left.left);
        Node<E> newRight = black(right.bHeight, right.element, right.left.right, right.right);
        return red(bHeight, right.left.element, newLeft, newRight);
    }

    public Node<E> deleteMax() {
        Node<E> result = isEmpty() ? this : turn(RED).deleteMax_();
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<E> deleteMax_() {
        if (isBlack()) {
            throw new AssertionError("deleteMax_");
        } else if (left.isEmpty() && right.isEmpty()) {
            return empty();
        } else if (left.isRed()) {
            return rotateR();
        } else if (right.isBlackLeftBlack()) {
            return left.isBlackLeftRed() ? hardMax()
                    : balanceR(BLACK, bHeight - 1, element, left.turn(RED), right.turn(RED).deleteMax_());
        } else {
            return red(bHeight, element, left, right.rotateR());
        }
    }

    private Node<E> rotateR() {
        Node<E> newRight = red(bHeight, element, left.right, right).deleteMax_();
        return balanceR(color(), bHeight, left.element, left.left, newRight);
    }

    private Node<E> hardMax() {
        Node<E> newRightRight = right.turn(RED).deleteMax_();
        Node<E> newRight = balanceR(BLACK, left.bHeight, element, left.right, newRightRight);
        return red(bHeight, left.element, left.left.turn(BLACK), newRight);
    }

    public Node<E> delete(Ord<? super E> ord, E element) {
        Node<E> result = isEmpty() ? this : turn(RED).delete_(ord, element);
        return result.isEmpty() ? result : result.turn(BLACK);
    }

    private Node<E> delete_(Ord<? super E> ord, E delElement) {
        return isEmpty()
                ? this
                : ord.cmp(delElement, element)
                .caseLT(() -> deleteLT(ord, delElement))
                .caseEQ(() -> deleteEQ(ord, delElement))
                .caseGT(() -> deleteGT(ord, delElement));
    }

    private Node<E> deleteLT(Ord<? super E> ord, E delElement) {
        if (isRed() && left.isBlackLeftBlack()) {
            if (right.isBlackLeftRed()) {
                Node<E> newLeft = black(right.bHeight, element, left.turn(RED).delete_(ord, delElement), right.left.left);
                Node<E> newRight = black(right.bHeight, right.element, right.left.right, right.right);
                return red(bHeight, right.left.element, newLeft, newRight);
            }
            return balanceR(BLACK, bHeight - 1, element, left.turn(RED).delete_(ord, delElement), right.turn(RED));
        }
        return node(color(), bHeight, element, left.delete_(ord, delElement), right);
    }

    private Node<E> deleteGT(Ord<? super E> ord, E delElement) {
        if (!isEmpty() && left.isRed()) {
            Node<E> newRight = red(bHeight, element, left.right, right).delete_(ord, delElement);
            return balanceR(color(), bHeight, left.element, left.left, newRight);
        } else if (!isEmpty() && isRed()) {
            if (right.isBlackLeftBlack()) {
                if (left.isBlackLeftRed()) {
                    Node<E> newRightRight = right.turn(RED).delete_(ord, delElement);
                    Node<E> newRight = balanceR(BLACK, left.bHeight, element, left.right, newRightRight);
                    return red(bHeight, left.element, left.left.turn(BLACK), newRight);
                }
                Node<E> newRight = right.turn(RED).delete_(ord, delElement);
                return balanceR(BLACK, bHeight - 1, element, left.turn(RED), newRight);
            }
            return red(bHeight, element, left, right.delete_(ord, delElement));
        }
        throw new AssertionError("deleteGT");
    }

    private Node<E> deleteEQ(Ord<? super E> ord, E delElement) {
        if (isRed() && left.isEmpty() && right.isEmpty()) {
            return empty();
        } else if (!left.isEmpty() && left.isRed()) {
            Node<E> newRight = red(bHeight, element, left.right, right).delete_(ord, delElement);
            return balanceR(color(), bHeight, left.element, left.left, newRight);
        } else if (isRed()) {
            E m = right.minimum();
            if (right.isBlackLeftBlack()) {
                if (left.isBlackLeftRed()) {
                    Node<E> newRightRight = right.turn(RED).deleteMin_();
                    Node<E> newRight = balanceR(BLACK, left.bHeight, m, left.right, newRightRight);
                    return balanceR(RED, bHeight, left.element, left.left.turn(BLACK), newRight);
                }
                return balanceR(BLACK, bHeight - 1, m, left.turn(RED), right.turn(RED).deleteMin_());
            }
            Node<E> newRight = black(right.bHeight, right.element, right.left.deleteMin_(), right.right);
            return red(bHeight, m, left, newRight);
        }
        throw new AssertionError("deleteEQ");
    }

    private static <E> Node<E> join(Ord<? super E> ord, E element, Node<E> t1, Node<E> t2) {
        if (t1.isEmpty()) {
            return t2.insert(ord, element);
        } else if (t2.isEmpty()) {
            return t1.insert(ord, element);
        }
        return INTEGER_ORD.cmp(t1.bHeight, t2.bHeight)
                .caseLT(() -> joinLT(element, t1, t2))
                .caseEQ(() -> black(t1.bHeight + 1, element, t1, t2))
                .caseGT(() -> joinGT(element, t1, t2));
    }

    private static <E> Node<E> joinLT(E element, Node<E> t1, Node<E> t2) {
        if (t1.bHeight == t2.bHeight) {
            return red(t1.bHeight + 1, element, t1, t2);
        } else {
            Node<E> newLeft = joinLT(element, t1, t2.left);
            return balanceL(t2.color(), t2.bHeight, t2.element, newLeft, t2.right);
        }
    }

    private static <E> Node<E> joinGT(E element, Node<E> t1, Node<E> t2) {
        if (t1.bHeight == t2.bHeight) {
            return red(t1.bHeight + 1, element, t1, t2);
        } else {
            Node<E> newRight = joinGT(element, t1.right, t2);
            return balanceR(t1.color(), t1.bHeight, t1.element, t1.left, newRight);
        }
    }

    private static <E> Node<E> merge(Node<E> t1, Node<E> t2) {
        return t1.isEmpty() ? t2
                : t2.isEmpty() ? t1
                : INTEGER_ORD.cmp(t1.bHeight, t2.bHeight)
                .caseLT(() -> mergeLT(t1, t2, t1.bHeight))
                .caseEQ(() -> mergeEQ(t1, t2))
                .caseGT(() -> mergeGT(t1, t2, t2.bHeight))
                .turn(BLACK);
    }

    private static <E> Node<E> mergeLT(Node<E> t1, Node<E> t2, int h1) {
        return h1 == t2.bHeight
                ? mergeEQ(t1, t2)
                : balanceL(t2.color(), t2.bHeight, t2.element, mergeLT(t1, t2.left, h1), t2.right);
    }

    private static <E> Node<E> mergeGT(Node<E> t1, Node<E> t2, int h2) {
        return t1.bHeight == h2
                ? mergeEQ(t1, t2)
                : balanceR(t1.color(), t1.bHeight, t1.element, t1.left, mergeGT(t1.right, t2, h2));
    }

    private static <E> Node<E> mergeEQ(Node<E> t1, Node<E> t2) {
        if (t1.isEmpty() && t2.isEmpty()) {
            return empty();
        }
        E m = t2.minimum();
        Node<E> t2_ = t2.deleteMin();
        if (t1.bHeight == t2.bHeight) {
            return red(t1.bHeight + 1, m, t1, t2_);
        } else if (t1.left.isRed()) {
            Node<E> newRight = black(t1.bHeight, m, t1.right, t2_);
            return red(t1.bHeight + 1, t1.element, t1.left.turn(BLACK), newRight);
        } else {
            return black(t1.bHeight, m, t1.turn(RED), t2_);
        }
    }

    private T2<Node<E>, Node<E>> split(Ord<? super E> ord, E splitElement) {
        return isEmpty()
                ? T2.of(empty(), empty())
                : ord.cmp(splitElement, element)
                .caseLT(() -> left.split(ord, splitElement).map_2(gt -> join(ord, element, gt, right)))
                .caseEQ(() -> T2.of(left.turnB_(), right))
                .caseGT(() -> right.split(ord, splitElement).map_1(lt -> join(ord, element, left, lt)));
    }

    public static <E> Node<E> union(Ord<? super E> ord, Node<E> t1, Node<E> t2) {
        if (t2.isEmpty()) {
            return t1;
        } else if (t1.isEmpty()) {
            return t2.turnB_();
        } else {
            T2<Node<E>, Node<E>> splitted = t1.split(ord, t2.element);
            return join(ord, t2.element,
                    union(ord, splitted._1(), t2.left),
                    union(ord, splitted._2(), t2.right));
        }
    }

    public static <E> Node<E> intersection(Ord<? super E> ord, Node<E> t1, Node<E> t2) {
        if (t1.isEmpty() || t2.isEmpty()) {
            return empty();
        }
        T2<Node<E>, Node<E>> splitted = t1.split(ord, t2.element);
        Node<E> newLeft = intersection(ord, splitted._1(), t2.left);
        Node<E> newRight = intersection(ord, splitted._2(), t2.right);
        return  t1.test(ord, t2.element)
                  ? join(ord, t2.element, newLeft, newRight)
                  : merge(newLeft, newRight);
    }

    public static <E> Node<E> difference(Ord<? super E> ord, Node<E> t1, Node<E> t2) {
        if (t1.isEmpty()) {
            return empty();
        } else if (t2.isEmpty()) {
            return t1;
        }
        T2<Node<E>, Node<E>> splitted = t1.split(ord, t2.element);
        return merge(difference(ord, splitted._1(), t2.left),
                difference(ord, splitted._2(), t2.right));
    }

    @Override
    public String toString() {
        return isEmpty() ? "LEAF" :
                color() + "{" + element + ", left=" + left + ", right=" + right + '}';
    }
}
