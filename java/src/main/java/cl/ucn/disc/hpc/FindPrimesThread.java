package cl.ucn.disc.hpc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static cl.ucn.disc.hpc.SecuencialPrimes.isPrime;

@Slf4j
public class FindPrimesThread extends Thread{
    private int thread;
    private long from;
    private long to;
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
