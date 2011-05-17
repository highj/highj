/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.Option;
import highj._;
import highj._.Accessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dgronau
 */
public class OptionOfTest {
    private OptionOf optionOf;
    
    public OptionOfTest() {
    }
    
    @Before
    public void setUp() {
        optionOf = OptionOf.getInstance();
    }
    
    @After
    public void tearDown() {
        optionOf = null;
    }

    @Test
    public void testOption() {
        _<OptionOf, String> emptyOption = optionOf.option(null);
        assertEquals(true, optionOf.unwrap(emptyOption).isNone());
        _<OptionOf, String> nonEmptyOption = optionOf.option("X");
        assertEquals(true, optionOf.unwrap(nonEmptyOption).isSome());
        assertEquals("X", optionOf.unwrap(nonEmptyOption).valueE("WTF?"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSome() {
        _<OptionOf, String> nonEmptyOption = optionOf.some("X");
        assertEquals(true, optionOf.unwrap(nonEmptyOption).isSome());
        assertEquals("X", optionOf.unwrap(nonEmptyOption).valueE("WTF?"));

        _<OptionOf, String> emptyOption = optionOf.some(null);
    }

    @Test
    public void testNone() {
        _<OptionOf, String> emptyOption = optionOf.none();
        assertEquals(true, optionOf.unwrap(emptyOption).isNone());
    }

    @Test
    public void testWrapUnwrap() {
       Option<String> empty = Option.none();        
       assertEquals(empty, optionOf.unwrap(optionOf.wrap(empty)));
       Option<String> nonEmpty = Option.some("bla");        
       assertEquals(nonEmpty, optionOf.unwrap(optionOf.wrap(nonEmpty)));
    }

    @Test
    public void testIsSome() {
       _<OptionOf, String> emptyOption = optionOf.option(null);
        assertEquals(false, optionOf.isSome(emptyOption));
       _<OptionOf, String> nonEmptyOption = optionOf.option("X");
        assertEquals(true, optionOf.isSome(nonEmptyOption));
    }

    @Test
    public void testIsNone() {
       _<OptionOf, String> emptyOption = optionOf.option(null);
        assertEquals(true, optionOf.isNone(emptyOption));
       _<OptionOf, String> nonEmptyOption = optionOf.option("X");
        assertEquals(false, optionOf.isNone(nonEmptyOption));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrSome() {
       _<OptionOf, String> emptyOption = optionOf.option(null);
       _<OptionOf, String> nonEmptyOption = optionOf.option("X");
       assertEquals("Y", optionOf.orSome(emptyOption,"Y"));  
       assertEquals("X", optionOf.orSome(nonEmptyOption,"Y"));  
       optionOf.orSome(nonEmptyOption, null);        
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGet() {
       _<OptionOf, String> emptyOption = optionOf.option(null);
       _<OptionOf, String> nonEmptyOption = optionOf.option("X");
       assertEquals("X", optionOf.get(nonEmptyOption));  
       optionOf.get(emptyOption);
    }

   
}
