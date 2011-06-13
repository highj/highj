/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import highj.data.ListOf;
import fj.Monoid;
import highj._;
import highj.data.ListMonadPlus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlusAbstractTest {
    
    Plus<ListOf> plus;
      
    @Before
    public void setUp() {
       plus = ListMonadPlus.getInstance(); 
    }
    
    @After
    public void tearDown() {
        plus = null;
    }

    @Test
    public void testAsMonoid() {
        Monoid<_<ListOf,Integer>> monoid = plus.asMonoid();
        
        _<ListOf, Integer> emptyList = ListOf.<Integer>empty();
        
        assertEquals(ListOf.toString(emptyList), ListOf.toString(monoid.zero()));
    }

}
