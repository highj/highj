package org.highj.data.instance.list;

import org.highj.data.List;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.functor.Functor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asZipper;

public class ZipperTest {
    @Test
    public void testEmpty() {
        assertThat(Zipper.empty().position()).isEqualTo(0);
        assertThat(Zipper.empty().toList()).isEmpty();
    }

    @Test
    public void testFromList() {
        assertThat(Zipper.fromList(List.<String>empty()).isEmpty()).isTrue();

        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).position()).isEqualTo(0);
        assertThat(Zipper.fromList(list).toList()).isEqualTo(list);

        List<String> infinitList = List.cycle("foo");
        assertThat(Zipper.fromList(infinitList).position()).isEqualTo(0);
        assertThat(Zipper.fromList(infinitList).toList()).startsWith("foo", "foo", "foo");
    }

    @Test
    public void testFromListEnd() {
        assertThat(Zipper.fromList(List.<String>empty()).isEmpty()).isTrue();

        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromListEnd(list).position()).isEqualTo(3);
        assertThat(Zipper.fromListEnd(list).toList()).isEqualTo(list);
    }

    @Test
    public void testToList() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromListEnd(list).toEnd().toStart().toPosition(2).toList()).isEqualTo(list);
    }

    @Test
    public void testIsEmpty() {
        assertThat(Zipper.empty().isEmpty()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).isEmpty()).isFalse();
        assertThat(Zipper.fromList(list).toPosition(1).removeNext().removeBefore().removeNext().isEmpty()).isTrue();
    }

    @Test
    public void testIsStart() {
        assertThat(Zipper.empty().isStart()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).isStart()).isTrue();
        assertThat(Zipper.fromListEnd(list).isStart()).isFalse();
        assertThat(Zipper.fromListEnd(list).backwards().backwards().backwards().isStart()).isTrue();
    }

    @Test
    public void testIsEnd() {
        assertThat(Zipper.empty().isEnd()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromListEnd(list).isEnd()).isTrue();
        assertThat(Zipper.fromList(list).isEnd()).isFalse();
        assertThat(Zipper.fromList(list).forwards().forwards().forwards().isEnd()).isTrue();
    }

    @Test
    public void testPosition() {
        assertThat(Zipper.empty().position()).isEqualTo(0);
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).position()).isEqualTo(0);
        assertThat(Zipper.fromList(list).forwards().forwards().position()).isEqualTo(2);
        assertThat(Zipper.fromListEnd(list).position()).isEqualTo(3);
        assertThat(Zipper.fromList(List.cycle("foo")).position()).isEqualTo(0);
    }

    @Test
    public void testToStart() {
        assertThat(Zipper.empty().toStart().position()).isEqualTo(0);
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).toStart().position()).isEqualTo(0);
        assertThat(Zipper.fromList(list).forwards().toStart().position()).isEqualTo(0);
        assertThat(Zipper.fromListEnd(list).toStart().position()).isEqualTo(0);
        assertThat(Zipper.fromListEnd(list).toStart().toList()).isEqualTo(list);
    }

    @Test
    public void testToEnd() {
        assertThat(Zipper.empty().toEnd().position()).isEqualTo(0);
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromListEnd(list).toEnd().position()).isEqualTo(3);
        assertThat(Zipper.fromListEnd(list).backwards().toEnd().position()).isEqualTo(3);
        assertThat(Zipper.fromList(list).toEnd().position()).isEqualTo(3);
        assertThat(Zipper.fromList(list).toEnd().toList()).isEqualTo(list);
    }

    @Test
    public void testToPosition() {
        assertThat(Zipper.empty().toPosition(0).position()).isEqualTo(0);
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).toPosition(3).isEnd()).isTrue();
        assertThat(Zipper.fromListEnd(list).toPosition(0).isStart()).isTrue();
        T2<List<String>, List<String>> splitted = Zipper.fromList(list).forwards().toPosition(2).split();
        assertThat(splitted._1().size()).isEqualTo(2);
        assertThat(splitted._2().size()).isEqualTo(1);
    }

    @Test
    public void testMaybeToPosition() {
        assertThat(Zipper.empty().maybeToPosition(0).get().position()).isEqualTo(0);
        assertThat(Zipper.empty().maybeToPosition(-1).isNothing()).isTrue();
        assertThat(Zipper.empty().maybeToPosition(1).isNothing()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        for (int i = 0; i <= 3; i++) {
            assertThat(Zipper.fromList(list).maybeToPosition(i).get().position()).isEqualTo(i);
            assertThat(Zipper.fromListEnd(list).maybeToPosition(i).get().position()).isEqualTo(i);
        }
        assertThat(Zipper.fromList(list).maybeToPosition(-1).isNothing()).isTrue();
        assertThat(Zipper.fromList(list).maybeToPosition(4).isNothing()).isTrue();
    }

    @Test
    public void testForwards() {
        assertThat(Zipper.empty().forwards(0).position()).isEqualTo(0);
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).forwards().position()).isEqualTo(1);
        assertThat(Zipper.fromList(list).forwards().forwards().position()).isEqualTo(2);
        assertThat(Zipper.fromList(list).forwards(3).isEnd()).isTrue();
        assertThat(Zipper.fromListEnd(list).forwards(-3).isStart()).isTrue();
    }

    @Test
    public void testBackwards() {
        assertThat(Zipper.empty().backwards(0).position()).isEqualTo(0);
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromListEnd(list).backwards().position()).isEqualTo(2);
        assertThat(Zipper.fromListEnd(list).backwards().backwards().position()).isEqualTo(1);
        assertThat(Zipper.fromListEnd(list).backwards(3).isStart()).isTrue();
        assertThat(Zipper.fromList(list).backwards(-3).isEnd()).isTrue();

    }

    @Test
    public void testMaybeForwards() {
        assertThat(Zipper.empty().maybeForwards(0).get().position()).isEqualTo(0);
        assertThat(Zipper.empty().maybeForwards().isNothing()).isTrue();
        assertThat(Zipper.empty().maybeForwards(1).isNothing()).isTrue();
        assertThat(Zipper.empty().maybeForwards(-1).isNothing()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).maybeForwards().get().position()).isEqualTo(1);
        assertThat(Zipper.fromList(list).maybeForwards().bind(Zipper::maybeForwards).get().position()).isEqualTo(2);
        assertThat(Zipper.fromList(list).maybeForwards(3).get().isEnd()).isTrue();
        assertThat(Zipper.fromListEnd(list).maybeForwards(-3).get().isStart()).isTrue();

        assertThat(Zipper.fromList(list).maybeForwards(-1).isNothing()).isTrue();
        assertThat(Zipper.fromList(list).maybeForwards(4).isNothing()).isTrue();
        assertThat(Zipper.fromListEnd(list).maybeForwards().isNothing()).isTrue();
        assertThat(Zipper.fromListEnd(list).maybeForwards(-4).isNothing()).isTrue();
    }

    @Test
    public void testMaybeBackwards() {
        assertThat(Zipper.empty().maybeBackwards(0).get().position()).isEqualTo(0);
        assertThat(Zipper.empty().maybeBackwards().isNothing()).isTrue();
        assertThat(Zipper.empty().maybeBackwards(1).isNothing()).isTrue();
        assertThat(Zipper.empty().maybeBackwards(-1).isNothing()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromListEnd(list).maybeBackwards().get().position()).isEqualTo(2);
        assertThat(Zipper.fromListEnd(list).maybeBackwards().bind(Zipper::maybeBackwards).get().position()).isEqualTo(1);
        assertThat(Zipper.fromListEnd(list).maybeBackwards(3).get().isStart()).isTrue();
        assertThat(Zipper.fromList(list).maybeBackwards(-3).get().isEnd()).isTrue();

        assertThat(Zipper.fromListEnd(list).maybeBackwards(-1).isNothing()).isTrue();
        assertThat(Zipper.fromListEnd(list).maybeBackwards(4).isNothing()).isTrue();
        assertThat(Zipper.fromList(list).maybeBackwards().isNothing()).isTrue();
        assertThat(Zipper.fromList(list).maybeBackwards(-4).isNothing()).isTrue();
    }

    @Test
    public void testReadNext() {
        List<String> list = List.of("foo", "bar", "baz");
        for (int i = 0; i < 3; i++) {
            assertThat(Zipper.fromList(list).toPosition(i).readNext()).isEqualTo(list.get(i));
        }
    }

    @Test
    public void testReadBefore() {
        List<String> list = List.of("foo", "bar", "baz");
        for (int i = 0; i < 3; i++) {
            assertThat(Zipper.fromList(list).toPosition(i + 1).readBefore()).isEqualTo(list.get(i));
        }
    }

    @Test
    public void testMaybeReadNext() {
        List<String> list = List.of("foo", "bar", "baz");
        for (int i = 0; i <= 3; i++) {
            assertThat(Zipper.fromList(list).toPosition(i).maybeReadNext()).isEqualTo(list.apply(i));
        }
    }

    @Test
    public void testMaybeReadBefore() {
        List<String> list = List.of("foo", "bar", "baz");
        for (int i = 1; i < 3; i++) {
            assertThat(Zipper.fromList(list).toPosition(i + 1).maybeReadBefore()).isEqualTo(list.apply(i));
        }
    }

    @Test
    public void testRemoveNext() {
        List<String> list = List.of("foo", "bar", "baz", "ding", "dong");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .removeNext()
                         .removeNext()
                         .toList()).containsExactly("foo", "ding", "dong");
    }

    @Test
    public void testRemoveBefore() {
        List<String> list = List.of("foo", "bar", "baz", "ding", "dong");
        assertThat(Zipper.fromListEnd(list)
                         .backwards()
                         .removeBefore()
                         .removeBefore()
                         .toList()).containsExactly("foo", "bar", "dong");
    }

    @Test
    public void testMaybeRemoveNext() {
        List<String> list = List.of("foo", "bar", "baz", "ding", "dong");
        assertThat(Zipper.empty().maybeRemoveNext().isNothing()).isTrue();
        assertThat(Zipper.fromListEnd(list).maybeRemoveNext().isNothing()).isTrue();
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .maybeRemoveNext().get()
                         .maybeRemoveNext().get()
                         .toList()).containsExactly("foo", "ding", "dong");
    }

    @Test
    public void testMaybeRemoveBefore() {
        List<String> list = List.of("foo", "bar", "baz", "ding", "dong");
        assertThat(Zipper.empty().maybeRemoveBefore().isNothing()).isTrue();
        assertThat(Zipper.fromList(list).maybeRemoveBefore().isNothing()).isTrue();
        assertThat(Zipper.fromListEnd(list)
                         .backwards()
                         .maybeRemoveBefore().get()
                         .maybeRemoveBefore().get()
                         .toList()).containsExactly("foo", "bar", "dong");
    }

    @Test
    public void testInsertNext() {
        assertThat(Zipper.<String>empty().insertNext("foo").toList()).containsExactly("foo");

        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .insertNext("foo1")
                         .forwards(2)
                         .insertNext("bar1", "bar2")
                         .forwards(3)
                         .insertNext("baz1")
                         .toList()).containsExactly("foo", "foo1", "bar", "bar1", "bar2", "baz", "baz1");
    }

    @Test
    public void testInsertBefore() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .insertBefore("foo1")
                         .forwards()
                         .insertBefore("bar1", "bar2")
                         .forwards()
                         .insertBefore("baz1")
                         .toList()).containsExactly("foo", "foo1", "bar", "bar2", "bar1", "baz", "baz1");
    }

    @Test
    public void testReplaceNext() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .replaceNext("bar1")
                         .forwards()
                         .replaceNext("baz1")
                         .toList()).containsExactly("foo", "bar1", "baz1");
    }

    @Test
    public void testReplaceBefore() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .replaceBefore("foo1")
                         .forwards()
                         .replaceBefore("bar1")
                         .toList()).containsExactly("foo1", "bar1", "baz");
    }

    @Test
    public void testMaybeReplaceNext() {
        assertThat(Zipper.<String>empty().maybeReplaceNext("foo").isNothing()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .maybeReplaceNext("bar1").get()
                         .forwards()
                         .maybeReplaceNext("baz1").get()
                         .toList()).containsExactly("foo", "bar1", "baz1");
    }

    @Test
    public void testMaybeReplaceBefore() {
        assertThat(Zipper.<String>empty().maybeReplaceBefore("foo").isNothing()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .maybeReplaceBefore("foo1").get()
                         .forwards()
                         .maybeReplaceBefore("bar1").get()
                         .toList()).containsExactly("foo1", "bar1", "baz");
    }

    @Test
    public void testModifyNext() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .modifyNext(s -> s + "1")
                         .forwards()
                         .modifyNext(s -> s + "1")
                         .toList()).containsExactly("foo", "bar1", "baz1");
    }

    @Test
    public void testModifyBefore() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .modifyBefore(s -> s + "1")
                         .forwards()
                         .modifyBefore(s -> s + "1")
                         .toList()).containsExactly("foo1", "bar1", "baz");
    }

    @Test
    public void testMaybeModifyNext() {
        assertThat(Zipper.<String>empty().maybeModifyNext(s -> s + "1").isNothing()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .maybeModifyNext(s -> s + "1").get()
                         .forwards()
                         .maybeModifyNext(s -> s + "1").get()
                         .toList()).containsExactly("foo", "bar1", "baz1");
    }

    @Test
    public void testMaybeModifyBefore() {
        assertThat(Zipper.<String>empty().maybeModifyBefore(s -> s + "1").isNothing()).isTrue();
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list)
                         .forwards()
                         .maybeModifyBefore(s -> s + "1").get()
                         .forwards()
                         .maybeModifyBefore(s -> s + "1").get()
                         .toList()).containsExactly("foo1", "bar1", "baz");
    }


    @Test
    public void testDropAfter() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.<String>empty().dropAfter().toList()).isEmpty();
        assertThat(Zipper.fromList(list).dropAfter().toList()).isEmpty();
        assertThat(Zipper.fromList(list).forwards().dropAfter().toList()).containsExactly("foo");
        assertThat(Zipper.fromListEnd(list).dropAfter().toList()).containsExactly("foo", "bar", "baz");
    }

    @Test
    public void testDropBefore() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.<String>empty().dropBefore().toList()).isEmpty();
        assertThat(Zipper.fromListEnd(list).dropBefore().toList()).isEmpty();
        assertThat(Zipper.fromListEnd(list).backwards().dropBefore().toList()).containsExactly("baz");
        assertThat(Zipper.fromList(list).dropBefore().toList()).containsExactly("foo", "bar", "baz");
    }

    @Test
    public void testReverse() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.<String>empty().reverse().toList()).isEmpty();
        assertThat(Zipper.fromList(list).reverse().toList()).containsExactly("baz", "bar", "foo");
        assertThat(Zipper.fromList(list).reverse().position()).isEqualTo(3);
        assertThat(Zipper.fromList(list).forwards().reverse().toList()).containsExactly("baz", "bar", "foo");
        assertThat(Zipper.fromList(list).forwards().reverse().position()).isEqualTo(2);
    }

    @Test
    public void testMap() {
        List<String> list = List.of("one", "two", "three", "four");
        assertThat(Zipper.<String>empty().map(String::length).toList()).isEmpty();
        assertThat(Zipper.fromList(list).map(String::length).toList()).containsExactly(3, 3, 5, 4);
        assertThat(Zipper.fromList(list).forwards().map(String::length).position()).isEqualTo(1);
    }

    @Test
    public void testFilter() {
        List<String> list = List.of("one", "two", "three", "four", "five", "six");
        assertThat(Zipper.<String>empty().filter(s -> s.length() == 3).toList()).isEmpty();
        assertThat(Zipper.fromList(list).filter(s -> s.length() == 3).toList()).containsExactly("one", "two", "six");
        assertThat(Zipper.fromListEnd(list).filter(s -> s.length() == 3).toList()).containsExactly("one", "two", "six");
        assertThat(Zipper.fromList(list).forwards(3).filter(s -> s.length() == 3).position()).isEqualTo(2);
    }

    @Test
    public void testFilterBefore() {
        List<String> list = List.of("one", "two", "three", "four", "five", "six");
        assertThat(Zipper.<String>empty().filterBefore(s -> s.length() == 3).toList()).isEmpty();
        assertThat(Zipper.fromList(list).filterBefore(s -> s.length() == 3).toList()).containsExactly("one", "two", "three", "four", "five", "six");
        assertThat(Zipper.fromList(list).forwards(4).filterBefore(s -> s.length() == 3).toList()).containsExactly("one", "two", "five", "six");
        assertThat(Zipper.fromList(list).forwards(4).filterBefore(s -> s.length() == 3).position()).isEqualTo(2);
        assertThat(Zipper.fromListEnd(list).filterBefore(s -> s.length() == 3).toList()).containsExactly("one", "two", "six");
    }

    @Test
    public void testFilterAfter() {
        List<String> list = List.of("one", "two", "three", "four", "five", "six");
        assertThat(Zipper.<String>empty().filterAfter(s -> s.length() == 3).toList()).isEmpty();
        assertThat(Zipper.fromListEnd(list).filterAfter(s -> s.length() == 3).toList()).containsExactly("one", "two", "three", "four", "five", "six");
        assertThat(Zipper.fromList(list).forwards(3).filterAfter(s -> s.length() == 3).toList()).containsExactly("one", "two", "three", "six");
        assertThat(Zipper.fromList(list).forwards(3).filterAfter(s -> s.length() == 3).position()).isEqualTo(3);
        assertThat(Zipper.fromList(list).filterAfter(s -> s.length() == 3).toList()).containsExactly("one", "two", "six");
    }

    @Test
    public void testSplit() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.fromList(list).split()).isEqualTo(T2.of(List.<String>empty(), list));
        assertThat(Zipper.fromListEnd(list).split()).isEqualTo(T2.of(list.reverse(), List.<String>empty()));
        assertThat(Zipper.fromList(list).forwards(2).split()).isEqualTo(
            T2.of(List.of("bar", "foo"), List.of("baz")));
    }

    @Test
    public void testSize() {
        List<String> list = List.of("foo", "bar", "baz");
        assertThat(Zipper.empty().size()).isEqualTo(0);
        assertThat(Zipper.fromList(list).size()).isEqualTo(3);
        assertThat(Zipper.fromList(list).forwards(2).size()).isEqualTo(3);
        assertThat(Zipper.fromListEnd(list).size()).isEqualTo(3);
    }

    @Test
    public void testFunctor() {
        List<String> list = List.of("one", "two", "three", "four");
        Functor<Zipper.µ> functor = Zipper.functor;
        assertThat(asZipper(functor.map(String::length, Zipper.empty())).toList()).isEmpty();
        assertThat(asZipper(functor.map(String::length, Zipper.fromList(list))).toList()).containsExactly(3, 3, 5, 4);
        assertThat(asZipper(functor.map(String::length, Zipper.fromList(list).forwards())).position()).isEqualTo(1);
    }
}