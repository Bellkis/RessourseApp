package org.group2.iatic.ihm.utils;

import java.util.Random;

public class Generators {
    public static int generateRandomIntBasedOnTime() {
        long currentTime = System.currentTimeMillis();
        Random random = new Random(currentTime);

        // Adjust the range as needed
        int minRange = 1;
        int maxRange = 100;

        return random.nextInt(maxRange - minRange + 1) + minRange;
    }
}
