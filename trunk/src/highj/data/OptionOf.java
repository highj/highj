/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.Option;
import highj._;

/**
 *
 * @author DGronau
 */
public final class OptionOf {

    private static final OptionOf hidden = new OptionOf();

    private OptionOf() {
    }

    public static <T> _<OptionOf, T> option(T t) {
        return t == null ? OptionOf.<T>none() : some(t);
    }

    public static <T> _<OptionOf, T> some(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Null value not allowed");
        }
        return wrap(Option.some(t));
    }

    public static <T> _<OptionOf, T> none() {
        return wrap(Option.<T>none());
    }

    public static <T> _<OptionOf, T> wrap(Option<T> ot) {
        return new _<OptionOf, T>(hidden, ot);
    }

    public static <T> Option<T> unwrap(_<OptionOf, T> ow) {
        return (Option<T>) ow.read(hidden);
    }

    public static boolean isSome(_<OptionOf, ?> ow) {
        return unwrap(ow).isSome();
    }

    public static boolean isNone(_<OptionOf, ?> ow) {
        return unwrap(ow).isNone();
    }

    public static <T> T orSome(_<OptionOf, T> ow, T t) {
        if (t == null) {
            throw new IllegalArgumentException("Null value not allowed");
        }
        return unwrap(ow).orSome(t);
    }

    public static <T> T get(_<OptionOf, T> ow) {
        try {
            return unwrap(ow).valueE("No such element");
        } catch (Error e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
