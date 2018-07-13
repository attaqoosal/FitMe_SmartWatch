package com.test.fitme;

public class Calculation {
    public static final double ONE_MILE = 1.609344; //1마일 = 1.609344 km/h

    public static double dist(int height, int count) {
        return ((height - 100) * count) / 100.0; //미터
    }

    public static double kcalPerMile(int weight) {
       return 3.7103 + (0.2678 * weight) + (0.0359 * (weight * 60 * 0.0006213) * 2) * weight;
    }

    public static int walk_kcal(int height, int weight, int count) {
        return (int)(dist(height, count) * kcalPerMile(weight) * 0.0006213 * 2.6);
    }

    public static int run_kcal(int height, int weight, int count) {
        return (int)(dist(height, count) * kcalPerMile(weight) * 0.0006213 * 2.6 * 2.4);
    }

    public static int jumpRope_kcal(int count) {
        return (int)(count * 0.2);
    }

    public static int situp_kcal(int count) {
        return count;
    }
}
