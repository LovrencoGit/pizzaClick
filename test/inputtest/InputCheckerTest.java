/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputtest;

import java.util.ArrayList;
import java.util.HashMap;
import model.Pizza;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.InputChecker;

/**
 *
 * @author Lovrenco
 */
public class InputCheckerTest {

    public InputCheckerTest() {
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
    public void testCheckGenericText() {
        assertFalse(InputChecker.checkGenericText("loris"));
        assertTrue(InputChecker.checkGenericText("loris'"));
        assertTrue(InputChecker.checkGenericText("lo#ris"));
    }
    
    @Test
    public void testCheckData() {
        assertFalse(InputChecker.checkData("2016-09-13"));
        assertTrue(InputChecker.checkData("13-09-2016'"));
        assertTrue(InputChecker.checkData("2016/09/13"));
        assertTrue(InputChecker.checkData("2016/09/13 "));
        assertTrue(InputChecker.checkData("2016/09/13/3453"));
    }
    
    @Test
    public void testCheckOra() {
        assertFalse(InputChecker.checkOra("13:45"));
        assertTrue(InputChecker.checkOra("5:30"));
        assertTrue(InputChecker.checkOra("13:3"));
        assertTrue(InputChecker.checkOra("3:4"));
    }
    
    @Test
    public void testCheckPrezzo() {
        assertFalse(InputChecker.checkPrezzo("10.50"));
        assertFalse(InputChecker.checkPrezzo("6.00"));
        assertTrue(InputChecker.checkPrezzo("10,50"));
        assertTrue(InputChecker.checkPrezzo("5,00"));
        assertTrue(InputChecker.checkPrezzo("10.500"));
        assertTrue(InputChecker.checkPrezzo("6.001"));
    }
    
    /*
    @Test
    public void testGetElencoPizzeToHashMap() {
        Pizza margherita = new Pizza(0, "margherita", "blabla", 6.0, true);
        Pizza diavola = new Pizza(1, "diavola", "blabla", 6.0, true);
        Pizza bismark = new Pizza(2, "bismark", "blabla", 6.0, true);
        
        ArrayList<Pizza> elencoPizze = new ArrayList<>();
        elencoPizze.add(margherita);
        elencoPizze.add(diavola);
        elencoPizze.add(bismark);
        elencoPizze.add(margherita);
        elencoPizze.add(diavola);
        elencoPizze.add(margherita);
        
        HashMap<Pizza, Integer> map = new HashMap<>();
        map.put(bismark, 1);
        map.put(diavola, 2);
        map.put(margherita, 3);
        
        assertEquals(map, InputChecker.getElencoPizzeToHashMap(elencoPizze));
    }
    */
}
