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
        OptionOf optionOf = OptionOf.getInstance();
        return optionOf.isSome(ta) ? f.f(optionOf.get(ta)) : optionOf.<B>none();
    }

    @Override
    public <A, B> _<OptionOf, B> fmap(F<A, B> f, _<OptionOf, A> ta) {
        OptionOf optionOf = OptionOf.getInstance();
        return optionOf.isSome(ta)
                ? optionOf.some(f.f(optionOf.get(ta)))
                : optionOf.<B>none();
    }

    @Override
    public <T> _<OptionOf, T> pure(T t) {
        return OptionOf.getInstance().some(t);
    }

    @Override
    public <A, B> _<OptionOf, B> star(_<OptionOf, F<A, B>> fun, _<OptionOf, A> ta) {
        OptionOf optionOf = OptionOf.getInstance();
        return optionOf.isSome(fun) && optionOf.isSome(ta)
                ? optionOf.some(optionOf.get(fun).f(optionOf.get(ta)))
                : optionOf.<B>none();
    }

    @Override
    public <A> _<OptionOf, A> empty() {
        return OptionOf.getInstance().<A>none();
    }

    @Override
    public <A> _<OptionOf, A> or(_<OptionOf, A> first, _<OptionOf, A> second) {
        return OptionOf.getInstance().isSome(first) ? first : second;
    }

    private static OptionMonadPlus INSTANCE = new OptionMonadPlus();

    public static OptionMonadPlus getInstance() {
        return INSTANCE;
    }

}
