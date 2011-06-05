/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.doblock;

import fj.F;
import fj.F2;
import highj._;
import highj.typeclasses.category.Monad;

/**
 *
 * @author DGronau
 */
public class Do2<Ctor, A, Var1, Var2> extends Do1<Ctor, A, Var1> {
    
   protected final _<Ctor, Var2> var2;
    
   Do2(Monad<Ctor> monad, _<Ctor,A> returnValue, _<Ctor,Var1> var1, _<Ctor, Var2> var2) {
       super(monad, returnValue, var1);
       this.var2 = var2;
   }
   
   public <B> Do2<Ctor,B,B,Var2> assign1(_<Ctor,B> action) {
        return new Do2<Ctor, B, B, Var2>(monad, monad.semicolon(returnValue,action), action, var2);
   }  
   
   @Override
   public <B> Do2<Ctor, B, Var1, Var2> bind1(F<Var1, _<Ctor, B>> fn){
       _<Ctor, B> b = monad.bind(var1, fn);
       return new Do2<Ctor, B, Var1, Var2>(monad, monad.semicolon(returnValue, b), var1, var2);
   }
   
   @Override
   public <B> Do2<Ctor, B, Var1, Var2> lift1(F<Var1, B> fn){
       return bind1(monad.lift(fn));
   }

   public <B> Do2<Ctor, B, Var1, Var2> bind2(F<Var2, _<Ctor, B>> fn){
       _<Ctor, B> b = monad.bind(var2, fn);
       return new Do2<Ctor, B, Var1, Var2>(monad, monad.semicolon(returnValue, b), var1, var2);
   }
   
   public <B> Do2<Ctor, B, Var1, Var2> lift2(F<Var2, B> fn){
       return bind2(monad.lift(fn));
   }
   
   public <B> Do2<Ctor, B, Var1, Var2> bind12(F2<Var1, Var2, _<Ctor, B>> fn){
       _<Ctor,F<Var2,_<Ctor, B>>> fn1 =  monad.fmap(fn.curry(), var1);
       _<Ctor, B> b = monad.join(monad.ap(fn1, var2));
       return new Do2<Ctor, B, Var1, Var2>(monad, monad.semicolon(returnValue, b), var1, var2);
   }

   public <B> Do2<Ctor, B, Var1, Var2> lift12(F2<Var1, Var2, _<Ctor, B>> fn){
       return bind12(monad.lift2Flat(fn));
   }

   public <B> Do2<Ctor, B, Var1, Var2> bind21(F2<Var2, Var1, _<Ctor, B>> fn){
       _<Ctor,F<Var1,_<Ctor, B>>> fn1 =  monad.fmap(fn.curry(), var2);
       _<Ctor, B> b = monad.join(monad.ap(fn1, var1));
       return new Do2<Ctor, B, Var1, Var2>(monad, monad.semicolon(returnValue, b), var1, var2);
   }
   
   public <B> Do2<Ctor, B, Var1, Var2> lift21(F2<Var2, Var1, B> fn){
       return bind21(monad.lift2Flat(fn));
   }
   
}
