/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.derive4j.hkt.__4;
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
public class ReaderArrow<R,A,B,C> implements __4<ReaderArrow.µ,R,A,B,C> {
    public static class µ {}
    
    private final __2<A,T2<B,R>,C> _run;
    
    private ReaderArrow(__2<A,T2<B,R>,C> run) {
        this._run = run;
    }
    
    public static <R,A,B,C> ReaderArrow<R,A,B,C> readerArrow(__2<A,T2<B,R>,C> run) {
        return new ReaderArrow<>(run);
    }

    public __2<A,T2<B,R>,C> run() {
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
