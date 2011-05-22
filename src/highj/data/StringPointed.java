/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import highj.typeclasses.category.PointedBounded;
import highj.typeclasses.category.PointedBoundedAbstract;

/**
 *
 * @author DGronau
 */
public class StringPointed extends PointedBoundedAbstract<StringOf, Character> implements PointedBounded<StringOf, Character> {

    @Override
    public <A extends Character, B extends Character> _<StringOf, B> fmap(F<A, B> fn, _<StringOf, A> nestedA) {
        char[] chars = StringOf.unwrap(nestedA).toCharArray();
        for(int i = 0; i < chars.length; i++) {
            chars[i] = fn.f((A) Character.valueOf(chars[i]));
        }
        return StringOf.wrap(new String(chars));
    }

    @Override
    public <A extends Character> _<StringOf, A> pure(A a) {
        return StringOf.wrap(a.toString());
    }
    
}
