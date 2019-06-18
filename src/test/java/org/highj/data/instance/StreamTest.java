package org.highj.data.instance;

import org.highj.data.List;
import org.highj.data.Stream;
import org.highj.data.num.Integers;
import org.highj.data.tuple.T2;
import org.highj.function.Strings;
import org.highj.typeclass1.monad.Monad;
import org.junit.Test;

import java.util.Iterator;

import static junit.framework.Assert.assertEquals;
import static org.highj.Hkt.asStream;
import static org.highj.data.Stream.*;

public class StreamTest {

    @Test
    public void testHead()  {
        Stream<String> stream = cycle("foo");
        assertEquals("foo", stream.head());
        stream = newStream("foo", cycle("bar", "baz"));
        assertEquals("foo", stream.head());
        stream = newLazyStream("foo", () -> cycle("bar", "baz"));
        assertEquals("foo", stream.head());
        stream = unfold(s -> s + "!", "foo");
        assertEquals("foo", stream.head());
    }

    @Test
    public void testTail()  {
        Stream<String> stream = cycle("foo");
        assertEquals("foo", stream.tail().head());
        stream = newStream("foo", cycle("bar", "baz"));
        assertEquals("bar", stream.tail().head());
        stream = newLazyStream("foo", () -> cycle("bar", "baz"));
        assertEquals("bar", stream.tail().head());
        stream = unfold(s -> s + "!", "foo");
        assertEquals("foo!", stream.tail().head());
    }

    @Test
    public void testStreamRepeat()  {
        Stream<String> stream = cycle("foo");
        for (int i = 1; i < 10; i++) {
            assertEquals("foo", stream.head());
            stream = stream.tail();
        }
    }

    @Test
    public void testStreamHeadFn()  {
        Stream<String> stream = unfold(s -> s + "!", "foo");
        assertEquals("foo", stream.head());
        stream = stream.tail();
        assertEquals("foo!", stream.head());
        stream = stream.tail();
        assertEquals("foo!!", stream.head());
    }

    @Test
    public void testStreamHeadStream()  {
        Stream<String> stream = newStream("foo", cycle("bar"));
        assertEquals("foo", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
    }

    @Test
    public void testStreamHeadThunk()  {
        Stream<String> stream = newLazyStream("foo", () -> cycle("bar"));
        assertEquals("foo", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
        stream = stream.tail();
        assertEquals("bar", stream.head());
    }

    @Test
    public void testStreamIterator()  {
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
        assertEquals("Stream(0,1,2,3,4,5,6,7,8,9...)", stream.toString());
    }

    @Test
    public void testFilter()  {
        Stream<Integer> stream = range(1).filter(Integers.even::test);
        assertEquals(Integer.valueOf(2), stream.head());
        stream = stream.tail();
        assertEquals(Integer.valueOf(4), stream.head());
        stream = stream.tail();
        assertEquals(Integer.valueOf(6), stream.head());

    }

    @Test
    public void testToString()  {
        Stream<Integer> stream = range(1).filter(Integers.even::test);
        assertEquals("Stream(2,4,6,8,10,12,14,16,18,20...)", stream.toString());
    }

    @Test
    public void testTake()  {
        Stream<Integer> stream = range(1).filter(Integers.odd::test);
        assertEquals("List(1,3,5,7)", stream.take(4).toString());
        assertEquals("List()", stream.take(0).toString());
        assertEquals("List()", stream.take(-4).toString());
    }

    @Test
    public void testTakeWhile()  {
        Stream<Integer> stream = range(10, -3);
        assertEquals("List(10,7,4,1)", stream.takeWhile(Integers.positive).toString());
        assertEquals("List()", stream.takeWhile(Integers.negative).toString());
    }

    @Test
    public void testDrop()  {
        Stream<Integer> stream = range(1);
        assertEquals(Integer.valueOf(5), stream.drop(4).head());
        assertEquals(Integer.valueOf(1), stream.drop(0).head());
        assertEquals(Integer.valueOf(1), stream.drop(-4).head());
    }

    @Test
    public void testDropWhile()  {
        Stream<Integer> stream = range(10, -3);
        assertEquals(Integer.valueOf(-2), stream.dropWhile(Integers.positive).head());
        assertEquals(Integer.valueOf(10), stream.dropWhile(Integers.negative).head());
    }

    @Test
    public void testRangeFrom()  {
        Stream<Integer> stream = range(10);
        assertEquals("Stream(10,11,12,13...)", stream.toString(4));
    }

    @Test
    public void testRangeFromTo()  {
        Stream<Integer> stream = range(10, 3);
        assertEquals("Stream(10,13,16,19...)", stream.toString(4));
        stream = range(10, 0);
        assertEquals("Stream(10,10,10,10...)", stream.toString(4));
        stream = range(10, -3);
        assertEquals("Stream(10,7,4,1...)", stream.toString(4));
    }

    @Test
    public void testCycle()  {
        Stream<String> stream = cycle("foo", "bar", "baz");
        assertEquals("Stream(foo,bar,baz,foo,bar,baz,foo,bar,baz,foo...)", stream.toString());
    }

    @Test
    public void testMap()  {
        Stream<Integer> stream = cycle("one", "two", "three").map(String::length);
        assertEquals("Stream(3,3,5,3,3,5,3,3,5,3...)", stream.toString());

    }

    @Test
    public void testZip()  {
        Stream<T2<Integer, String>> stream = zip(range(1), cycle("foo", "bar", "baz"));
        assertEquals("Stream((1,foo),(2,bar),(3,baz),(4,foo)...)", stream.toString(4));
    }

    @Test
    public void testZipWith()  {
        Stream<String> stream = zipWith(Strings.repeat::apply, cycle("foo", "bar", "baz"), range(2));
        assertEquals("Stream(foofoo,barbarbar,bazbazbazbaz,foofoofoofoofoo...)", stream.toString(4));
    }

    @Test
    public void testUnzip()  {
        Stream<T2<Integer, String>> stream = zip(range(1), cycle("foo", "bar", "baz"));
        T2<Stream<Integer>, Stream<String>> t2 = unzip(stream);
        assertEquals("Stream(1,2,3,4,5,6,7,8,9,10...)", t2._1().toString());
        assertEquals("Stream(foo,bar,baz,foo,bar,baz,foo,bar,baz,foo...)", t2._2().toString());
    }

    @Test
    public void testIterator()  {
        int n = 3;
        for (int i : range(3)) {
            assertEquals(n, i);
            n++;
            if (n > 10) {
                return;
            }
        }
    }

    @Test
    public void testMonad()  {
        Monad<Stream.µ> monad = Stream.monad;

        Stream<String> foobars = newStream("foo", repeat("bars"));
        Stream<Integer> foobarsLength = asStream(monad.map(String::length, foobars));
        assertEquals("Stream(3,4,4,4,4,4,4,4,4,4...)", foobarsLength.toString());

        Stream<String> foos = asStream(monad.pure("foo"));
        assertEquals("Stream(foo,foo,foo,foo,foo,foo,foo,foo,foo,foo...)", foos.toString());

        Stream<Integer> absSqr = asStream(monad.ap(cycle(Integers.negate, Integers.sqr), range(1)));
        assertEquals("Stream(-1,4,-3,16,-5,36,-7,64,-9,100...)", absSqr.toString());

        Stream<Integer> streamOfStream = asStream(monad.bind(range(1),
            integer -> range(1, integer)));
        assertEquals("Stream(1,3,7,13,21,31,43,57,73,91...)", streamOfStream.toString());
    }

    @Test
    public void testInits()  {
        Stream<List<Integer>> stream = range(1).inits();
        assertEquals("Stream(List(),List(1),List(1,2),List(1,2,3)...)", stream.toString(4));
    }

    @Test
    public void testTails()  {
        Stream<Stream<Integer>> stream = range(1).tails();
        assertEquals("Stream(List(1,2,3),List(2,3,4),List(3,4,5),List(4,5,6)...)",
            stream.map(x -> x.take(3)).toString(4));
    }

    @Test
    public void testIntersperse()  {
        Stream<Integer> stream = range(3).intersperse(0);
        assertEquals("Stream(3,0,4,0,5,0,6,0,7,0...)", stream.toString());
    }

    @Test
    public void testInterleave()  {
        Stream<Integer> stream = interleave(range(3), range(1));
        assertEquals("Stream(3,1,4,2,5,3,6,4,7,5...)", stream.toString());
    }

}
