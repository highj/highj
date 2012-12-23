package org.highj.data;

import org.highj._;
import org.highj.__;
import org.highj.function.F1;
import org.highj.typeclass.monad.Monad;
import org.highj.typeclass.monad.MonadAbstract;

public class Cont<R,A> extends __<Cont.µ, R, A> {

    private static final µ hidden = new µ();

    private F1<F1<A,R>,R> fn;

    /**
     * The witness class of Either
     */
    public static class µ {
        private µ() {
        }
    }

    private Cont(F1<F1<A,R>,R> fn) {
        super(hidden);
        this.fn = fn;
    }

    public F1<F1<A,R>,R> runCont() {
       return fn;
    }

    public Cont<R,A> mapCont(F1<R,R> transform) {
       return new Cont<R,A>(fn.andThen(transform));
    }

    public <B> Cont<R,B> withCont(F1<F1<B,R>,F1<A,R>> transform) {
        return new Cont<R,B>(transform.andThen(fn));
    }

    private static <S> Monad<__.µ<µ, S>> monad()  {
        return new MonadAbstract<__.µ<µ, S>>() {
            @Override
            public <A> _<__.µ<µ, S>, A> pure(A a) {
                return new Cont<S,A>(F1.<A,S>flipApply().$(a));
            }

            @Override
            public <A, B> _<__.µ<µ, S>, B> ap(_<__.µ<µ, S>, F1<A, B>> fn, _<__.µ<µ, S>, A> nestedA) {
                return null;
            }

            //bind or join...

        };
    }

}


/*

instance Functor (Cont r) where
    fmap f m = Cont $ \c -> runCont m (c . f)

instance Monad (Cont r) where
    return a = Cont ($ a)
    m >>= k  = Cont $ \c -> runCont m $ \a -> runCont (k a) c

instance MonadCont (Cont r) where
    callCC f = Cont $ \c -> runCont (f (\a -> Cont $ \_ -> c a)) c

{- |
The continuation monad transformer.
Can be used to add continuation handling to other monads.
-}
newtype ContT r m a = ContT { runContT :: (a -> m r) -> m r }

mapContT :: (m r -> m r) -> ContT r m a -> ContT r m a
mapContT f m = ContT $ f . runContT m

withContT :: ((b -> m r) -> (a -> m r)) -> ContT r m a -> ContT r m b
withContT f m = ContT $ runContT m . f

instance (Monad m) => Functor (ContT r m) where
    fmap f m = ContT $ \c -> runContT m (c . f)

instance (Monad m) => Monad (ContT r m) where
    return a = ContT ($ a)
    m >>= k  = ContT $ \c -> runContT m (\a -> runContT (k a) c)

instance (Monad m) => MonadCont (ContT r m) where
    callCC f = ContT $ \c -> runContT (f (\a -> ContT $ \_ -> c a)) c

-- ---------------------------------------------------------------------------
-- Instances for other mtl transformers

instance MonadTrans (ContT r) where
    lift m = ContT (m >>=)

instance (MonadIO m) => MonadIO (ContT r m) where
    liftIO = lift . liftIO

-- Needs -fallow-undecidable-instances
instance (MonadReader r' m) => MonadReader r' (ContT r m) where
    ask       = lift ask
    local f m = ContT $ \c -> do
        r <- ask
        local f (runContT m (local (const r) . c))

-- Needs -fallow-undecidable-instances
instance (MonadState s m) => MonadState s (ContT r m) where
    get = lift get
    put = lift . put

{- $simpleContExample
Calculating length of a list continuation-style:

>calculateLength :: [a] -> Cont r Int
>calculateLength l = return (length l)

Here we use @calculateLength@ by making it to pass its result to @print@:

>main = do
>  runCont (calculateLength "123") print
>  -- result: 3

It is possible to chain 'Cont' blocks with @>>=@.

>double :: Int -> Cont r Int
>double n = return (n * 2)
>
>main = do
>  runCont (calculateLength "123" >>= double) print
>  -- result: 6
-}

{- $callCCExample
This example gives a taste of how escape continuations work, shows a typical
pattern for their usage.

>-- Returns a string depending on the length of the name parameter.
>-- If the provided string is empty, returns an error.
>-- Otherwise, returns a welcome message.
>whatsYourName :: String -> String
>whatsYourName name =
>  (`runCont` id) $ do                      -- 1
>    response <- callCC $ \exit -> do       -- 2
>      validateName name exit               -- 3
>      return $ "Welcome, " ++ name ++ "!"  -- 4
>    return response                        -- 5
>
>validateName name exit = do
>  when (null name) (exit "You forgot to tell me your name!")

Here is what this example does:

(1) Runs an anonymous 'Cont' block and extracts value from it with
@(\`runCont\` id)@. Here @id@ is the continuation, passed to the @Cont@ block.

(1) Binds @response@ to the result of the following 'callCC' block,
binds @exit@ to the continuation.

(1) Validates @name@.
This approach illustrates advantage of using 'callCC' over @return@.
We pass the continuation to @validateName@,
and interrupt execution of the @Cont@ block from /inside/ of @validateName@.

(1) Returns the welcome message from the @callCC@ block.
This line is not executed if @validateName@ fails.

(1) Returns from the @Cont@ block.
-}

{-$ContTExample
'ContT' can be used to add continuation handling to other monads.
Here is an example how to combine it with @IO@ monad:

>import Control.Monad.Cont
>import System.IO
>
>main = do
>  hSetBuffering stdout NoBuffering
>  runContT (callCC askString) reportResult
>
>askString :: (String -> ContT () IO String) -> ContT () IO String
>askString next = do
>  liftIO $ putStrLn "Please enter a string"
>  s <- liftIO $ getLine
>  next s
>
>reportResult :: String -> IO ()
>reportResult s = do
>  putStrLn ("You entered: " ++ s)

Action @askString@ requests user to enter a string,
and passes it to the continuation.
@askString@ takes as a parameter a continuation taking a string parameter,
and returning @IO ()@.
Compare its signature to 'runContT' definition.
-}
* */