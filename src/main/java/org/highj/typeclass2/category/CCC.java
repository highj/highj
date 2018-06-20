package org.highj.typeclass2.category;

import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.typeclass2.arrow.Category;

interface CCC<K, Tensor, Hom, Unit> extends Category<K> {

    <A,B> __2<K, __3<Tensor, K, __3<Hom, K, A, B>,  A>, B> eval();

    <A,B,C> __2<K, A, __3<Hom, K, B, C>> curry(__2<K, __3<Tensor, K, A, B>, C> v);

    <A,B,C> __2<K, __3<Tensor, K, A, B>, C> uncurry(__2<K, A, __3<Hom, K, B, C>> v);

    <A,C, D> __2<K, A, __3<Tensor, K, C, D>> fork(__2<K, A, C> first, __2<K, A, D> second);

    <A,B> __2<K, __3<Tensor, K, A, B>,  A> exl();

    <A,B> __2<K, __3<Tensor, K, A, B>,  B> exr();

}
