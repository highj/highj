package org.highj.typeclass.comonad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.collection.Stream;
import org.highj.data.tuple.T2;
import org.highj.function.F1;

public interface Comonad<µ> extends Extend<µ> {

    public <A> A extract(_<µ,A> nestedA);

    public <A> F1<_<µ,A>, A> extract();

    //(=>>)
    public <A,B> _<µ,B> unbind(_<µ, A> nestedA, F1<_<µ,A>, B> fn);

    //(.>>)
    public <A,B> _<µ,B> inject(_<µ, A> nestedA, B b);

    public <A,B> F1<_<µ, A>, B> liftCtx(F1<A, B> fn);

    public <A, B> F1<_<µ,List<A>>, List<B>> mapW(F1<_<µ, A>, B> fn);

    public <A> List<_<µ,A>> parallelW(_<µ, List<A>> nestedList);

    public <A,B> F1<_<µ,A>, Stream<B>> unfoldW(F1<_<µ, A>, T2<B,A>> fn);

    public <A,B> List<B> sequenceW(List<F1<_<µ,A>,B>> fnList, _<µ, A> nestedA);
}

