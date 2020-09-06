package org.highj.data.bool;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.MonoidContract;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class AndMonoidContract implements MonoidContract<Boolean> {

    @Override
    public Monoid<Boolean> subject() {
        return Booleans.andMonoid;
    }
}
