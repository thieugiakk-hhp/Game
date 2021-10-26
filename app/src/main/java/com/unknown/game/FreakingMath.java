package com.unknown.game;

import android.util.Log;

import java.util.Random;

public class FreakingMath {
    final static int MAX = 100;

    final static int EQUAL = 0;
    final static int BIGGER = 1;
    final static int SMALLER = 2;

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isExactly() {
        return exactly;
    }

    public void setExactly(boolean exactly) {
        this.exactly = exactly;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    private int n1;
    private int n2;
    private String sign;
    private boolean result;
    private boolean exactly;

    public FreakingMath() {
    }

    public FreakingMath(int n1, int n2, String sign) {
        this.n1 = n1;
        this.n2 = n2;
        this.sign = sign;
    }

    public static FreakingMath randomFreakingMath() {
        FreakingMath freakingMath = new FreakingMath();

        Random random = new Random();

        int n1 = random.nextInt(MAX);
        int n2 = random.nextInt(MAX);
        int intSign = random.nextInt(3);
        String sign = "=";
        boolean result = true;

        switch (intSign) {
            case 0:
                sign = " = ";
                if (n1 == n2) {
                    result = true;
                }
                else result = false;
                Log.e("sign", " = " + result);
                break;
            case 1:
                sign = " > ";
                if (n1 > n2) {
                    result = true;
                }
                else result = false;
                Log.e("sign", " > " + result);
                break;
            case 2:
                sign = " < ";
                if (n1 < n2) {
                    result = true;
                }
                else result = false;
                Log.e("sign", " < " + result);
                break;
        }

        freakingMath.setN1(n1);
        freakingMath.setN2(n2);
        freakingMath.setSign(sign);
        freakingMath.setResult(result);

        return freakingMath;
    }
}
