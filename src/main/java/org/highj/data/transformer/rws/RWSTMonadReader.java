/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import java.util.function.Function;
import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj._____;
import org.highj.data.transformer.RWST;
import org.highj.data.tuple.T3;
import org.highj.typeclass1.monad.MonadReader;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadReader<R,W,S,M> extends RWSTMonad<R,W,S,M>, MonadReader<R,_<_<_<_<RWST.µ,R>,W>,S>,M>> {

    @Override
    public default RWST<R, W, S, M, R> ask() {
        return (R r, S s) -> m().pure(T3.of(r, s, w().identity()));
    }

    @Override
    public default <A> RWST<R, W, S, M, A> local(Function<R, R> modFn, _<_<_<_<_<RWST.µ, R>, W>, S>, M>, A> nestedA) {
        return (R r, S s) -> RWST.narrow(nestedA).run(modFn.apply(r), s);
    }
}
