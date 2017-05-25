package org.highj.data;

import org.highj.data.ord.Ord;
import org.highj.function.Strings;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TreeSetTest {

    @Test
    public void empty() {
        TreeSet<String> set = TreeSet.empty();
        assertThat(set.isEmpty()).isTrue();
        assertThat(set.size()).isEqualTo(0);
        assertThat(set.test("foo")).isFalse();
    }

    @Test
    public void empty_Ord() {
        TreeSet<String> set = TreeSet.empty(Strings.ordIgnoreCase);
        assertThat(set.isEmpty()).isTrue();
        assertThat(set.size()).isEqualTo(0);
        assertThat(set.test("foo")).isFalse();
    }

    @Test
    public void singleton() {
        TreeSet<String> set = TreeSet.singleton("foo");
        assertThat(set.isEmpty()).isFalse();
        assertThat(set.test("foo")).isTrue();
        assertThat(set.test("bar")).isFalse();
        assertThat(set).containsExactly("foo");
    }

    @Test
    public void singleton_Ord() {
        TreeSet<String> set = TreeSet.singleton(Strings.ordIgnoreCase, "foo");
        assertThat(set.isEmpty()).isFalse();
        assertThat(set.test("fOO")).isTrue();
        assertThat(set.test("bar")).isFalse();
        assertThat(set).containsExactly("foo");
    }

    @Test
    public void fromIterable() {
        TreeSet<String> set = TreeSet.fromIterable(List.of("foo","bar","baz"));
        assertThat(set.isEmpty()).isFalse();
        assertThat(set.test("baz")).isTrue();
        assertThat(set.test("boing")).isFalse();
        assertThat(set).containsExactly("bar","baz","foo");
    }

    @Test
    public void fromIterable_Ord() {
        TreeSet<String> set = TreeSet.fromIterable(Ord.<String>fromComparable().reversed(), List.of("foo","bar","baz"));
        assertThat(set.isEmpty()).isFalse();
        assertThat(set.test("baz")).isTrue();
        assertThat(set.test("boing")).isFalse();
        assertThat(set).containsExactly("foo", "baz","bar");
    }

    @Test
    public void size() {
        assertThat(TreeSet.empty().size()).isEqualTo(0);
        assertThat(TreeSet.singleton("foo").size()).isEqualTo(1);
        assertThat(TreeSet.of("foo","bar","baz").size()).isEqualTo(3);
    }

    @Test
    public void isEmpty() {
        assertThat(TreeSet.empty().isEmpty()).isTrue();
        assertThat(TreeSet.singleton("foo").isEmpty()).isFalse();
        assertThat(TreeSet.of("foo","bar","baz").isEmpty()).isFalse();
    }

    @Test
    public void insert() {
        assertThat(TreeSet.<String>empty().insert("foo")).containsExactly("foo");
        assertThat(TreeSet.of("foo","bar","baz").insert("foo")).containsExactly("bar","baz","foo");
        assertThat(TreeSet.of("foo","bar","baz").insert("boo")).containsExactly("bar","baz","boo","foo");
    }

    @Test
    public void insertAll() {
        assertThat(TreeSet.of("foo","bar","baz")
                .insertAll(List.of("foo","boo","faz","boo")))
                .containsExactly("bar","baz","boo","faz","foo");
    }

    @Test
    public void delete() {
        assertThat(TreeSet.of("foo","bar","baz").delete("foo")).containsExactly("bar","baz");
        assertThat(TreeSet.of("foo","bar","baz").delete("boo")).containsExactly("bar","baz","foo");
    }

    @Test
    public void deleteMin() {
        assertThat(TreeSet.empty().deleteMin()).isEmpty();
        assertThat(TreeSet.of("foo","bar","baz").deleteMin()).containsExactly("baz","foo");
    }

    @Test
    public void deleteMax() {
        assertThat(TreeSet.empty().deleteMax()).isEmpty();
        assertThat(TreeSet.of("foo","bar","baz").deleteMax()).containsExactly("bar","baz");
    }

    @Test
    public void test() {
        assertThat(TreeSet.<String>empty().test("foo")).isFalse();
        assertThat(TreeSet.of("foo","bar","baz").test("baz")).isTrue();
        assertThat(TreeSet.of("foo","bar","baz").test("faz")).isFalse();
    }

    @Test
    public void minimum() {
        assertThat(TreeSet.singleton("foo").minimum()).isEqualTo("foo");
        assertThat(TreeSet.of("foo","bar","baz").minimum()).isEqualTo("bar");
        assertThatThrownBy(() -> TreeSet.empty().minimum()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void maximum() {
        assertThat(TreeSet.singleton("foo").maximum()).isEqualTo("foo");
        assertThat(TreeSet.of("foo","bar","baz").maximum()).isEqualTo("foo");
        assertThatThrownBy(() -> TreeSet.empty().maximum()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void toList() {
        assertThat(TreeSet.empty().toList()).isEqualTo(List.empty());
        assertThat(TreeSet.singleton("foo").toList()).isEqualTo(List.of("foo"));
        assertThat(TreeSet.of("foo","bar","baz").toList()).isEqualTo(List.of("bar","baz","foo"));
    }

    @Test
    public void toList_Function() {
        assertThat(TreeSet.<String>empty().toList(String::toUpperCase)).isEqualTo(List.empty());
        assertThat(TreeSet.singleton("foo").toList(String::toUpperCase)).isEqualTo(List.of("FOO"));
        assertThat(TreeSet.of("foo","bar","baz").toList(String::toUpperCase))
                .isEqualTo(List.of("BAR","BAZ","FOO"));
    }

    @Test
    public void iterator() {
        assertThat(TreeSet.empty().iterator()).isEmpty();
        assertThat(TreeSet.singleton("foo").iterator()).containsExactly("foo");
        assertThat(TreeSet.of("foo","bar","baz").iterator()).containsExactly("bar","baz","foo");
    }

    @Test
    public void equals() {
        assertThat(TreeSet.empty()).isEqualTo(TreeSet.empty());
        assertThat(TreeSet.singleton("foo")).isEqualTo(TreeSet.singleton("foo"));
        assertThat(TreeSet.of("foo","bar","baz")).isEqualTo(TreeSet.of("bar","foo","baz"));

        assertThat(TreeSet.singleton("foo")).isNotEqualTo(TreeSet.singleton("boo"));
        assertThat(TreeSet.of("foo","bar","baz")).isNotEqualTo(TreeSet.of("bar","foo","faz"));
    }

    @Test
    public void testHashCode() {
        assertThat(TreeSet.of("foo","bar","baz").hashCode())
                .isEqualTo(TreeSet.of("bar","foo","baz").hashCode());

        assertThat(TreeSet.of("foo","bar","baz").hashCode())
                .isNotEqualTo(TreeSet.of("bar","foo","faz").hashCode());
    }

    @Test
    public void testToString() {
        assertThat(TreeSet.empty().toString()).isEqualTo("Set()");
        assertThat(TreeSet.singleton("foo").toString()).isEqualTo("Set(foo)");
        assertThat(TreeSet.of("foo","bar","baz").toString())
                .isEqualTo("Set(bar,baz,foo)");
    }

    @Test
    public void union() {
        TreeSet<String> set1 = TreeSet.of("foo","bar","baz");
        TreeSet<String> set2 = TreeSet.of("bar","faz");

        assertThat(TreeSet.union(set1, set2)).containsExactly("bar","baz","faz","foo");
    }

    @Test
    public void intersection() {
        TreeSet<String> set1 = TreeSet.of("foo","bar","baz");
        TreeSet<String> set2 = TreeSet.of("bar","faz");

        assertThat(TreeSet.intersection(set1, TreeSet.empty())).isEmpty();
        assertThat(TreeSet.intersection(TreeSet.empty(), set2)).isEmpty();
        assertThat(TreeSet.intersection(set1, set1)).isEqualTo(set1);
        assertThat(TreeSet.intersection(set2, set2)).isEqualTo(set2);
        assertThat(TreeSet.intersection(set2, set1)).containsExactly("bar");
        assertThat(TreeSet.intersection(set1, set2)).containsExactly("bar");
    }

    @Test
    public void difference() {
        TreeSet<String> set1 = TreeSet.of("foo","bar","baz");
        TreeSet<String> set2 = TreeSet.of("bar","faz");

        assertThat(TreeSet.difference(TreeSet.empty(), set1)).isEmpty();
        assertThat(TreeSet.difference(set1, TreeSet.empty())).isEqualTo(set1);
        assertThat(TreeSet.difference(set1, set2)).containsExactly("baz","foo");
        assertThat(TreeSet.difference(set2, set1)).containsExactly("faz");
    }

}