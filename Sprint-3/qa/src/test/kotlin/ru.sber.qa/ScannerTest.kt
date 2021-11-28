package ru.sber.qa

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

internal class ScannerTest {

    @Test
    fun `getScanData() should throw ScanTimeoutException when scan duration is too long`() {
        mockkObject(Random)

        every { Random.nextLong(5000L, 15000L) } returns 20_000L

        assertThrows<ScanTimeoutException> { Scanner.getScanData() }
    }

    @Test
    fun `data should be same`() {
        val expected = Random.nextBytes(100)

        mockkObject(Random)

        every { Random.nextLong(5000L, 15000L) } returns 5000L
        every { Random.nextBytes(100) } returns expected

        val actual = Scanner.getScanData()

        assertArrayEquals(expected, actual)
    }

    @AfterEach
    fun unmockk() {
        unmockkAll()
    }
}