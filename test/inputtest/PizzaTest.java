/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputtest;

import java.util.HashMap;
import model.Pizza;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lovrenco
 */
public class PizzaTest {
    Pizza p1A;
    Pizza p1B;
    Pizza p2;
    
    public PizzaTest() {
        p1A = new Pizza();
        p1A.setIdPizza(99);
        p1B = new Pizza();
        p1B.setIdPizza(99);
        p2 = new Pizza();
        p2.setIdPizza(23);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testEquals(){
               
        assertEquals(p1A, p1B);
        assertNotEquals(p2, p1A);
        assertNotEquals(p2, p1B);
        
        assertTrue(p1A.equals(p1B));
        assertFalse(p2.equals(p1A));
        assertFalse(p2.equals(p1B));
    }
    
    @Test
    public void testHashMapPut(){
        HashMap<Pizza, Integer> map = new HashMap<>();
        addInMap(map,p1A);
        addInMap(map,p1B);
        addInMap(map,p2);
        
        assertEquals((Integer)2, map.get(p1A));
        assertEquals((Integer)2, map.get(p1B));
        assertEquals((Integer)1, map.get(p2));
        
    }
    
    public void addInMap(HashMap<Pizza, Integer> map, Pizza p){
        Integer qty = map.get(p);
        map.put(p, (qty==null?1:qty+1));
    }
    
}
