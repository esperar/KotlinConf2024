package example

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(2)
open class BenchmarkTests {

    @Benchmark
    fun testCreateRegularClass() {
        val instances = mutableListOf<RegularClass>()
        (1..10_000_000).forEach {
            instances.add(RegularClass(it))
        }
    }

    @Benchmark
    fun testCreateValueClass() {
        val instances = mutableListOf<ValueClass>()
        (1..10_000_000).forEach {
            instances.add(ValueClass(it))
        }
    }

    @Benchmark
    fun testSumRegularClass() {
        var sum = 0L
        (1..10_000_000).forEach {
            val obj = RegularClass(it)
            sum += obj.number
        }
    }

    @Benchmark
    fun testSumValueClass() {
        var sum = 0L
        (1..10_000_000).forEach {
            val obj = ValueClass(it)
            sum += obj.number
        }
    }
}

class RegularClass(val number: Int)

@JvmInline
value class ValueClass(val number: Int)