package org.highj.function;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.MonoidContract;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class StringMonoidContract implements MonoidContract<String> {

    @Override
    public Monoid<String> subject() {
        return Strings.monoid;
    }
}
