/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.doblock;

import fj.F;
import fj.F2;
import fj.Unit;
import highj._;
import highj.typeclasses.category.Monad;

/**
 *
 * @author DGronau
 */
public class Do<Ctor, R, V1, V2, V3, V4, V5, V6> {
    private final Monad<Ctor> monad;
    private final _<Ctor, R> returnValue;
    private final _<Ctor, V1> v1;
    private final _<Ctor, V2> v2;
    private final _<Ctor, V3> v3;
    private final _<Ctor, V4> v4;
    private final _<Ctor, V5> v5;
    private final _<Ctor, V6> v6;

    Do(Monad<Ctor> monad, _<Ctor, R> action,
            _<Ctor, V1> v1, _<Ctor, V2> v2, _<Ctor, V3> v3,
            _<Ctor, V4> v4, _<Ctor, V5> v5, _<Ctor, V6> v6) {
        this.monad = monad;
        this.returnValue = action;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.v5 = v5;
        this.v6 = v6;
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> act(_<Ctor, B> action) {
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(
                monad, monad.semicolon(returnValue, action), v1, v2, v3, v4, v5, v6);
    }

    public <B> _<Ctor, B> return_(B b) {
        return monad.semicolon(returnValue, monad.pure(b));
    }

    public _<Ctor, R> return_() {
        return returnValue;
    }

    public <B> Do<Ctor, B, B, V2, V3, V4, V5, V6> assign1(_<Ctor, B> action) {
        return new Do<Ctor, B, B, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, action), action, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, B, V3, V4, V5, V6> assign2(_<Ctor, B> action) {
        return new Do<Ctor, B, V1, B, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, action), v1, action, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, B, V4, V5, V6> assign3(_<Ctor, B> action) {
        return new Do<Ctor, B, V1, V2, B, V4, V5, V6>(monad, monad.semicolon(returnValue, action), v1, v2, action, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, B, V5, V6> assign4(_<Ctor, B> action) {
        return new Do<Ctor, B, V1, V2, V3, B, V5, V6>(monad, monad.semicolon(returnValue, action), v1, v2, v3, action, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, B, V6> assign5(_<Ctor, B> action) {
        return new Do<Ctor, B, V1, V2, V3, V4, B, V6>(monad, monad.semicolon(returnValue, action), v1, v2, v3, v4, action, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, B> assign6(_<Ctor, B> action) {
        return new Do<Ctor, B, V1, V2, V3, V4, V5, B>(monad, monad.semicolon(returnValue, action), v1, v2, v3, v4, v5, action);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind1(F<V1, _<Ctor, B>> fn) {
        _<Ctor, B> b = monad.bind(v1, fn);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind2(F<V2, _<Ctor, B>> fn) {
        _<Ctor, B> b = monad.bind(v2, fn);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind3(F<V3, _<Ctor, B>> fn) {
        _<Ctor, B> b = monad.bind(v3, fn);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind4(F<V4, _<Ctor, B>> fn) {
        _<Ctor, B> b = monad.bind(v4, fn);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind5(F<V5, _<Ctor, B>> fn) {
        _<Ctor, B> b = monad.bind(v5, fn);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind6(F<V6, _<Ctor, B>> fn) {
        _<Ctor, B> b = monad.bind(v6, fn);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind12(F2<V1, V2, _<Ctor, B>> fn) {
        _<Ctor, F<V2, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v1);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v2));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind13(F2<V1, V3, _<Ctor, B>> fn) {
        _<Ctor, F<V3, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v1);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v3));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind14(F2<V1, V4, _<Ctor, B>> fn) {
        _<Ctor, F<V4, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v1);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v4));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind15(F2<V1, V5, _<Ctor, B>> fn) {
        _<Ctor, F<V5, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v1);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v5));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind16(F2<V1, V6, _<Ctor, B>> fn) {
        _<Ctor, F<V6, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v1);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v6));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind23(F2<V2, V3, _<Ctor, B>> fn) {
        _<Ctor, F<V3, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v2);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v3));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind24(F2<V2, V4, _<Ctor, B>> fn) {
        _<Ctor, F<V4, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v2);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v4));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind25(F2<V2, V5, _<Ctor, B>> fn) {
        _<Ctor, F<V5, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v2);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v5));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind26(F2<V2, V6, _<Ctor, B>> fn) {
        _<Ctor, F<V6, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v2);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v6));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind34(F2<V3, V4, _<Ctor, B>> fn) {
        _<Ctor, F<V4, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v3);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v4));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind35(F2<V3, V5, _<Ctor, B>> fn) {
        _<Ctor, F<V5, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v3);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v5));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind36(F2<V3, V6, _<Ctor, B>> fn) {
        _<Ctor, F<V6, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v3);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v6));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind45(F2<V4, V5, _<Ctor, B>> fn) {
        _<Ctor, F<V5, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v4);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v5));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind46(F2<V4, V6, _<Ctor, B>> fn) {
        _<Ctor, F<V6, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v4);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v6));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> bind56(F2<V5, V6, _<Ctor, B>> fn) {
        _<Ctor, F<V6, _<Ctor, B>>> fn1 = monad.fmap(fn.curry(), v5);
        _<Ctor, B> b = monad.join(monad.ap(fn1, v6));
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public Do<Ctor, R, V2, V1, V3, V4, V5, V6> swap12() {
        return new Do<Ctor, R, V2, V1, V3, V4, V5, V6>(monad, returnValue, v2, v1, v3, v4, v5, v6);
    }

    public Do<Ctor, R, V3, V2, V1, V4, V5, V6> swap13() {
        return new Do<Ctor, R, V3, V2, V1, V4, V5, V6>(monad, returnValue, v3, v2, v1, v4, v5, v6);
    }

    public Do<Ctor, R, V4, V2, V3, V1, V5, V6> swap14() {
        return new Do<Ctor, R, V4, V2, V3, V1, V5, V6>(monad, returnValue, v4, v2, v3, v1, v5, v6);
    }

    public Do<Ctor, R, V5, V2, V3, V4, V1, V6> swap15() {
        return new Do<Ctor, R, V5, V2, V3, V4, V1, V6>(monad, returnValue, v5, v2, v3, v4, v1, v6);
    }

    public Do<Ctor, R, V6, V2, V3, V4, V5, V1> swap16() {
        return new Do<Ctor, R, V6, V2, V3, V4, V5, V1>(monad, returnValue, v6, v2, v3, v4, v5, v1);
    }

    public Do<Ctor, R, V1, V3, V2, V4, V5, V6> swap23() {
        return new Do<Ctor, R, V1, V3, V2, V4, V5, V6>(monad, returnValue, v1, v3, v2, v4, v5, v6);
    }

    public Do<Ctor, R, V1, V4, V3, V2, V5, V6> swap24() {
        return new Do<Ctor, R, V1, V4, V3, V2, V5, V6>(monad, returnValue, v1, v4, v3, v2, v5, v6);
    }

    public Do<Ctor, R, V1, V5, V3, V4, V2, V6> swap25() {
        return new Do<Ctor, R, V1, V5, V3, V4, V2, V6>(monad, returnValue, v1, v5, v3, v4, v2, v6);
    }

    public Do<Ctor, R, V1, V6, V3, V4, V5, V2> swap26() {
        return new Do<Ctor, R, V1, V6, V3, V4, V5, V2>(monad, returnValue, v1, v6, v3, v4, v5, v2);
    }

    public Do<Ctor, R, V1, V2, V4, V3, V5, V6> swap34() {
        return new Do<Ctor, R, V1, V2, V4, V3, V5, V6>(monad, returnValue, v1, v2, v4, v3, v5, v6);
    }

    public Do<Ctor, R, V1, V2, V5, V4, V3, V6> swap35() {
        return new Do<Ctor, R, V1, V2, V5, V4, V3, V6>(monad, returnValue, v1, v2, v5, v4, v3, v6);
    }

    public Do<Ctor, R, V1, V2, V6, V4, V5, V3> swap36() {
        return new Do<Ctor, R, V1, V2, V6, V4, V5, V3>(monad, returnValue, v1, v2, v6, v4, v5, v3);
    }

    public Do<Ctor, R, V1, V2, V3, V5, V4, V6> swap45() {
        return new Do<Ctor, R, V1, V2, V3, V5, V4, V6>(monad, returnValue, v1, v2, v3, v5, v4, v6);
    }

    public Do<Ctor, R, V1, V2, V3, V6, V5, V4> swap46() {
        return new Do<Ctor, R, V1, V2, V3, V6, V5, V4>(monad, returnValue, v1, v2, v3, v6, v5, v4);
    }

    public Do<Ctor, R, V1, V2, V3, V4, V6, V5> swap56() {
        return new Do<Ctor, R, V1, V2, V3, V4, V6, V5>(monad, returnValue, v1, v2, v3, v4, v6, v5);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> lift1(F<V1, B> fn) {
        _<Ctor, B> b = monad.fmap(fn, v1);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> lift2(F<V2, B> fn) {
        _<Ctor, B> b = monad.fmap(fn, v2);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> lift3(F<V3, B> fn) {
        _<Ctor, B> b = monad.fmap(fn, v3);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> lift4(F<V4, B> fn) {
        _<Ctor, B> b = monad.fmap(fn, v4);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> lift5(F<V5, B> fn) {
        _<Ctor, B> b = monad.fmap(fn, v5);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public <B> Do<Ctor, B, V1, V2, V3, V4, V5, V6> lift6(F<V6, B> fn) {
        _<Ctor, B> b = monad.fmap(fn, v6);
        return new Do<Ctor, B, V1, V2, V3, V4, V5, V6>(monad, monad.semicolon(returnValue, b), v1, v2, v3, v4, v5, v6);
    }

    public static <Ctor> Do<Ctor, Unit, Unit, Unit, Unit, Unit, Unit, Unit> with(Monad<Ctor> monad) {
        _<Ctor, Unit> u = monad.pure(Unit.unit());
        return new Do<Ctor, Unit, Unit, Unit, Unit, Unit, Unit, Unit>(monad, u, u, u, u, u, u, u);
    }
}
