/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.rws;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj._____;
import org.highj.data.transformer.RWST;
import org.highj.typeclass1.monad.MonadRWS;

/**
 *
 * @author clintonselke
 */
public interface RWSTMonadRWS<R,W,S,M> extends RWSTMonadReader<R,W,S,M>, RWSTMonadWriter<R,W,S,M>, RWSTMonadState<R,W,S,M>, MonadRWS<R,W,S,_<_<_<_<RWST.µ,R>,W>,S>,M>> {
}
