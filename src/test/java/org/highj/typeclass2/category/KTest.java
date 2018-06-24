package org.highj.typeclass2.category;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.Hkt;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KTest {


    private static <Unit,A> String debug(__2<K,__<Unit,K>,A> k) {
        return Hkt.asK(k).optimize().prettyPrint();
    }

    private static <K,Tensor,Hom,Unit,I,A,B> __2<K,I,A> fst(CCC<K,Tensor,Hom,Unit> ccc, __2<K,I,__3<Tensor,K,A,B>> a) {
        return Lam2CCC.create(ccc).liftCCC(ccc.exl(), a);
    }

    @Test
    public void test1() {
        String result = debug(Lam2CCC.create(K.ccc()).lam1(x -> x));
        assertThat(result).isEqualTo("curry snd");
    }

    @Test
    public void test7() {
        String result = debug(Lam2CCC.create(K.ccc()).lam2Curried(x -> _unused -> x));
        assertThat(result).isEqualTo("curry (curry (snd . fst))");
    }
}
