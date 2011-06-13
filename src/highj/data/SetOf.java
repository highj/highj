/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.Ord;
import fj.data.Set;
import highj._;

public final class SetOf {

    private final static SetOf hidden = new SetOf();

    private SetOf() {
    }

    public static <A> _<SetOf, A> wrap(Set<A> set) {
        return new _<SetOf, A>(hidden, set);
    }

    public static <A> Set<A> unwrap(_<SetOf, A> wrapped) {
        return (Set<A>) wrapped.read(hidden);
    }

    public static boolean isEmpty(_<SetOf, ?> wrapped) {
        return unwrap(wrapped).isEmpty();
    }

    public static <A> boolean member(_<SetOf, A> wrapped, A a) {
        return unwrap(wrapped).member(a);
    }

    public static <A> _<SetOf, A> empty(Ord<A> ord) {
        return wrap(Set.empty(ord));
    }

    public static <A> _<SetOf, A> insert(_<SetOf, A> wrapped, A a) {
        return wrap(unwrap(wrapped).insert(a));
    }
}
