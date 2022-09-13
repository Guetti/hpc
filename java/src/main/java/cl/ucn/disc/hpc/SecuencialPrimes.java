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

        // Testing from 2 to n-1
        for (long i = 2; i < n; i++) {

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

        // Run the code
        long from = 1;
        long to = 500 * 1000;
        long primes = 0;

        log.info("Finding Primes from {} to {} ..", from, String.format("%,d", to));

        long start = System.nanoTime();
        for (long k = from; k <= to; k++) {
            if (isPrime(k)) {
                primes++;
            }
        }
        long end = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
        log.info("Founded {} primes in {}ms.", String.format("%,d", primes), millis);

        log.debug("Done.");

    }

}
