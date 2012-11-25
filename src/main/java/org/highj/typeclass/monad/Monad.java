package org.highj.typeclass.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.data.tuple.T0;
import org.highj.function.F1;
import org.highj.function.F2;

public interface Monad<µ> extends Applicative<µ>, Bind<µ> {

    // mapM (Control.Monad)
    public <A, B> F1<List<A>, _<µ, List<B>>> mapM(final F1<A, _<µ, B>> fn);

    // mapM_ (Control.Monad)
    public <A, B> F1<List<A>, _<µ, T0>> mapM_(final F1<A, _<µ, B>> fn);

    //foldM (Control.Monad)
    public <A, B> F2<A, List<B>, _<µ, A>> foldM(final F1<A, F1<B, _<µ, A>>> fn);

    //foldM_ (Control.Monad)
    public <A, B> F2<A, List<B>, _<µ, T0>> foldM_(final F1<A, F1<B, _<µ, A>>> fn);

    //replicateM (Control.Monad)
    public <A> _<µ, List<A>> replicateM(int n, _<µ, A> nestedA);

    //replicateM_ (Control.Monad)
    public <A> _<µ, T0> replicateM_(int n, _<µ, A> nestedA);

    //sequence (Control.Monad)
    public <A> _<µ, List<A>> sequence(List<_<µ, A>> list);

    //sequence_ (Control.Monad)
    public <A> _<µ, T0> sequence_(List<_<µ, A>> list);
}
