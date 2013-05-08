package org.exp.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Fraction {

    private BigInteger top;
    private BigInteger bot;

    public Fraction(long top) {
        this.top = BigInteger.valueOf(top);
        this.bot = BigInteger.ONE;
    }

    public Fraction(long top, long bot) {
        this.top = BigInteger.valueOf(top);
        this.bot = BigInteger.valueOf(bot);
    }

    public Fraction(long whole, long top, long bot) {
        this.top = BigInteger.valueOf(whole).multiply(BigInteger.valueOf(bot)).add(BigInteger.valueOf(top));
        this.bot = BigInteger.valueOf(bot);
    }

    public Fraction(BigInteger top) {
        this.top = top;
        this.bot = BigInteger.ONE;
    }

    public Fraction(BigInteger top, BigInteger bot) {
        this.top = top;
        this.bot = bot;
    }

    public Fraction(BigInteger whole, BigInteger top, BigInteger bot) {
        this.top = whole.multiply(bot).add(top);
        this.bot = bot;
    }

    public Fraction(Fraction inFrac) {
        top = inFrac.getTop();
        bot = inFrac.getBot();
    }

    public String showMixed() {
        BigInteger whole = top.divide(bot);
        BigInteger rTop = top.mod(bot);
        if(rTop.equals(BigInteger.ZERO)){
            return whole.toString();
        }
        return whole.toString() + " " + rTop.toString() + "/" + bot.toString();
    }

    public String showProper() {
        return top.toString() + "/" + bot.toString();
    }

    public BigInteger getTop() {
        return top;
    }

    public void setTop(BigInteger top) {
        this.top = top;
    }

    public BigInteger getBot() {
        return bot;
    }

    public void setBot(BigInteger bot) {
        this.bot = bot;
    }

    @Override
    public String toString() {
        return showMixed();
    }

    public Fraction mul(Fraction b) {
        return mul(this, b);
    }

    public static Fraction mul(Fraction a, Fraction b) {
        BigInteger top = a.getTop().multiply(b.getTop());
        BigInteger bot = a.getBot().multiply(b.getBot());
        Fraction f = new Fraction(top, bot);
        return reduce(f);
    }

    public Fraction add(Fraction b) {
        return add(this, b);
    }

    public static Fraction add(Fraction a, Fraction b) {
        BigInteger top = a.getTop().multiply(b.getBot()).add(a.getBot().multiply(b.getTop()));
        BigInteger bot = a.getBot().multiply(b.getBot());
        return new Fraction(top, bot);
    }

    public Fraction sub(Fraction b) {
        return sub(this, b);
    }

    public static Fraction sub(Fraction a, Fraction b) {
        BigInteger negTop = b.getTop().negate();
        Fraction neg = new Fraction(negTop, b.getBot());
        return add(a, b);
    }

    public Fraction div(Fraction b) {
        return div(this, b);
    }

    public static Fraction div(Fraction a, Fraction b) {
        BigInteger top = a.getTop().multiply(b.getBot());
        BigInteger bot = a.getBot().multiply(b.getTop());
        return reduce(new Fraction(top, bot));
    }

    public boolean equals(Fraction a, Fraction b) {
        Fraction aR = reduce(a);
        Fraction bR = reduce(b);
        if (a.getTop() == b.getTop() && a.getBot() == b.getBot()) {
            return true;
        }
        return false;
    }

    public Fraction reduce() {
        return reduce(this);
    }

    public static Fraction reduce(Fraction frac) {
        List<BigInteger> vals = new ArrayList<BigInteger>();
        vals.add(frac.getTop());
        vals.add(frac.getBot());
        BigInteger gcdVal = BigIntegerUtils.gcd(vals);
        return new Fraction(frac.getTop().divide(gcdVal), frac.getBot().divide(gcdVal));
    }

    public static Fraction parse(String strIn) {
        Fraction frac = null;
        BigInteger[] bi = new BigInteger[3];
        int ni = 0;
        int charLen = strIn.length();
        char[] chars = new char[charLen];
        int i = 0;
        int j = 0;
        strIn.getChars(0, charLen, chars, 0);
        while (i < charLen) {
            if (ni >= 3) {
                throw new RuntimeException("Too many numbers");
            }
            i++;
            if (i >= charLen || chars[i] == ',') {
                if (i - j <= 0) {
                    throw new RuntimeException("Empty number field found");
                }
                String foundStr = new String(chars, j, i - j);
                bi[ni] = new BigInteger(foundStr);
                ni++;
                i++;
                j = i;
            }
        }
        switch (ni) {
            case 0:
                throw new RuntimeException("No numbers parse");
            case 1:
                return new Fraction(bi[0]);

            case 2:
                return new Fraction(bi[0], bi[1]);
            case 3:
                return new Fraction(bi[0], bi[1], bi[2]);
            default:
                throw new RuntimeException("Too many numbers to parse");
        }
    }
}
