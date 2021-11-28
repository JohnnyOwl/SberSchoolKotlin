package com.danya.springmvc

import com.danya.springmvc.models.PersonPage
import com.danya.springmvc.service.AddressBookService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.concurrent.ConcurrentHashMap


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestControllerIntegrationTests {

    private val headers = HttpHeaders()

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var addressBookService: AddressBookService

    @BeforeEach
    fun setUp(){
        headers.add("Cookie", logging())

        addressBookService.addPage(PersonPage("John", "lennon@abbeyroad.com"))
        addressBookService.addPage(PersonPage("Paul", "mccarntey@sumbarine.org"))
        addressBookService.addPage(PersonPage("George", "harrison@letitbe.ru"))
    }

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    private fun logging(): String? {
        val request: MultiValueMap<String, String> = LinkedMultiValueMap()
        request.set("login", "user")
        request.set("password", "user")

        val response = restTemplate.postForEntity(url("login"), HttpEntity(request, HttpHeaders()), String::class.java)

        return response!!.headers["Set-Cookie"]!![0]
    }

    @ParameterizedTest
    @MethodSource("different pages")
    fun `should correct add notes`(personPage: PersonPage) {
        val response = restTemplate.exchange(
            url("api/add"),
            HttpMethod.POST,
            HttpEntity(personPage, headers),
            PersonPage::class.java
        )

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(personPage.name, response.body!!.name)
    }

    @ParameterizedTest
    @MethodSource("different id")
    fun `should correct get notes`(id: Int) {
        val response = restTemplate.exchange(
            url("api/$id/view"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            ConcurrentHashMap::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
    }

    @ParameterizedTest
    @MethodSource("different id")
    fun `should return list of notes with conditional`(id: Int) {
        val response = restTemplate.exchange(
            url("api/list"),
            HttpMethod.POST,
            HttpEntity(mapOf("id" to id.toString()), headers),
            ConcurrentHashMap::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
    }

    @Test
    fun `should return list`() {
        val response = restTemplate.exchange(
            url("api/list"),
            HttpMethod.POST,
            HttpEntity(emptyMap<String, String>(), headers),
            ConcurrentHashMap::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
    }

    @ParameterizedTest
    @MethodSource("id for edit page model")
    fun `should edit notes with id`(id: Int, personPage: PersonPage) {
        val response = restTemplate.exchange(
            url("api/$id/edit"),
            HttpMethod.PUT,
            HttpEntity(personPage, headers),
            ConcurrentHashMap::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
    }

    companion object {
        @JvmStatic
        fun `different pages`() = listOf(
            PersonPage("John", "lennon@abbeyroad.com"),
            PersonPage("Paul", "mccarntey@sumbarine.org"),
            PersonPage("George", "harrison@letitbe.ru")
        )

        @JvmStatic
        fun `different id`() = listOf(
            2, 0, 1
        )

        @JvmStatic
        fun `id for edit page model`() = listOf(
            Arguments.of(1, PersonPage("John", "lennon@abbeyroad.com")),
            Arguments.of(0, PersonPage("Paul", "mccarntey@sumbarine.org")),
            Arguments.of(2, PersonPage("George", "harrison@letitbe.ru"))
        )
    }
}