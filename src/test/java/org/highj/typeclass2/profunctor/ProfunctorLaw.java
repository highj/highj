package org.highj.typeclass2.profunctor;

import org.derive4j.hkt.__2;
import org.highj.data.eq.Eq;
import org.highj.typeclass2.arrow.Category;
import org.highj.util.Gen;
import org.highj.util.Law;

public abstract class ProfunctorLaw<P> implements Law {

    private final Profunctor<P> profunctor;
    private final Category<P> category;

    public ProfunctorLaw(Profunctor<P> profunctor, Category<P> category) {
        this.profunctor = profunctor;
        this.category = category;
    }

    public abstract <B, C> boolean areEqual(__2<P, B, C> one, __2<P, B, C> two, B b, Eq<C> eq);

    @Override
    public void test() {
        dimapIdentity();
    }

    public void dimapIdentity() {
        __2<P, String, Integer> f = profunctor.arr(category, String::length);
        __2<P, String, Integer> g = profunctor.dimap(x -> x, y -> y, f);
        for (String s : Gen.stringGen.get(20)) {
            areEqual(f, g, s, Eq.fromEquals());
        }
    }


    /*
    dimap id id ≡ id

lmap id ≡ id
rmap id ≡ id

dimap f g ≡ lmap f . rmap g

dimap (f . g) (h . i) ≡ dimap g h . dimap f i
lmap (f . g) ≡ lmap g . lmap f
rmap (f . g) ≡ rmap f . rmap g
     */
}