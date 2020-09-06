package org.highj.data;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.MonoidContract;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class MultiSetMonoidContract implements MonoidContract<MultiSet<String>> {

    @Override
    public Monoid<MultiSet<String>> subject() {
        return MultiSet.monoid();
    }
}
