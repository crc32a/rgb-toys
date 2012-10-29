package org.exp.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimeFactorGenerator {

    private static final int PRIMES_TO_ADD = 1;
    private static final BigInteger TWO = BigInteger.ONE.shiftLeft(1);
    private BigInteger[] primes;

    public PrimeFactorGenerator() {
        primes = new BigInteger[]{TWO};
    }

    public static PrimeFactorGenerator newPrimeFactorGenerator(int n) {
        PrimeFactorGenerator pg = new PrimeFactorGenerator();
        pg.addPrimes(n);
        return pg;
    }

    public BigInteger getLastPrime() {
        return primes[primes.length - 1];
    }

    public void addPrimes(int n) {
        int pl;
        int bp;
        int i;

        if (primes == null || primes.length < 1) {
            primes = new BigInteger[]{TWO};
        }

        pl = primes.length;

        BigInteger[] newPrimes = new BigInteger[pl + n];
        for (bp = 0; bp < pl; bp++) {
            newPrimes[bp] = primes[bp];
        }
        for (i = 0; i < n; i++) {
            newPrimes[bp + i] = newPrimes[bp + i - 1].nextProbablePrime();
        }
        primes = newPrimes;
    }

    public BigInteger[] getPrimes() {
        return primes;
    }

    public void setPrimes(BigInteger[] primes) {
        this.primes = primes;
    }

    public Map<BigInteger,Integer> getPrimeFactorMap(BigInteger n){
        Map<BigInteger,Integer> out = new HashMap<BigInteger, Integer>();
        List<PrimeRoot> rootList = getPrimeFactors(n);
        for(PrimeRoot primeRoot : rootList){
            int exponent = primeRoot.getExponent();
            BigInteger prime = primeRoot.getPrimeRoot();
            out.put(prime,exponent);
        }
        return out;
    }

    public List<PrimeRoot> getPrimeFactors(BigInteger n) {
        BigInteger one = BigInteger.ONE;
        List<PrimeRoot> primeRootList = new ArrayList<PrimeRoot>();
        boolean alreadyAddedRootPrime;
        String wtf;
        BigInteger curr = n.add(BigInteger.ZERO);
        int pi = 0;
        while (curr.compareTo(BigInteger.ONE) == 1) {
            if (pi + 1 > primes.length) {
                addPrimes(PRIMES_TO_ADD);
            }
            BigInteger prime = primes[pi];
            PrimeRoot primeRoot = new PrimeRoot(prime);
            alreadyAddedRootPrime = false;
            while (curr.mod(prime).compareTo(BigInteger.ZERO) == 0) {
                curr = curr.divide(prime);
                primeRoot.incExponent();
                if (!alreadyAddedRootPrime) {
                    primeRootList.add(primeRoot);
                    alreadyAddedRootPrime = true;
                }
            }
            pi++;
       }
        return primeRootList;
    }
}
