/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.Option;
import highj._;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dgronau
 */
public class OptionOfTest {
    
    public OptionOfTest() {
    }

    @Test
    public void testOption() {
        _<OptionOf, String> emptyOption = OptionOf.option(null);
        assertEquals(true, OptionOf.unwrap(emptyOption).isNone());
        _<OptionOf, String> nonEmptyOption = OptionOf.option("X");
        assertEquals(true, OptionOf.unwrap(nonEmptyOption).isSome());
        assertEquals("X", OptionOf.unwrap(nonEmptyOption).valueE("WTF?"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSome() {
        _<OptionOf, String> nonEmptyOption = OptionOf.some("X");
        assertEquals(true, OptionOf.unwrap(nonEmptyOption).isSome());
        assertEquals("X", OptionOf.unwrap(nonEmptyOption).valueE("WTF?"));

        _<OptionOf, String> emptyOption = OptionOf.some(null);
    }

    @Test
    public void testNone() {
        _<OptionOf, String> emptyOption = OptionOf.none();
        assertEquals(true, OptionOf.unwrap(emptyOption).isNone());
    }

    @Test
    public void testWrapUnwrap() {
       Option<String> empty = Option.none();        
       assertEquals(empty, OptionOf.unwrap(OptionOf.wrap(empty)));
       Option<String> nonEmpty = Option.some("bla");        
       assertEquals(nonEmpty, OptionOf.unwrap(OptionOf.wrap(nonEmpty)));
    }

    @Test
    public void testIsSome() {
       _<OptionOf, String> emptyOption = OptionOf.option(null);
        assertEquals(false, OptionOf.isSome(emptyOption));
       _<OptionOf, String> nonEmptyOption = OptionOf.option("X");
        assertEquals(true, OptionOf.isSome(nonEmptyOption));
    }

    @Test
    public void testIsNone() {
       _<OptionOf, String> emptyOption = OptionOf.option(null);
        assertEquals(true, OptionOf.isNone(emptyOption));
       _<OptionOf, String> nonEmptyOption = OptionOf.option("X");
        assertEquals(false, OptionOf.isNone(nonEmptyOption));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrSome() {
       _<OptionOf, String> emptyOption = OptionOf.option(null);
       _<OptionOf, String> nonEmptyOption = OptionOf.option("X");
       assertEquals("Y", OptionOf.orSome(emptyOption,"Y"));  
       assertEquals("X", OptionOf.orSome(nonEmptyOption,"Y"));  
       OptionOf.orSome(nonEmptyOption, null);        
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGet() {
       _<OptionOf, String> emptyOption = OptionOf.option(null);
       _<OptionOf, String> nonEmptyOption = OptionOf.option("X");
       assertEquals("X", OptionOf.get(nonEmptyOption));  
       OptionOf.get(emptyOption);
    }

   
}
