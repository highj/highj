package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.function.F2;
import org.highj.typeclass.monad.Applicative;
import org.highj.typeclass.monad.Functor;

public interface Alt<µ> extends Functor<µ> {

    //<|> (Control.Applicative), <!> (Data.Functor.Alt)
    public <A> _<µ, A> mplus(_<µ, A> first, _<µ, A> second);

    public <A> F2<_<µ, A>,_<µ, A>,_<µ, A>> mplus();

    //public <F,A> _<F,List<A>> some(Applicative<F> applicative, _<F,A> fa);
    //public <F,A> _<F,List<A>> many(Applicative<F> applicative, _<F,A> fa);

}
