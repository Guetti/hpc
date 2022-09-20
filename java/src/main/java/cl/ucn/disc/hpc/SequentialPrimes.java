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

/**
 * The Parallel Primes to show the speedup.
 *
 * @author Diego Urrutia-Astorga.
 * @version 0.0.1
 */
@Slf4j
public final class SequentialPrimes {

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
    public static void main(String[] args) {

        log.debug("Starting Main ..");

        // System info
        SystemInfo si = new SystemInfo();
        int realCores = si.getHardware().getProcessor().getPhysicalProcessorCount();
        log.debug("Detected {} real cores.", realCores);

        int logicalCores = si.getHardware().getProcessor().getLogicalProcessorCount();
        log.debug("Detected {} logical cores.", logicalCores);

        // Configuration
        final long from = 1;
        final long to = 1000 * 500 * 500; // ~ 29 seconds.

        final long numbersPerCore = to / logicalCores;

        final long rest = to % logicalCores;

        // Calculate how many numbers can a single core process.
        log.info("We can perform {} numbers per core", numbersPerCore);

        log.info("{} Numbers left over, add to the final thread.", rest);

        log.info("Finding Primes from {} to {} ..", from, String.format("%,d", to));

        // Declare the threads..
        FindPrimesThread[] threads = new FindPrimesThread[logicalCores];

        // Declare and initialize from where to start finding primes.
        long counter = from;

        for (int i = 0; i < logicalCores; i++){
            // Initialize the thread.
            if (i == logicalCores)
                threads[i] = new FindPrimesThread(counter, counter + numbersPerCore + rest, i + 1);

            else
                threads[i] = new FindPrimesThread(counter, counter + numbersPerCore, i + 1);
            // Add to the counter the next start position.
            counter += numbersPerCore + 1;
            // Start counting primes.
            threads[i].start();
        }

        // Save the amount of primes.
        long primes = 0;

        for (int i = 0; i < logicalCores; i++){
            try{
                // Try to join the indexed thread.
                threads[i].join();

                // Add the amount of primes that the core found.
                primes += threads[i].getPrimes();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        // Show the info.
        log.info("Total primes: {}", String.format("%,d", primes));
    }


}
