/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.doblock;

import fj.F;
import highj._;
import highj.typeclasses.category.Monad;

/**
 *
 * @author DGronau
 */
public class Do1<Ctor, A, Var1> extends Do {
   protected final _<Ctor, Var1> var1;
    
   Do1(Monad<Ctor> monad, _<Ctor,A> returnValue, _<Ctor,Var1> var1) {
       super(monad, returnValue);
       this.var1 = var1;
   }
   
   public <B> Do1<Ctor, B, Var1> bind1(F<Var1, _<Ctor, B>> fn){
       _<Ctor, B> b = monad.bind(var1, fn);
       return new Do1<Ctor, B, Var1>(monad, monad.semicolon(returnValue, b), var1);
   }
   
   public <B> Do1<Ctor, B, Var1> lift1(F<Var1, B> fn){
       return bind1(monad.lift(fn));
   }
   
   public <B> Do2<Ctor,A,Var1,B> assign2(_<Ctor,B> action) {
        return new Do2<Ctor, A, Var1, B>(monad, monad.semicolon(returnValue,action), var1, action);
   }   
}
