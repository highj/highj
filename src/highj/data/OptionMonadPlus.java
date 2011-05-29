/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import highj.typeclasses.category.MonadPlus;
import highj.typeclasses.category.MonadPlusAbstract;
import highj.typeclasses.category.PlusAbstract;

/**
 *
 * @author DGronau
 */
public class OptionMonadPlus extends MonadPlusAbstract<OptionOf> implements MonadPlus<OptionOf> {

    public OptionMonadPlus() {
        super(new OptionPlus());
    }
    
    @Override
    public <A, B> _<OptionOf, B> bind(_<OptionOf, A> ta, F<A, _<OptionOf, B>> f) {
        return OptionOf.isSome(ta) ? f.f(OptionOf.get(ta)) : OptionOf.<B>none();
    }

    @Override
    public <A, B> _<OptionOf, B> ap(_<OptionOf, F<A, B>> fun, _<OptionOf, A> ta) {
        return OptionOf.isSome(fun) && OptionOf.isSome(ta)
                ? OptionOf.some(OptionOf.get(fun).f(OptionOf.get(ta)))
                : OptionOf.<B>none();
    }

    @Override
    public <A, B> _<OptionOf, B> fmap(F<A, B> fn, _<OptionOf, A> nestedA) {
        return OptionOf.isSome(nestedA)
                ? OptionOf.some(fn.f(OptionOf.get(nestedA)))
                : OptionOf.<B>none();
    }

    @Override
    public <A> _<OptionOf, A> pure(A a) {
        return OptionOf.some(a);
    }
    
    private static OptionMonadPlus INSTANCE = new OptionMonadPlus();

    public static OptionMonadPlus getInstance() {
        return INSTANCE;
    }
    
    private static class OptionPlus extends PlusAbstract<OptionOf> {

        @Override
        public <A> _<OptionOf, A> empty() {
            return OptionOf.<A>none();
        }

        @Override
        public <A> _<OptionOf, A> or(_<OptionOf, A> first, _<OptionOf, A> second) {
            return OptionOf.isSome(first) ? first : second;
        }

        @Override
        public <A, B> _<OptionOf, B> fmap(F<A, B> fn, _<OptionOf, A> nestedA) {
          return OptionOf.isSome(nestedA)
                ? OptionOf.some(fn.f(OptionOf.get(nestedA)))
                : OptionOf.<B>none();
        }
        
    }

}
