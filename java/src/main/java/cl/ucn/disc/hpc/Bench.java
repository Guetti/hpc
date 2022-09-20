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
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;

/**
 * Benchmark example.
 */
@Slf4j
public class Bench {

    /**
     * The Main.
     */
    public static void main(String[] args) throws IOException {
        Main.main(args);
    }

    /**
     * The Benchmark.
     *
     * @param iteration to use.
     */
    @Benchmark
    @BenchmarkMode(Mode.All)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmarkPrime(Blackhole blackhole, Iteration iteration) {
        blackhole.consume(SequentialPrimes.isPrime(iteration.values));
    }

    /**
     * The State.
     */
    @State(Scope.Benchmark)
    public static class Iteration {

        @Param({"1500450271",
                "2860486313",
                "3267000013",
                "3367900313",
                "3628273133",
                "4093082899",
                "5463458053",
                "5754853343",
                "5915587277",
                "9576890767"})
        public long values;

    }

}
