package ru.sber.qa

import io.mockk.mockkClass
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CertificateTest {
    private val certificateRequest = mockkClass(CertificateRequest::class)
    private val certificate = Certificate(certificateRequest, 5L, byteArrayOf(1, 2, 3))

    @Test
    fun `certificateRequest should be same`() {
        val expected = this.certificateRequest
        val actual = certificate.certificateRequest

        assertEquals(expected, actual)
    }

    @Test
    fun `processedBy should be same`() {
        val expected = 5L
        val actual = certificate.processedBy

        assertEquals(expected, actual)
    }

    @Test
    fun `data should be same`() {
        val expected = byteArrayOf(1, 2, 3)
        val actual = certificate.data

        assertArrayEquals(expected, actual)
    }

    @AfterEach
    fun unmock(){
        unmockkAll()
    }
}
