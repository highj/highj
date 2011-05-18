/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import highj.typeclasses.category.MonadPlus;
import highj.typeclasses.category.MonadPlusAbstract;

/**
 *
 * @author DGronau
 */
public class OptionMonadPlus extends MonadPlusAbstract<OptionOf> implements MonadPlus<OptionOf> {

    @Override
    public <A, B> _<OptionOf, B> bind(_<OptionOf, A> ta, F<A, _<OptionOf, B>> f) {
        return OptionOf.isSome(ta) ? f.f(OptionOf.get(ta)) : OptionOf.<B>none();
    }

    @Override
    public <A, B> _<OptionOf, B> fmap(F<A, B> f, _<OptionOf, A> ta) {
        return OptionOf.isSome(ta)
                ? OptionOf.some(f.f(OptionOf.get(ta)))
                : OptionOf.<B>none();
    }

    @Override
    public <T> _<OptionOf, T> pure(T t) {
        return OptionOf.some(t);
    }

    @Override
    public <A, B> _<OptionOf, B> star(_<OptionOf, F<A, B>> fun, _<OptionOf, A> ta) {
        return OptionOf.isSome(fun) && OptionOf.isSome(ta)
                ? OptionOf.some(OptionOf.get(fun).f(OptionOf.get(ta)))
                : OptionOf.<B>none();
    }

    @Override
    public <A> _<OptionOf, A> empty() {
        return OptionOf.<A>none();
    }

    @Override
    public <A> _<OptionOf, A> or(_<OptionOf, A> first, _<OptionOf, A> second) {
        return OptionOf.isSome(first) ? first : second;
    }

    private static OptionMonadPlus INSTANCE = new OptionMonadPlus();

    public static OptionMonadPlus getInstance() {
        return INSTANCE;
    }

}
