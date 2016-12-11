/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.transformer.ErrorArrow;
import org.highj.typeclass2.arrow.ArrowChoice;
import org.highj.typeclass2.arrow.Semigroupoid;

import static org.highj.Hkt.asErrorArrow;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowSemigroupoid<EX,A> extends Semigroupoid<__<__<ErrorArrow.µ,EX>,A>> {
    
    public ArrowChoice<A> a();

    @Override
    public default <B, C, D> ErrorArrow<EX, A, B, D> dot(__2<__<__<ErrorArrow.µ, EX>, A>, C, D> cd, __2<__<__<ErrorArrow.µ, EX>, A>, B, C> bc) {
        return ErrorArrow.errorArrow(a().dot(
            a().fanin(
                a().arr(Either::Left),
                asErrorArrow(cd).run()
            ),
            asErrorArrow(bc).run()
        ));
    }
}
