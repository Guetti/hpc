/*
 * Copyright (c) 2022 Gustavo Szigethi Araya.
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

import java.util.concurrent.TimeUnit;

import static cl.ucn.disc.hpc.SecuencialPrimes.isPrime;

@Slf4j
public class FindPrimesThread extends Thread{
    private final int thread;
    private final long from;
    private final long to;
    private long primes = 0;

    public FindPrimesThread(long from, long to, int thread){
        this.thread = thread;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run(){
        log.debug("Using Thread: {}", thread);
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
        log.info("Thread {} Found {} primes in {} ms", thread, String.format("%,d", primes), String.format("%,d", millis));

        log.debug("Done.");
    }

    public long getPrimes(){
        return this.primes;
    }
}
