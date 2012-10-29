/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.exp.math;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BigIntegerUtilsTest {

    public BigIntegerUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLcm() {
        PrimeFactorGenerator pfg = PrimeFactorGenerator.newPrimeFactorGenerator(10);
        List<BigInteger> bList;
        BigInteger exp;

        bList = BigIntegerUtils.getBigIntegerList("2940,12600,1260");
        exp = new BigInteger("88200");
        assertEquals(exp, BigIntegerUtils.lcm(pfg, bList));

        bList = BigIntegerUtils.getBigIntegerList("6,75,18");
        exp = new BigInteger("450");
        assertEquals(exp, BigIntegerUtils.lcm(pfg, bList));

        bList = BigIntegerUtils.getBigIntegerList("30,20,50");
        exp = new BigInteger("300");
        assertEquals(exp, BigIntegerUtils.lcm(pfg, bList));

        bList = BigIntegerUtils.getBigIntegerList("60,105,3150");
        exp = new BigInteger("6300");

        bList = BigIntegerUtils.getBigIntegerList("2940,12600,1260");
        exp = new BigInteger("88200");
        assertEquals(exp, BigIntegerUtils.lcm(pfg, bList));

    }

    @Test
    public void testGcd() {
        PrimeFactorGenerator pfg = PrimeFactorGenerator.newPrimeFactorGenerator(10);
        List<BigInteger> bList;
        BigInteger exp;

        bList = BigIntegerUtils.getBigIntegerList("6,75");
        exp = new BigInteger("3");
        assertEquals(exp, BigIntegerUtils.gcd(pfg, bList));

        bList = BigIntegerUtils.getBigIntegerList("60,105,3150");
        exp = new BigInteger("15");
        assertEquals(exp, BigIntegerUtils.gcd(pfg, bList));

        bList = BigIntegerUtils.getBigIntegerList("2940,12600,1260");
        exp = new BigInteger("420");
        assertEquals(exp, BigIntegerUtils.gcd(pfg, bList));


    }
}
