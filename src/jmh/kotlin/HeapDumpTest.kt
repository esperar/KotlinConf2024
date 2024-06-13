package example

import com.sun.management.HotSpotDiagnosticMXBean
import org.junit.jupiter.api.Test
import java.lang.management.ManagementFactory

class LengthRegular(
    val meters: Double
) {
    fun toCm(): Double = meters * 100
    fun toKm(): Double = meters / 1000

    companion object {
        fun fromCm(cm: Double): LengthRegular = LengthRegular(cm / 100)
        fun fromKm(km: Double): LengthRegular = LengthRegular(km * 1000)
    }
}

@JvmInline
value class LengthValue(
    val meters: Double
) {
    fun toCm(): Double = meters * 100
    fun toKm(): Double = meters / 1000

    companion object {
        fun fromCm(cm: Double): LengthValue = LengthValue(cm / 100)
        fun fromKm(km: Double): LengthValue = LengthValue(km * 1000)
    }
}

fun main() {
    testMemoryValueClassArray()
}


// list
fun testMemoryRegularClassList() {
    val instances = mutableListOf<LengthRegular>()
    (1..10_000_000).forEach {
        instances.add(LengthRegular(it + 0.0))
    }
    createHeapDump("RegularClass in List")
}

fun testMemoryValueClassList() {
    val instances = mutableListOf<LengthValue>()
    (1..10_000_000).forEach {
        instances.add(LengthValue(it + 0.0))
    }
    createHeapDump("ValueClass in List")
}

// array
fun testMemoryRegularClassArray() {
    (0..10_000_000).forEach {
        LengthRegular(it + 0.0)
    }
    createHeapDump("RegularClass")
}

fun testMemoryValueClassArray() {
    (0..10_000_000).forEach {
        LengthValue(it + 0.0)
    }
    createHeapDump("ValueClass")
}


fun createHeapDump(key: String = "") {
    val path = "./src/main/resources"
    val liveOnly = false
    val server = ManagementFactory.getPlatformMBeanServer()
    try {
        val mxBean = ManagementFactory.newPlatformMXBeanProxy(
            server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean::class.java
        )
        mxBean.dumpHeap("$path/heapdump-$key-${System.currentTimeMillis()}.hprof", liveOnly)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
