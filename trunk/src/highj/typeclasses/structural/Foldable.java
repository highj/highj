/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.structural;

import fj.F;
import fj.F2;
import fj.Monoid;
import highj._;

/**
 *
 * @author DGronau
 */
public interface Foldable<Ctor> {
    
   public <A> A fold(Monoid<A> ma, _<Ctor, A> nestedA);

   public <A,B> B foldMap(Monoid<B> mb, F<A,B> fn, _<Ctor, A> nestedA);

   public <A,B> B foldr(F<A,F<B,B>> fn, B b, _<Ctor, A> nestedA);

   public <A,B> A foldl(F<A,F<B,A>> fn, A a, _<Ctor, B> nestedB);

   public <A> A  foldr1(F<A,F<A,A>> fn,  _<Ctor, A> nestedA);

   public <A> A  foldl1(F<A,F<A,A>> fn, _<Ctor, A> nestedA);

   public <A,B> B foldr(F2<A,B,B> fn, B b, _<Ctor, A> nestedA);

   public <A,B> A foldl(F2<A,B,A> fn, A a, _<Ctor, B> nestedB);

   public <A> A  foldr1(F2<A,A,A> fn, _<Ctor, A> nestedA);

   public <A> A  foldl1(F2<A,A,A> fn, _<Ctor, A> nestedA);

}
