package org.exp.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BigIntegerUtils {

    private static PrimeFactorGenerator primeFactorGenerator;

    static {
        primeFactorGenerator = new PrimeFactorGenerator();
    }

    public static List<BigInteger> getBigIntegerList(String strIn) throws RuntimeException {
        List<BigInteger> bigInts = new ArrayList<BigInteger>();
        String[] strSplit = strIn.split(",");
        for (int i = 0; i < strSplit.length; i++) {
            String trimedNumber = strSplit[i].trim();
            if (trimedNumber.equals("")) {
                continue; // Don't consider blanks
            }
            BigInteger bi;
            bi = new BigInteger(trimedNumber);
            bigInts.add(bi);
        }
        return bigInts;
    }

    public static List<BigInteger> getPrimes(int n) {
        int currPrimeCount = primeFactorGenerator.getPrimes().length;
        if (currPrimeCount < n) {
            primeFactorGenerator.addPrimes(n - currPrimeCount);
        }
        List<BigInteger> primeList = new ArrayList<BigInteger>();
        BigInteger[] primeArray = primeFactorGenerator.getPrimes();
        for (int i = 0; i < n; i++) {
            primeList.add(primeArray[i]);
        }
        return primeList;
    }

    public static String listToString(List<?> objs) {
        StringBuilder sb = new StringBuilder();
        int listSize = objs.size();
        sb.append("{ ");
        for (int i = 0; i < listSize - 1; i++) {
            sb.append(objs.get(i).toString()).append(", ");
        }
        sb.append(objs.get(listSize - 1).toString()).append(" }");
        return sb.toString();
    }

    private static BigInteger[] toArray(List<BigInteger> bigIntsIn) {
        BigInteger[] ba = new BigInteger[bigIntsIn.size()];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = bigIntsIn.get(i);
        }
        return ba;
    }

    public static BigInteger composePrimeFactorMap(Map<BigInteger, Integer> mapIn) {
        BigInteger out = BigInteger.ONE;
        for (Entry<BigInteger, Integer> ent : mapIn.entrySet()) {
            BigInteger prime = ent.getKey();
            int exponent = ent.getValue().intValue();
            out = out.multiply(prime.pow(exponent));
        }
        return out;
    }

    public static List<PrimeRoot> primeFactorSetToList(Map<BigInteger, Integer> primeFactorSet) {
        List<PrimeRoot> roots = new ArrayList<PrimeRoot>();
        List<BigInteger> keys = new ArrayList<BigInteger>(primeFactorSet.keySet());
        Collections.sort(keys);
        for (BigInteger key : keys) {
            roots.add(new PrimeRoot(key, primeFactorSet.get(key)));
        }
        return roots;
    }

    public static Map<BigInteger, Integer> filterPrimeFactorSet(List<Map<BigInteger, Integer>> pMapList, BigIntegerOperation op) {
        List<BigInteger> primes = sortPrimeFactorKeys(pMapList);
        Map<BigInteger, Integer> pMap = new HashMap<BigInteger, Integer>();
        List<BigInteger> keys = sortPrimeFactorKeys(pMapList);
        for (BigInteger prime : keys) {
            int exp;
            switch (op) {
                case MAX:
                    exp = maxPrimeExponentFromMap(prime, pMapList);
                    if (exp > 0) {
                        pMap.put(prime, exp);
                    }
                    break;
                case MIN:
                    exp = minPrimeExponentFromMap(prime, pMapList);
                    if (exp > 0) {
                        pMap.put(prime, exp);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Illegal Value for BigIntegerOperation");
            }
        }
        return pMap;
    }

    private static int maxPrimeExponentFromMap(BigInteger prime, List<Map<BigInteger, Integer>> pMapList) {
        int maxExp = 0;
        for (Map<BigInteger, Integer> pMap : pMapList) {
            if (!pMap.containsKey(prime)) {
                continue;
            }
            if (pMap.get(prime) > maxExp) {
                maxExp = pMap.get(prime);
            }
        }
        return maxExp;
    }

    private static int minPrimeExponentFromMap(BigInteger prime, List<Map<BigInteger, Integer>> pMapList) {
        Integer minExp = null;
        for (Map<BigInteger, Integer> pMap : pMapList) {
            if (!pMap.containsKey(prime)) {
                return 0;
            }
            if (minExp == null || pMap.get(prime) < minExp) {
                minExp = pMap.get(prime);
            }
        }
        if (minExp == null) {
            return 0;
        }
        return minExp;
    }

    private static List<BigInteger> sortPrimeFactorKeys(List<Map<BigInteger, Integer>> primeMapList) {
        Set<BigInteger> keySet = new HashSet<BigInteger>();
        for (Map<BigInteger, Integer> primeMap : primeMapList) {
            keySet.addAll(primeMap.keySet());
        }
        List<BigInteger> keys = new ArrayList<BigInteger>(keySet);
        Collections.sort(keys);
        return keys;
    }

    public static BigInteger gcd(List<BigInteger> bigIntsIn) {
        BigInteger out = BigInteger.ONE;
        List<Map<BigInteger, Integer>> primeFactorMapList = new ArrayList<Map<BigInteger, Integer>>();
        for (BigInteger bigInt : bigIntsIn) {
            Map<BigInteger, Integer> primeFactorMap = primeFactorGenerator.getPrimeFactorMap(bigInt);
            primeFactorMapList.add(primeFactorMap);
        }
        Map<BigInteger, Integer> filteredMap = filterPrimeFactorSet(primeFactorMapList, BigIntegerOperation.MIN);
        out = composePrimeFactorMap(filteredMap);

        return out;
    }

    public static BigInteger lcm(List<BigInteger> bigIntsIn) {
        BigInteger out = BigInteger.ONE;
        List<Map<BigInteger, Integer>> primeFactorMapList = new ArrayList<Map<BigInteger, Integer>>();
        for (BigInteger bigInt : bigIntsIn) {
            Map<BigInteger, Integer> primeFactorMap = primeFactorGenerator.getPrimeFactorMap(bigInt);
            primeFactorMapList.add(primeFactorMap);
        }
        Map<BigInteger, Integer> filteredMap = filterPrimeFactorSet(primeFactorMapList, BigIntegerOperation.MAX);
        out = composePrimeFactorMap(filteredMap);

        return out;
    }

    public static PrimeFactorGenerator getPrimeFactorGenerator() {
        return primeFactorGenerator;
    }

    public static void setPrimeFactorGenerator(PrimeFactorGenerator aPfg) {
        primeFactorGenerator = aPfg;
    }
}
