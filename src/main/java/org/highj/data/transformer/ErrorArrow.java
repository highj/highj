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
import org.highj.data.collection.Either;
import org.highj.data.transformer.error_arrow.ErrorArrowArrow;
import org.highj.data.transformer.error_arrow.ErrorArrowArrowChoice;
import org.highj.data.transformer.error_arrow.ErrorArrowArrowError;
import org.highj.data.transformer.error_arrow.ErrorArrowArrowTransformer;
import org.highj.data.transformer.error_arrow.ErrorArrowCategory;
import org.highj.data.transformer.error_arrow.ErrorArrowSemigroupoid;
import org.highj.typeclass2.arrow.ArrowChoice;

/**
 *
 * @author clintonselke
 */
public class ErrorArrow<EX,A,B,C> implements ____<ErrorArrow.µ,EX,A,B,C> {
    public static class µ {}
    
    private final __<A,B,Either<EX,C>> _run;
    
    private ErrorArrow(__<A,B,Either<EX,C>> run) {
        this._run = run;
    }
    
    public static <EX,A,B,C> ErrorArrow<EX,A,B,C> errorArrow(__<A,B,Either<EX,C>> run) {
        return new ErrorArrow<>(run);
    }
    
    public static <EX,A,B,C> ErrorArrow<EX,A,B,C> narrow(____<ErrorArrow.µ,EX,A,B,C> a) {
        return (ErrorArrow<EX,A,B,C>)a;
    }
    
    public static <EX,A,B,C> ErrorArrow<EX,A,B,C> narrow(___<_<ErrorArrow.µ,EX>,A,B,C> a) {
        return (ErrorArrow<EX,A,B,C>)a;
    }
    
    public static <EX,A,B,C> ErrorArrow<EX,A,B,C> narrow(__<_<_<ErrorArrow.µ,EX>,A>,B,C> a) {
        return (ErrorArrow<EX,A,B,C>)a;
    }
    
    public static <EX,A,B,C> ErrorArrow<EX,A,B,C> narrow(_<_<_<_<ErrorArrow.µ,EX>,A>,B>,C> a) {
        return (ErrorArrow<EX,A,B,C>)a;
    }
    
    public __<A,B,Either<EX,C>> run() {
        return _run;
    }
    
    public static <EX,A> ErrorArrowSemigroupoid<EX,A> semigroupoid(ArrowChoice<A> a) {
        return () -> a;
    }
    
    public static <EX,A> ErrorArrowCategory<EX,A> category(ArrowChoice<A> a) {
        return () -> a;
    }
    
    public static <EX,A> ErrorArrowArrow<EX,A> arrow(ArrowChoice<A> a) {
        return () -> a;
    }
    
    public static <EX,A> ErrorArrowArrowError<EX,A> arrowError(ArrowChoice<A> a) {
        return () -> a;
    }
    
    public static <EX,A> ErrorArrowArrowChoice<EX,A> arrowChoice(ArrowChoice<A> a) {
        return () -> a;
    }
    
    public static <EX,A> ErrorArrowArrowTransformer<EX,A> arrowTransformer(ArrowChoice<A> a) {
        return () -> a;
    }
}
