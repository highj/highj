package org.highj.data;

import org.highj.data.num.Integers;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;
import org.highj.typeclass1.monad.Monad;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asStream;
import static org.highj.data.Stream.*;

public class StreamTest {

    @Test
    public void testHead() {
        Stream<String> stream = cycle("foo");
        assertThat(stream.head()).isEqualTo("foo");
        stream = newStream("foo", cycle("bar", "baz"));
        assertThat(stream.head()).isEqualTo("foo");
        stream = newLazyStream("foo", () -> cycle("bar", "baz"));
        assertThat(stream.head()).isEqualTo("foo");
        stream = unfold(s -> s + "!", "foo");
        assertThat(stream.head()).isEqualTo("foo");
    }

    @Test
    public void testTail() {
        Stream<String> stream = cycle("foo");
        assertThat(stream.tail().head()).isEqualTo("foo");
        stream = newStream("foo", cycle("bar", "baz"));
        assertThat(stream.tail().head()).isEqualTo("bar");
        stream = newLazyStream("foo", () -> cycle("bar", "baz"));
        assertThat(stream.tail().head()).isEqualTo("bar");
        stream = unfold(s -> s + "!", "foo");
        assertThat(stream.tail().head()).isEqualTo("foo!");
    }

    @Test
    public void testStreamRepeat() {
        Stream<String> stream = cycle("foo");
        for (int i = 1; i < 10; i++) {
            assertThat(stream.head()).isEqualTo("foo");
            stream = stream.tail();
        }
    }

    @Test
    public void testStreamHeadFn() {
        Stream<String> stream = unfold(s -> s + "!", "foo");
        assertThat(stream.head()).isEqualTo("foo");
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo("foo!");
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo("foo!!");
    }

    @Test
    public void testStreamHeadStream() {
        Stream<String> stream = newStream("foo", cycle("bar"));
        assertThat(stream.head()).isEqualTo("foo");
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo("bar");
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo("bar");
    }

    @Test
    public void testStreamHeadThunk() {
        Stream<String> stream = newLazyStream("foo", () -> cycle("bar"));
        assertThat(stream.head()).isEqualTo("foo");
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo("bar");
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo("bar");
    }

    @Test
    public void testStreamIterator() {
        Iterator<Integer> myIterator = new Iterator<Integer>() {

            private int i = 0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return i++;
            }
        };

        Stream<Integer> stream = newLazyStream(myIterator);
        assertThat(stream.toString()).isEqualTo("Stream(0,1,2,3,4,5,6,7,8,9...)");
    }

    @Test
    public void testFilter() {
        Stream<Integer> stream = range(1).filter(Integers.even::test);
        assertThat(stream.head()).isEqualTo(Integer.valueOf(2));
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo(Integer.valueOf(4));
        stream = stream.tail();
        assertThat(stream.head()).isEqualTo(Integer.valueOf(6));

    }

    @Test
    public void testToString() {
        Stream<Integer> stream = range(1).filter(Integers.even::test);
        assertThat(stream.toString()).isEqualTo("Stream(2,4,6,8,10,12,14,16,18,20...)");
    }

    @Test
    public void testTake() {
        Stream<Integer> stream = range(1).filter(Integers.odd::test);
        assertThat(stream.take(4).toString()).isEqualTo("List(1,3,5,7)");
        assertThat(stream.take(0).toString()).isEqualTo("List()");
        assertThat(stream.take(-4).toString()).isEqualTo("List()");
    }

    @Test
    public void testTakeWhile() {
        Stream<Integer> stream = range(10, -3);
        assertThat(stream.takeWhile(Integers.positive).toString()).isEqualTo("List(10,7,4,1)");
        assertThat(stream.takeWhile(Integers.negative).toString()).isEqualTo("List()");
    }

    @Test
    public void testDrop() {
        Stream<Integer> stream = range(1);
        assertThat(stream.drop(4).head()).isEqualTo(Integer.valueOf(5));
        assertThat(stream.drop(0).head()).isEqualTo(Integer.valueOf(1));
        assertThat(stream.drop(-4).head()).isEqualTo(Integer.valueOf(1));
    }

    @Test
    public void testDropWhile() {
        Stream<Integer> stream = range(10, -3);
        assertThat(stream.dropWhile(Integers.positive).head()).isEqualTo(Integer.valueOf(-2));
        assertThat(stream.dropWhile(Integers.negative).head()).isEqualTo(Integer.valueOf(10));
    }

    @Test
    public void testRangeFrom() {
        Stream<Integer> stream = range(10);
        assertThat(stream.toString(4)).isEqualTo("Stream(10,11,12,13...)");
    }

    @Test
    public void testRangeFromTo() {
        Stream<Integer> stream = range(10, 3);
        assertThat(stream.toString(4)).isEqualTo("Stream(10,13,16,19...)");
        stream = range(10, 0);
        assertThat(stream.toString(4)).isEqualTo("Stream(10,10,10,10...)");
        stream = range(10, -3);
        assertThat(stream.toString(4)).isEqualTo("Stream(10,7,4,1...)");
    }

    @Test
    public void testCycle() {
        Stream<String> stream = cycle("foo", "bar", "baz");
        assertThat(stream.toString()).isEqualTo("Stream(foo,bar,baz,foo,bar,baz,foo,bar,baz,foo...)");
    }

    @Test
    public void testMap() {
        Stream<Integer> stream = cycle("one", "two", "three").map(String::length);
        assertThat(stream.toString()).isEqualTo("Stream(3,3,5,3,3,5,3,3,5,3...)");

    }

    @Test
    public void testZip() {
        Stream<T2<Integer, String>> stream = zip(range(1), cycle("foo", "bar", "baz"));
        assertThat(stream.toString(4)).isEqualTo("Stream((1,foo),(2,bar),(3,baz),(4,foo)...)");
    }

    @Test
    public void testZipWith() {
        Stream<String> stream = zipWith(Strings.repeat::apply, cycle("foo", "bar", "baz"), range(2));
        assertThat(stream.toString(4)).isEqualTo("Stream(foofoo,barbarbar,bazbazbazbaz,foofoofoofoofoo...)");
    }

    @Test
    public void testUnzip() {
        Stream<T2<Integer, String>> stream = zip(range(1), cycle("foo", "bar", "baz"));
        T2<Stream<Integer>, Stream<String>> t2 = unzip(stream);
        assertThat(t2._1().toString()).isEqualTo("Stream(1,2,3,4,5,6,7,8,9,10...)");
        assertThat(t2._2().toString()).isEqualTo("Stream(foo,bar,baz,foo,bar,baz,foo,bar,baz,foo...)");
    }

    @Test
    public void testIterator() {
        int n = 3;
        for (int i : range(3)) {
            assertThat(i).isEqualTo(n);
            n++;
            if (n > 10) {
                return;
            }
        }
    }

    @Test
    public void testMonad() {
        Monad<Stream.µ> monad = Stream.monad;

        Stream<String> foobars = newStream("foo", repeat("bars"));
        Stream<Integer> foobarsLength = asStream(monad.map(String::length, foobars));
        assertThat(foobarsLength.toString()).isEqualTo("Stream(3,4,4,4,4,4,4,4,4,4...)");

        Stream<String> foos = asStream(monad.pure("foo"));
        assertThat(foos.toString()).isEqualTo("Stream(foo,foo,foo,foo,foo,foo,foo,foo,foo,foo...)");

        Stream<Integer> absSqr = asStream(monad.ap(cycle(Integers.negate, Integers.sqr), range(1)));
        assertThat(absSqr.toString()).isEqualTo("Stream(-1,4,-3,16,-5,36,-7,64,-9,100...)");

        Stream<Integer> streamOfStream = asStream(monad.bind(range(1),
            integer -> range(1, integer)));
        assertThat(streamOfStream.toString()).isEqualTo("Stream(1,3,7,13,21,31,43,57,73,91...)");
    }

    @Test
    public void testInits() {
        Stream<List<Integer>> stream = range(1).inits();
        assertThat(stream.toString(4)).isEqualTo("Stream(List(),List(1),List(1,2),List(1,2,3)...)");
    }

    @Test
    public void testTails() {
        Stream<Stream<Integer>> stream = range(1).tails();
        assertThat(stream.map(x -> x.take(3)).toString(4))
            .isEqualTo("Stream(List(1,2,3),List(2,3,4),List(3,4,5),List(4,5,6)...)");
    }

    @Test
    public void testIntersperse() {
        Stream<Integer> stream = range(3).intersperse(0);
        assertThat(stream.toString()).isEqualTo("Stream(3,0,4,0,5,0,6,0,7,0...)");
    }

    @Test
    public void testInterleave() {
        Stream<Integer> stream = interleave(range(3), range(1));
        assertThat(stream.toString()).isEqualTo("Stream(3,1,4,2,5,3,6,4,7,5...)");
    }

}
