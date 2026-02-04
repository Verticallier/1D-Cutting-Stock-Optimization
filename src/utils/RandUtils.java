package utils;

import java.security.SecureRandom;
import java.util.Random;

public class RandUtils {
    static Random rng = new SecureRandom();


    public static boolean rollDice(double p) {
        int max = 100000;

        return rng.nextInt(max)< p*max;
    }
    public static int randInt(int bound) {
        return rng.nextInt(bound);
    }
}
