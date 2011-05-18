/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import highj.typeclasses.structural.Foldable;
import highj.typeclasses.structural.FoldableAbstract;

/**
 *
 * @author DGronau
 */
public class ListFoldable extends FoldableAbstract<ListOf> {
    
    private static Foldable<ListOf> INSTANCE = new ListFoldable();

    public static Foldable<ListOf> getInstance() {
        return INSTANCE;
    }

    @Override
    public <A, B> B foldr(F<A, F<B, B>> fn, B b, _<ListOf, A> nestedA) {
        return ListOf.unwrap(nestedA).foldRight(fn, b);
    }

    @Override
     public <A,B> A foldl(F<A,F<B,A>> fn, A a, _<ListOf, B> nestedB) {
        return ListOf.unwrap(nestedB).foldLeft(fn, a);
    }    
}
