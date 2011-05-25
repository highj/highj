/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 *
 * @author dgronau
 */
public abstract class CopointedAbstract<Ctor> implements Copointed<Ctor> {
    @Override
    public <A> F<_<Ctor,A>, A> extract() {
       return new F<_<Ctor,A>, A>(){
            @Override
            public A f(_<Ctor, A> a) {
                return extract(a);
            }
       };
    }
}
