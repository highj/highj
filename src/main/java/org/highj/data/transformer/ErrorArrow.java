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
import org.highj.data.Either;
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
public class ErrorArrow<EX,A,B,C> implements __4<ErrorArrow.µ,EX,A,B,C> {
    public static class µ {}
    
    private final __2<A,B,Either<EX,C>> _run;
    
    private ErrorArrow(__2<A,B,Either<EX,C>> run) {
        this._run = run;
    }
    
    public static <EX,A,B,C> ErrorArrow<EX,A,B,C> errorArrow(__2<A,B,Either<EX,C>> run) {
        return new ErrorArrow<>(run);
    }

    public __2<A,B,Either<EX,C>> run() {
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
