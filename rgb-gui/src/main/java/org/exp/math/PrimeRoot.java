package org.exp.math;

import java.math.BigInteger;

public class PrimeRoot implements Comparable<BigInteger> {

    private BigInteger primeRoot;
    private int exponent;

    public PrimeRoot() {
    }

    public PrimeRoot(BigInteger primeRoot){
        this.primeRoot = primeRoot;
        exponent=0;
    }

    public PrimeRoot(BigInteger primeRoot, int exponent) {
        this.primeRoot = primeRoot;
        this.exponent = exponent;
    }

    public PrimeRoot(PrimeRoot orgPrimeRoot){
        primeRoot = orgPrimeRoot.getPrimeRoot();
        exponent = orgPrimeRoot.getExponent();
    }

    public BigInteger getPrimeRoot() {
        return primeRoot;
    }

    public void setPrimeRoot(BigInteger primeRoot) {
        this.primeRoot = primeRoot;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public void incExponent(){
        this.exponent++;
    }

    // Used to sort a list of PrimeRoots and order by the actual root
    @Override
    public int compareTo(BigInteger o) {
        return primeRoot.compareTo(o);
    }

    @Override
    public String toString(){
        return String.format("%s^%d",primeRoot.toString(),exponent);
    }
}
