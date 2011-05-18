/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.data.List;
import highj._;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class ListOfTest {
    
    @Test
    public void testWrapUnwrap() {
        List<String> list = List.list("one","two","three","four");
        _<ListOf, String> wrapped = ListOf.wrap(list);
        List<String> listUnwrapped = ListOf.unwrap(wrapped);
        assertEquals(list, listUnwrapped);
    }

    @Test
    public void testIsEmpty() {
        List<String> emptyList = List.list();
        _<ListOf, String> emptyWrapped = ListOf.wrap(emptyList);
        assertEquals(true, ListOf.isEmpty(emptyWrapped));

        List<String> nonEmptyList = List.list("one");
        _<ListOf, String> nonEmptyWrapped = ListOf.wrap(nonEmptyList);
        assertEquals(false, ListOf.isEmpty(nonEmptyWrapped));
    }

    @Test
    public void testEmpty() {
        _<ListOf, String> emptyWrapped = ListOf.empty();
        List<String> emptyList = ListOf.unwrap(emptyWrapped);
        assertEquals(true, emptyList.isEmpty());
    }

    /**
     * Test of toString method, of class ListOf.
     */
    @Test
    public void testToString() {
        List<String> emptyList = List.list();
        _<ListOf, String> emptyWrapped = ListOf.wrap(emptyList);
        assertEquals("[]", ListOf.toString(emptyWrapped));

        List<String> nonEmptyList = List.list("one","two","three");
        _<ListOf, String> nonEmptyWrapped = ListOf.wrap(nonEmptyList);
        assertEquals("[one, two, three]", ListOf.toString(nonEmptyWrapped));
    }

}
