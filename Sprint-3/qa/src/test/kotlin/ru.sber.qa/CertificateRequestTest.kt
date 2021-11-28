package ru.sber.qa

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CertificateRequestTest {
    private val certificateType = mockk<CertificateType>()
    private val certificateRequest = CertificateRequest(20L, certificateType)

    @Test
    fun `employeeNumber should be same`() {
        val expected = 20L
        val actual = certificateRequest.employeeNumber

        assertEquals(expected, actual)
    }

    @Test
    fun `certificateType should be same`() {
        val expected = certificateType
        val actual = certificateRequest.certificateType

        assertEquals(expected, actual)
    }

    @Test
    fun `process() should put correct data in Certificate`() {
        mockkObject(Scanner)
        every { Scanner.getScanData() } returns byteArrayOf(1, 2, 3)

        val certificate = certificateRequest.process(23L)

        verify { Scanner.getScanData() }


        val expectedCertificateRequest = this.certificateRequest
        val actualCertificateRequest = certificate.certificateRequest
        assertEquals(expectedCertificateRequest, actualCertificateRequest)

        val expectedHrNumber = 23L
        val actualHrNumber = certificate.processedBy
        assertEquals(expectedHrNumber, actualHrNumber)

        val expectedData = byteArrayOf(1, 2, 3)
        val actualData = certificate.data
        assertArrayEquals(expectedData, actualData)

    }


    @AfterEach
    fun unmockk() {
        unmockkAll()
    }
}
