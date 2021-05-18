package me.hanslovsky.jepyter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PythonTest {
    @Test
    fun `put variable onto CPython interpreter from Kotlin`() {
        val a = "b"
        Python().use { python ->
            python["a"] = a
            assertEquals(a, python["a"])
        }
    }

    @Test fun `create variable in CPython and access from Kotlin`() {
        Python().use { python ->
            python("a = 1 + 2")
            assertEquals(3, python["a", java.lang.Integer::class.java])
        }
    }
}