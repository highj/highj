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
import org.highj.data.transformer.reader_arrow.ReaderArrowArrow;
import org.highj.data.transformer.reader_arrow.ReaderArrowArrowReader;
import org.highj.data.transformer.reader_arrow.ReaderArrowArrowTransformer;
import org.highj.data.transformer.reader_arrow.ReaderArrowCategory;
import org.highj.data.transformer.reader_arrow.ReaderArrowSemigroupoid;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

/**
 *
 * @author clintonselke
 */
public class ReaderArrow<R,A,B,C> implements ____<ReaderArrow.µ,R,A,B,C> {
    public static class µ {}
    
    private final __<A,T2<B,R>,C> _run;
    
    private ReaderArrow(__<A,T2<B,R>,C> run) {
        this._run = run;
    }
    
    public static <R,A,B,C> ReaderArrow<R,A,B,C> readerArrow(__<A,T2<B,R>,C> run) {
        return new ReaderArrow<>(run);
    }
    
    public static <R,A,B,C> ReaderArrow<R,A,B,C> narrow(____<ReaderArrow.µ,R,A,B,C> a) {
        return (ReaderArrow<R,A,B,C>)a;
    }
    
    public static <R,A,B,C> ReaderArrow<R,A,B,C> narrow(___<____.µ<ReaderArrow.µ,R>,A,B,C> a) {
        return (ReaderArrow<R,A,B,C>)a;
    }
    
    public static <R,A,B,C> ReaderArrow<R,A,B,C> narrow(__<___.µ<____.µ<ReaderArrow.µ,R>,A>,B,C> a) {
        return (ReaderArrow<R,A,B,C>)a;
    }
    
    public static <R,A,B,C> ReaderArrow<R,A,B,C> narrow(_<__.µ<___.µ<____.µ<ReaderArrow.µ,R>,A>,B>,C> a) {
        return (ReaderArrow<R,A,B,C>)a;
    }
    
    public __<A,T2<B,R>,C> run() {
        return _run;
    }
    
    public static <R,A> ReaderArrowSemigroupoid<R,A> semigroupoid(Arrow<A> a) {
        return () -> a;
    }
    
    public static <R,A> ReaderArrowCategory<R,A> category(Arrow<A> a) {
        return () -> a;
    }
    
    public static <R,A> ReaderArrowArrow<R,A> arrow(Arrow<A> a) {
        return () -> a;
    }
    
    public static <R,A> ReaderArrowArrowReader<R,A> arrowReader(Arrow<A> a) {
        return () -> a;
    }
    
    public static <R,A> ReaderArrowArrowTransformer<R,A> arrowTransformer(Arrow<A> a) {
        return () -> a;
    }
}
