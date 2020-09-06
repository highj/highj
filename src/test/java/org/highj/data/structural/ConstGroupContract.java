package org.highj.data.structural;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.highj.data.num.Integers;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.GroupContract;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class ConstGroupContract implements GroupContract<Const<Integer, String>> {

    @Override
    public Group<Const<Integer, String>> subject() {
        return Const.group(Integers.additiveGroup);
    }
}
