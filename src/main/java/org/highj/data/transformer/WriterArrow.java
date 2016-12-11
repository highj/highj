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
import org.highj.data.transformer.writer_arrow.WriterArrowArrow;
import org.highj.data.transformer.writer_arrow.WriterArrowArrowTransformer;
import org.highj.data.transformer.writer_arrow.WriterArrowArrowWriter;
import org.highj.data.transformer.writer_arrow.WriterArrowCategory;
import org.highj.data.transformer.writer_arrow.WriterArrowSemigroupoid;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.arrow.Arrow;

/**
 *
 * @author clintonselke
 */
public class WriterArrow<W,A,B,C> implements __4<WriterArrow.µ,W,A,B,C> {
    public static class µ {}
    
    private final __2<A,B,T2<C,W>> _run;
    
    private WriterArrow(__2<A,B,T2<C,W>> run) {
        this._run = run;
    }
    
    public static <W,A,B,C> WriterArrow<W,A,B,C> writerArrow(__2<A,B,T2<C,W>> run) {
        return new WriterArrow<>(run);
    }

    public __2<A,B,T2<C,W>> run() {
        return _run;
    }
    
    public static <W,A> WriterArrowSemigroupoid<W,A> semigroupoid(Semigroup<W> w, Arrow<A> a) {
        return new WriterArrowSemigroupoid<W,A>() {
            @Override
            public Semigroup<W> w() {
                return w;
            }
            @Override
            public Arrow<A> a() {
                return a;
            }
        };
    }
    
    public static <W,A> WriterArrowCategory<W,A> category(Monoid<W> w, Arrow<A> a) {
        return new WriterArrowCategory<W,A>() {
            @Override
            public Monoid<W> w() {
                return w;
            }
            @Override
            public Arrow<A> a() {
                return a;
            }
        };
    }
    
    public static <W,A> WriterArrowArrow<W,A> arrow(Monoid<W> w, Arrow<A> a) {
        return new WriterArrowArrow<W,A>() {
            @Override
            public Monoid<W> w() {
                return w;
            }
            @Override
            public Arrow<A> a() {
                return a;
            }
        };
    }
    
    public static <W,A> WriterArrowArrowWriter<W,A> arrowWriter(Monoid<W> w, Arrow<A> a) {
        return new WriterArrowArrowWriter<W,A>() {
            @Override
            public Monoid<W> w() {
                return w;
            }
            @Override
            public Arrow<A> a() {
                return a;
            }
        };
    }
    
    public static <W,A> WriterArrowArrowTransformer<W,A> arrowTransformer(Monoid<W> w, Arrow<A> a) {
        return new WriterArrowArrowTransformer<W,A>() {
            @Override
            public Monoid<W> w() {
                return w;
            }
            @Override
            public Arrow<A> a() {
                return a;
            }
        };
    }
}
