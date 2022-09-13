/*
 * Copyright (c) 2022 Diego Urrutia-Astorga.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
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
        final long to = 1000 * 1000 * 1000; // ~ 25 mins.
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
