package ru.sber.qa

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.*


internal class HrDepartmentTest {
    private val certificateRequest = mockk<CertificateRequest>()
    private val hr = mockkClass(HrDepartment::class)

    @Test
    fun `receiveRequest() should throw WeekendDayException when current day is weekend`() {
        every { hr.clock } returns Clock.fixed(Instant.parse("2021-09-25T10:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.NDFL

        assertThrows<WeekendDayException> { HrDepartment.receiveRequest(certificateRequest) }
    }

    @Test
    fun `receiveRequest() should throw NotAllowReceiveRequestException when certificateType NDFL and day is Tuesday`() {
        every { hr.clock } returns Clock.fixed(Instant.parse("2021-09-28T10:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.NDFL

        assertThrows<NotAllowReceiveRequestException> { HrDepartment.receiveRequest(certificateRequest) }
    }

    @Test
    fun `receiveRequest() should throw NotAllowReceiveRequestException when certificateType LABOUR_BOOK and day is Monday`() {
        every { hr.clock } returns Clock.fixed(Instant.parse("2021-09-27T10:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.LABOUR_BOOK

        assertThrows<NotAllowReceiveRequestException> { HrDepartment.receiveRequest(certificateRequest) }
    }

    @Test
    fun `receiveRequest() should not throw NotAllowReceiveRequestException when certificateType LABOUR_BOOK and day is Tuesday`() {
        every { hr.clock } returns Clock.fixed(Instant.parse("2021-09-28T10:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.LABOUR_BOOK

        assertDoesNotThrow { HrDepartment.receiveRequest(certificateRequest) }
    }

    @Test
    fun `processNextRequest() should not throw NotAllowReceiveRequestException()`() {
        val certificate = mockkClass(Certificate::class)
        val hrEmployeeNumber = 12L

        every { hr.clock } returns Clock.fixed(Instant.parse("2021-09-28T10:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.LABOUR_BOOK
        every { certificateRequest.process(hrEmployeeNumber) } returns certificate

        HrDepartment.receiveRequest(certificateRequest)

        assertDoesNotThrow { HrDepartment.processNextRequest(hrEmployeeNumber) }
    }

    @AfterEach
    fun unmockk() {
        unmockkAll()
    }
}