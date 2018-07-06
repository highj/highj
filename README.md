# Welcome to highJ #

[![Build Status](https://travis-ci.org/highj/highj.svg?branch=master)](https://travis-ci.org/highj/highj)

HighJ tries to overcome Java's lack of higher order type polymorphism (a.k.a. "higher kinded types" in Scala), and translates several well known type-classes (including `Applicative`, `Monad` and `Foldable`) and data structures from Haskell to Java.

The code required to simulate higher order polymorphism could be kindly described as "interesting", but to be honest, it is pretty ugly. Its most surprising feature is that it actually works.

This project is just an experiment, it relies heavily on Java 8 features, and is *not yet* intended for production, but we are working on it. A lot of bad things might happen:
  * Recursion is sometimes hard to avoid, which might lead to `StackOverflowError`s
  * The code isn't very efficient, there might be excessive object creation going on
  * Lazy behavior might lead to unexpected results, as beginners often face in Haskell
  * The HKT mechanism requires casts. Our assumption is that nobody tries to break the mechanism intentionally 
  * The test coverage and Wiki documentation is not as comprehensive as it should
  
Please consult the wiki for a more detailed description.

## Related

- [functionaljava](https://github.com/functionaljava/functionaljava)
- [Cyclops-React](https://github.com/aol/cyclops-react)
- [Kotlinz](https://github.com/kotlinz/kotlinz)
- [Scalaz](https://github.com/scalaz/scalaz)
- [Swiftz](https://github.com/typelift/Swiftz)

## License

See [LICENSE](LICENSE.txt)
