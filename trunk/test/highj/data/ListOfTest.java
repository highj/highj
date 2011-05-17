/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.List;
import highj._;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class ListOfTest {
    
    private ListOf listOf;
    
    @Before
    public void setUp() {
        listOf = ListOf.getInstance();
    }
    
    @After
    public void tearDown() {
        listOf = null;
    }

    @Test
    public void testWrapUnwrap() {
        List<String> list = List.list("one","two","three","four");
        _<ListOf, String> wrapped = listOf.wrap(list);
        List<String> listUnwrapped = listOf.unwrap(wrapped);
        assertEquals(list, listUnwrapped);
    }

    @Test
    public void testIsEmpty() {
        List<String> emptyList = List.list();
        _<ListOf, String> emptyWrapped = listOf.wrap(emptyList);
        assertEquals(true, listOf.isEmpty(emptyWrapped));

        List<String> nonEmptyList = List.list("one");
        _<ListOf, String> nonEmptyWrapped = listOf.wrap(nonEmptyList);
        assertEquals(false, listOf.isEmpty(nonEmptyWrapped));
    }

    @Test
    public void testEmpty() {
        _<ListOf, String> emptyWrapped = listOf.empty();
        List<String> emptyList = listOf.unwrap(emptyWrapped);
        assertEquals(true, emptyList.isEmpty());
    }

    /**
     * Test of toString method, of class ListOf.
     */
    @Test
    public void testToString() {
        List<String> emptyList = List.list();
        _<ListOf, String> emptyWrapped = listOf.wrap(emptyList);
        assertEquals("[]", listOf.toString(emptyWrapped));

        List<String> nonEmptyList = List.list("one","two","three");
        _<ListOf, String> nonEmptyWrapped = listOf.wrap(nonEmptyList);
        assertEquals("[one, two, three]", listOf.toString(nonEmptyWrapped));
    }

}
