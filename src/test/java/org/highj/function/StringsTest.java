package org.highj.function;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.highj.data.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
class StringsTest {

    @Property
    void reverse(String s) {
        assertThat(Strings.reverse(Strings.reverse(s))).isEqualTo(s);
    }

    @Test
    void reverse() {
        assertThat(Strings.reverse("live")).isEqualTo("evil");
    }

    @Test
    void format() {
        assertThat(Strings.format("answer=%d").apply(42)).isEqualTo("answer=42");
    }

    @Property
    void concat(String a, String b) {
        assertThat(Strings.concat(a,b)).isEqualTo(a + b);
    }

    @Test
    void mkStringVarargs() {
        assertThat(Strings.mkString(",")).isEqualTo("");
        assertThat(Strings.mkString(",", "a")).isEqualTo("a");
        assertThat(Strings.mkString(",", "a", "b", "c")).isEqualTo("a,b,c");
        assertThat(Strings.mkString("", "a", "b", "c")).isEqualTo("abc");
    }


    @Test
    void mkEnclosedVarargs() {
        assertThat(Strings.mkEnclosed("(",",",")")).isEqualTo("()");
        assertThat(Strings.mkEnclosed("(",",",")", "a")).isEqualTo("(a)");
        assertThat(Strings.mkEnclosed("(",",",")", "a", "b", "c")).isEqualTo("(a,b,c)");
        assertThat(Strings.mkEnclosed("(","",")", "a", "b", "c")).isEqualTo("(abc)");
    }

    @Test
    void mkStringIterable() {
        assertThat(Strings.mkString(",", List.empty())).isEqualTo("");
        assertThat(Strings.mkString(",", List.of("a"))).isEqualTo("a");
        assertThat(Strings.mkString(",", List.of("a", "b", "c"))).isEqualTo("a,b,c");
        assertThat(Strings.mkString("", List.of("a", "b", "c"))).isEqualTo("abc");
    }

    @Test
    void mkEnclosedIterable() {
        assertThat(Strings.mkEnclosed("(",",",")", List.empty())).isEqualTo("()");
        assertThat(Strings.mkEnclosed("(",",",")", List.of("a"))).isEqualTo("(a)");
        assertThat(Strings.mkEnclosed("(",",",")", List.of("a", "b", "c"))).isEqualTo("(a,b,c)");
        assertThat(Strings.mkEnclosed("(","",")", List.of("a", "b", "c"))).isEqualTo("(abc)");
    }

    @Test
    void iterable() {
        assertThat(Strings.iterable("")).isEmpty();
        assertThat(Strings.iterable("abc")).containsExactly('a','b','c');
    }

}