/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import fj.F2;
import fj.data.List;
import highj._;
import highj.typeclasses.category.MonadPlus;
import highj.typeclasses.category.MonadPlusAbstract;
import highj.typeclasses.category.Plus;
import highj.typeclasses.category.PlusAbstract;

/**
 *
 * @author DGronau
 */
public class ListMonadPlus extends MonadPlusAbstract<ListOf> implements MonadPlus<ListOf>, Plus<ListOf> {
    public ListMonadPlus() {
        super(new ListPlus());
    }

    @Override
    public <A, B> _<ListOf, B> ap(_<ListOf, F<A, B>> fun, _<ListOf, A> ta) {
        List<B> tb = List.nil();
        List<F<A, B>> funs = ListOf.unwrap(fun).reverse();
        for (F<A, B> f : funs) {
            for (A a : ListOf.unwrap(ta).reverse()) {
                tb = tb.cons(f.f(a));
            }
        }
        return ListOf.wrap(tb);
    }

    @Override
    public <A, B> _<ListOf, B> bind(_<ListOf, A> nestedA, F<A, _<ListOf, B>> fn) {
        List<A> as = ListOf.unwrap(nestedA);
        List<_<ListOf, B>> nestedBs = as.map(fn);
        return ListOf.wrap(nestedBs.foldLeft(
                new F2<List<B>, _<ListOf, B>, List<B>>() {
                    @Override
                    public List<B> f(List<B> a, _<ListOf, B> b) {
                        return a.append(ListOf.unwrap(b));
                    }
                }, List.<B>nil()));
    }

    @Override
    public <A> _<ListOf, A> pure(A a) {
        return ListOf.wrap(List.single(a));
    }

    @Override
    public <A> F<A, _<ListOf, A>> pure() {
        return new F<A, _<ListOf, A>>(){

            @Override
            public _<ListOf, A> f(A a) {
                return pure(a);
            }
            
        };
    }

    private static class ListPlus extends PlusAbstract<ListOf> {
        @Override
        public <A> _<ListOf, A> empty() {
            return ListOf.<A>empty();
        }

        @Override
        public <A> _<ListOf, A> or(_<ListOf, A> first, _<ListOf, A> second) {
            return ListOf.wrap(ListOf.unwrap(first).append(ListOf.unwrap(second)));
        }

        @Override
        public <A, B> _<ListOf, B> fmap(F<A, B> fn, _<ListOf, A> nestedA) {
            List<B> tb = List.nil();
            for (A a : ListOf.unwrap(nestedA)) {
                tb = tb.cons(fn.f(a));
            }
            return ListOf.wrap(tb.reverse());
        }
    }
    
    private static ListMonadPlus INSTANCE = new ListMonadPlus();

    public static ListMonadPlus getInstance() {
        return INSTANCE;
    }
    
}
