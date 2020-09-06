package org.highj.data;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.MonoidContract;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class ListMonoidContract implements MonoidContract<List<String>> {
    @Override
    public Monoid<List<String>> subject() {
        return List.monoid();
    }
}
