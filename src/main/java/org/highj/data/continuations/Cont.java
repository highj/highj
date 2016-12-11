package org.highj.data.continuations;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.continuations.cont.ContMonad;
import org.highj.function.Functions;

import java.util.function.Function;

public class Cont<R, A> implements __2<Cont.µ, R, A> {

    private Function<Function<A,R>,R> fn;

    public static class µ {}

    public Cont(Function<Function<A,R>,R> fn) {
        this.fn = fn;
    }

    public Function<Function<A,R>,R> runCont() {
       return fn;
    }

    public Cont<R,A> mapCont(Function<R,R> transform) {
       return new Cont<>(Functions.compose(transform,fn));
    }

    public <B> Cont<R,B> withCont(Function<Function<B,R>,Function<A,R>> transform) {
        return new Cont<>(Functions.compose(fn,transform));
    }

    public static <S> ContMonad<S> monad()  {
        return new ContMonad<S>(){};
    }

}


/*

instance Functor (Cont r) where
    fmap f m = Cont $ \c -> runCont m (c . f)

instance Monad (Cont r) where
    return a = Cont ($ a)
    m >>= k  = Cont $ \c -> runCont m $ \a -> runCont (k a) c

instance MonadCont (Cont r) where
    callCC f = Cont $ \c -> runCont (f (\a -> Cont $ \__ -> c a)) c

{- |
The continuation monadTrans transformer.
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
    callCC f = ContT $ \c -> runContT (f (\a -> ContT $ \__ -> c a)) c

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
Here is an example how to combine it with @IO@ monadTrans:

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