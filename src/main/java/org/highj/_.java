package org.highj;


/**
 * An interface for "lifting" a type constructors to type parameter level in order to allow the simulation
 * of higher order type polymorphism. The "hidden" class µ (which should be an inner static class
 * of the type constructor) identifies the type class and allows to cast values
 * back to their normal version. For this, the type constructor should provide a static method called narrow.
 */
public interface _<µ, T> {
}
