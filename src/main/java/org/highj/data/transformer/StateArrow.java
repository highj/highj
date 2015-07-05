/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.transformer.state_arrow.StateArrowArrow;
import org.highj.data.transformer.state_arrow.StateArrowArrowState;
import org.highj.data.transformer.state_arrow.StateArrowArrowTransformer;
import org.highj.data.transformer.state_arrow.StateArrowCategory;
import org.highj.data.transformer.state_arrow.StateArrowSemigroupoid;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.Category;
import org.highj.typeclass2.arrow.Semigroupoid;

/**
 *
 * @author clintonselke
 */
public class StateArrow<S,A,B,C> implements ____<StateArrow.µ,S,A,B,C> {
    public static class µ {}
    
    private final __<A,T2<B,S>,T2<C,S>> _run;
    
    private StateArrow(__<A,T2<B,S>,T2<C,S>> run) {
        this._run = run;
    }
    
    public static <S,A,B,C> StateArrow<S,A,B,C> stateArrow(__<A,T2<B,S>,T2<C,S>> run) {
        return new StateArrow<>(run);
    }
    
    public static <S,A,B,C> StateArrow<S,A,B,C> narrow(____<StateArrow.µ,S,A,B,C> a) {
        return (StateArrow<S,A,B,C>)a;
    }
    
    public static <S,A,B,C> StateArrow<S,A,B,C> narrow(___<____.µ<StateArrow.µ,S>,A,B,C> a) {
        return (StateArrow<S,A,B,C>)a;
    }
    
    public static <S,A,B,C> StateArrow<S,A,B,C> narrow(__<___.µ<____.µ<StateArrow.µ,S>,A>,B,C> a) {
        return (StateArrow<S,A,B,C>)a;
    }
    
    public static <S,A,B,C> StateArrow<S,A,B,C> narrow(_<__.µ<___.µ<____.µ<StateArrow.µ,S>,A>,B>,C> a) {
        return (StateArrow<S,A,B,C>)a;
    }
    
    public __<A,T2<B,S>,T2<C,S>> run() {
        return _run;
    }
    
    public static <S,A> StateArrowSemigroupoid<S,A> semigroupoid(Semigroupoid<A> a) {
        return () -> a;
    }
    
    public static <S,A> StateArrowCategory<S,A> category(Category<A> a) {
        return () -> a;
    }
    
    public static <S,A> StateArrowArrow<S,A> arrow(Arrow<A> a) {
        return () -> a;
    }
    
    public static <S,A> StateArrowArrowState<S,A> arrowState(Arrow<A> a) {
        return () -> a;
    }
    
    public static <S,A> StateArrowArrowTransformer<S,A> arrowTransformer(Arrow<A> a) {
        return () -> a;
    }
}
