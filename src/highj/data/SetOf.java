/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import highj._;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
 * Note that java.util.Set is used here, as fj.data.Set needs an Ord for construction,
 * which makes it unusable for most type classes, as in Haskell.
 */
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

    public static <A> boolean contains(_<SetOf, A> wrapped, A a) {
        return unwrap(wrapped).contains(a);
    }

    public static <A> _<SetOf, A> set(A ... as) {
        return wrap(new HashSet<A>(Arrays.asList(as)));
    }

    public static <A> _<SetOf, A> set(Collection<A> as) {
        return wrap(new HashSet<A>(as));
    }

    public static <A> void add(_<SetOf, A> wrapped, A a) {
        unwrap(wrapped).add(a);
    }
    
}
