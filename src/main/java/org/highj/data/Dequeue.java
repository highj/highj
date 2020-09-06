package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.instance.dequeue.DequeueTraversable;
import org.highj.data.instance.dequeue.DequeueFunctor;
import org.highj.function.Strings;
import org.highj.data.tuple.T2;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

//BankersQueue implementation of Haskell package dequeue-0.1.5
//... as described in Chris Okasaki's Purely Functional Data Structures.
public class Dequeue<A> implements __<Dequeue.µ, A>, Iterable<A> {

    public static final class µ {
    }

    //The maximum number of times longer one half of a 'BankersDequeue' is permitted to be relative to the other.
    private final static int BALANCE = 4;

    private final int sizeFront, sizeRear;
    private final List<A> front, rear;

    //adding the check directly in the constructor avoids
    //... unnecessary object creations
    //... to forget to call check method
    private Dequeue(int sizeFront, List<A> front, int sizeRear, List<A> rear) {
        //check q@(BankersDequeue sizeF front sizeR rear)
        //    | sizeF > c * sizeR + 1 =
        //        let front' = take size1 front
        //            rear' = rear ++ reverse (drop size1 front)
        //        in BankersDequeue size1 front' size2 rear'
        //    | sizeR > c * sizeF + 1 =
        //        let front' = front ++ reverse (drop size1 rear)
        //            rear' = take size1 rear
        //        in BankersDequeue size2 front' size1 rear'
        //    | otherwise = q
        //    where
        //        size1 = (sizeF + sizeR) `div` 2
        //        size2 = (sizeF + sizeR) - size1
        //        c = bqBalance
        int size1 = (sizeFront + sizeRear) / 2;
        int size2 = (sizeFront + sizeRear) - size1;

        if (sizeFront > BALANCE * sizeRear + 1) {
            this.sizeFront = size1;
            this.front = front.take(size1);
            this.sizeRear = size2;
            this.rear = List.append(rear, front.drop(size1).reverse());
        } else if (sizeRear > BALANCE * sizeFront + 1) {
            this.sizeFront = size2;
            this.front = List.append(front, rear.drop(size1).reverse());
            this.sizeRear = size1;
            this.rear = rear.take(size1);
        } else {
            this.sizeFront = sizeFront;
            this.front = front;
            this.sizeRear = sizeRear;
            this.rear = rear;
        }
    }

    public <B> Dequeue<B> map(Function<? super A, ? extends B> f) {
        //fmap f (BankersDequeue sizeF front sizeR rear) =
        //  BankersDequeue sizeF (fmap f front) sizeR (fmap f rear)
        return new Dequeue<B>(sizeFront, front.map(f), sizeRear, rear.map(f));
    }

    public List<A> toList() {
        return List.append(front, rear.reverse());
    }

    public static <A> Dequeue<A> empty() {
        //empty = BankersDequeue 0 [] 0 []
        return new Dequeue<>(0, List.of(), 0, List.of());
    }

    @SafeVarargs
    public static <A> Dequeue<A> of(A ... values) {
        return fromList(List.of(values));
    }

    public boolean isEmpty() {
        return sizeFront == 0 && sizeRear == 0;
    }

    public int size() {
        //length (BankersDequeue sizeF __ sizeR __) = sizeF + sizeR
        return sizeFront + sizeRear;
    }

    public Maybe<A> getFirst() {
        //first (BankersDequeue __ [] __ [x]) = Just x
        //first (BankersDequeue __ front __ __) =  headMay front
        return sizeFront == 0 && sizeRear == 1 ? rear.maybeHead() : front.maybeHead();
    }

    public Maybe<A> getLast() {
        //last (BankersDequeue __ [x] __ []) = Just x
        //last (BankersDequeue __ __ __ rear) = headMay rear
        return sizeFront == 1 && sizeRear == 0 ? front.maybeHead() : rear.maybeHead();
    }

    public List<A> takeFront(int i) {
        //takeFront i (BankersDequeue sizeF front __ rear) =
        //take i front ++ take (i - sizeF) (reverse rear)
        return i <= sizeFront ? front.take(i) : List.append(front.take(i), rear.reverse().take(i - sizeFront));
    }

    public List<A> takeBack(int i) {
        //takeBack i (BankersDequeue __ front sizeR rear) =
        //  take i rear ++ take (i - sizeR) (reverse front)
        return i <= sizeRear ? rear.take(i) : List.append(rear.take(i), front.reverse().take(i - sizeRear));
    }

    public Dequeue<A> pushFront(A a) {
        //pushFront (BankersDequeue sizeF front sizeR rear) x =
        //        check $ BankersDequeue (sizeF + 1) (x : front) sizeR rear
        return new Dequeue<>(sizeFront + 1, front.plus(a), sizeRear, rear);
    }

    public T2<Maybe<A>, Dequeue<A>> popFront() {
        //popFront (BankersDequeue __ [] __ []) = (Nothing, empty)
        //popFront (BankersDequeue __ [] __ [x]) = (Just x, empty)
        //popFront (BankersDequeue __ [] __ __) = error "Queue is too far unbalanced."
        //popFront (BankersDequeue sizeF (f : fs) sizeR rear) =
        //  (Just f, check $ BankersDequeue (sizeF - 1) fs sizeR rear)
        if (sizeFront == 0) {
            switch(sizeRear) {
                case 0 : return T2.of(Maybe.Nothing(), of());
                case 1: return  T2.of(rear.maybeHead(), of());
                default: throw new AssertionError("Queue is too far unbalanced.");
            }
        } else {
            return T2.of(front.maybeHead(), new Dequeue<A>(sizeFront - 1, front.tail(), sizeRear, rear));
        }
    }

    public Dequeue<A> pushBack(A a) {
        //pushBack (BankersDequeue sizeF front sizeR rear) x =
        //        check $ BankersDequeue sizeF front (sizeR + 1) (x : rear)
        return new Dequeue<>(sizeFront, front, sizeRear + 1, rear.plus(a));
    }

    public T2<Maybe<A>, Dequeue<A>> popBack() {
        //popBack (BankersDequeue __ [] __ []) = (Nothing, empty)
        //popBack (BankersDequeue __ [x] __ []) = (Just x, empty)
        //popBack (BankersDequeue __ __ __ []) = error "Queue is too far unbalanced."
        //popBack (BankersDequeue sizeF front sizeR (r : rs)) =
        //  (Just r, check $ BankersDequeue sizeF front (sizeR - 1) rs)
        if (sizeRear == 0) {
            switch(sizeFront) {
                case 0 : return T2.of(Maybe.Nothing(), of());
                case 1: return  T2.of(front.maybeHead(), of());
                default: throw new AssertionError("Queue is too far unbalanced.");
            }
        } else {
            return T2.of(rear.maybeHead(), new Dequeue<A>(sizeFront, front, sizeRear - 1, rear.tail()));
        }
    }

    public static <A> Dequeue<A> fromList(List<A> list) {
        // fromList list = check $ BankersDequeue (List.length list) list 0 []
        return new Dequeue<>(list.size(), list, 0, List.of());
    }

    @Override
    public String toString() {
        return Strings.mkEnclosed("Dequeue(", ",", ")", this);
    }

    @Override
    public Iterator<A> iterator() {
        return toList().iterator();
    }

    public <B> B foldr(BiFunction<A, B, B> fn, B b) {
        return toList().foldr(fn, b);
    }

    public <B> B foldl(B b, BiFunction<B, A, B> fn) {
        return toList().foldl(b, fn);
    }

    public static final DequeueFunctor functor = new DequeueFunctor() {};

    public static final DequeueTraversable traversable = new DequeueTraversable() {};
}
