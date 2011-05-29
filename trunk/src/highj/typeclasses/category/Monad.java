/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.Unit;
import fj.data.List;
import highj._;
import highj.data.ListOf;

/**
 *
 * @author DGronau
 */
public interface Monad<Ctor> extends Applicative<Ctor>, Bind<Ctor> {

  
    // mapM (Control.Monad)
    public <A, B> F<_<ListOf, A>,_<Ctor, _<ListOf, B>>> mapM(F<A, _<Ctor, B>> fn);
    // "flat" version of mapM
    public <A, B> F<List<A>,_<Ctor, List<B>>> mapMFlat(F<A, _<Ctor, B>> fn);
    // mapM_ (Control.Monad)
    public <A, B> F<_<ListOf, A>,_<Ctor, Unit>> mapM_(F<A, _<Ctor, B>> fn);
    // "flat" version of mapM_
    public <A, B> F<List<A>,_<Ctor, Unit>> mapM_Flat(F<A, _<Ctor, B>> fn);
        
    //foldM (Control.Monad) 
    public <A, B> F2<A,_<ListOf, B>,_<Ctor, A>> foldM(F<A,F<B,_<Ctor,A>>> fn);
    //"flat" version of foldM 
    public <A, B> F2<A,List<B>,_<Ctor, A>> foldMFlat(F2<A,B,_<Ctor,A>> fn);
    //foldM_ (Control.Monad)
    public <A, B> F2<A, _<ListOf, B>,_<Ctor, Unit>> foldM_(F<A,F<B,_<Ctor,A>>> fn); 
    //"flat" version of foldM_ 
    public <A, B> F2<A,List<B> ,_<Ctor, Unit>> foldM_Flat(F2<A, B,_<Ctor,A>> fn);

    //replicateM (Control.Monad)
    public <A> _<Ctor,_<ListOf,A>> replicateM(int n, _<Ctor, A> nestedA);
    //"flat" version of replicateM 
    public <A> _<Ctor,List<A>> replicateMFlat(int n, _<Ctor, A> nestedA);
    //replicateM_ (Control.Monad)
    public <A> _<Ctor, Unit> replicateM_(int n, _<Ctor, A> nestedA);
    
    // sequence (Control.Monad)
    public <A> _<Ctor, _<ListOf, A>> sequence(_<ListOf, _<Ctor, A>> list);
    //"flat" version of sequence
    public <A> _<Ctor, List<A>> sequenceFlat(List<_<Ctor, A>> list);
    // sequence_ (Control.Monad)
    public <A> _<Ctor, Unit> sequence_(_<ListOf, _<Ctor, A>> list);
    //"flat" version of sequence_
    public <A> _<Ctor, Unit> sequence_Flat(List<_<Ctor, A>> list);
    
}
