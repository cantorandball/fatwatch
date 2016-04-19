package com.candb.josh.protochi_wear;

import junit.framework.TestCase;

/**
 * Created by josh on 19/04/2016.
 */
public class CounterFragmentTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }

    public void testNewInstance() throws Exception {
    }

    public void testOnCreateView() throws Exception {

    }

    public void testOnResume() throws Exception {

    }

    public void testOnPause() throws Exception {

    }

    public void testResetValues() throws Exception {
    }

    public void testTwoDecimalPlaces() throws Exception {

    }

    public void testGetHighestAccel() throws Exception {
        CounterFragment testCounter = new CounterFragment();
        testCounter.currentMaxAccel = 65.8;
        // Set the accel above that and check it's changed
        testCounter.getHighestAccel(89.0);
        assertEquals(testCounter.currentMaxAccel, 89.0);
        //And check a lower value doesn't change it
        testCounter.getHighestAccel(0.0);
        assertEquals(testCounter.currentMaxAccel, 89.0);
        testCounter.getHighestAccel(7.8);
        assertEquals(testCounter.currentMaxAccel, 89.0);
        testCounter.getHighestAccel(-90.0);
        assertEquals(testCounter.currentMaxAccel, 89.0);
    }

    public void testDisplayValues() throws Exception {

    }

    public void testAccelInitialisedAtZero() throws Exception {
        CounterFragment testCounter = new CounterFragment();
        assertEquals(testCounter.currentMaxAccel, 0.0);
    }

}