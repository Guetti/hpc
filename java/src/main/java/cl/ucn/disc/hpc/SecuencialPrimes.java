/*
 * Copyright (c) 2019 Diego Urrutia-Astorga http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package cl.ucn.disc.hpc;

import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;

import java.util.concurrent.TimeUnit;

/**
 * The Parallel Primes to show the speedup.
 *
 * @author Diego Urrutia-Astorga.
 * @version 0.0.1
 */
@Slf4j
public final class SecuencialPrimes {

    /**
     * Function to test primality.
     *
     * @param n to prime test.
     * @return true is n is prime.
     */
    public static boolean isPrime(final long n) {

        // Can't process negative numbers.
        if (n <= 0) {
            throw new IllegalArgumentException("Error in n: Can't process negative numbers");
        }

        // One isn't prime!
        if (n == 1) {
            return false;
        }

        // Two is prime!
        if (n == 2) {
            return true;
        }

        // Even numbers aren't prime!
        if (n % 2 == 0) {
            return false;
        }

        // Testing from 3 to sqrt(n)
        long max = (long) Math.sqrt(n);
        for (long i = 3; i <= max; i += 2) {
            // If module == 0 -> not prime!
            if (n % i == 0) {
                return false;
            }
        }

        return true;

    }

    /**
     * The Main!
     *
     * @param args to use.
     */
    public static void main(String[] args) throws InterruptedException {

        log.debug("Starting Main ..");

        // System info
        SystemInfo si = new SystemInfo();
        int realCores = si.getHardware().getProcessor().getPhysicalProcessorCount();
        log.debug("Detected {} real cores.", realCores);

        int logicalCores = si.getHardware().getProcessor().getLogicalProcessorCount();
        log.debug("Detected {} logical cores.", logicalCores);

        // Configuration
        final long from = 1;
        final long to = 1000 * 1000 * 1000;
        long primes = 0;

        log.info("Finding Primes from {} to {} ..", from, String.format("%,d", to));

        // Timer
        long start = System.nanoTime();

        // Loop for check
        for (long k = from; k <= to; k++) {
            // Show some %
            if (k % 1000000 == 0) {
                log.debug("{}% -> {}", String.format("%.1f", 100 * (double) k / to), String.format("%,d", k));
            }

            // Count if prime
            if (isPrime(k)) {
                primes++;
            }
        }

        // How long?
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        log.info("Founded {} primes in {} ms", String.format("%,d", primes), String.format("%,d", millis));

        log.debug("Done.");

    }

}
