package com.danya.springmvc

import com.danya.springmvc.models.PersonPage
import com.danya.springmvc.service.AddressBookService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import java.util.concurrent.ConcurrentHashMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityRestTests(@LocalServerPort var port: Int) {

    private val headers = HttpHeaders()

    @Autowired
    private lateinit var addressBookService: AddressBookService

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val fullURI = "http://localhost:$port/"

    private fun getCookie(
        username: String,
        password: String,
        loginURI: String = "$fullURI/login"
    ): String {

        val form = LinkedMultiValueMap<String, String>()
        form.set("username", username)
        form.set("password", password)

        val loginResponse = restTemplate
            .postForEntity(
                loginURI,
                HttpEntity(form, HttpHeaders()),
                String::class.java
            )

        return loginResponse.headers["Set-Cookie"]!![0]
    }

    @BeforeEach
    fun setup() {
        val cookie = getCookie("admin", "admin")
        headers.add("Cookie", cookie)

        addressBookService.addPage(PersonPage("John", "lennon@abberyroad.com"))
        addressBookService.addPage(PersonPage("Paul", "mccartney@submarine.org"))
        addressBookService.addPage(PersonPage("George", "harrison@letitbe.ru"))
    }

    @ParameterizedTest
    @MethodSource("different pages")
    fun `should add page with authorities = API_ROLE`(page: PersonPage) {
        val url = "$fullURI/api/add"

        val response = restTemplate
            .exchange(
                url,
                HttpMethod.POST,
                HttpEntity(page, headers),
                PersonPage::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertTrue(response.body!! == page)
    }

    @Test
    fun `should get list of pages`() {
        val map = emptyMap<String, String>()

        val url = "$fullURI/api/list"

        val response = restTemplate.exchange(
            url, HttpMethod.POST,
            HttpEntity(map, headers),
            Map::class.java)

        assertEquals(response.statusCode, HttpStatus.OK)
        assertNotNull(response.body)
    }

    @ParameterizedTest
    @MethodSource("different pages")
    fun `should update page`(page: PersonPage) {
        val url = "$fullURI/api/0/edit"

        val response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            HttpEntity(page, headers),
            PersonPage::class.java, 1)

        assertNotNull(response.body)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(page.name, response.body!!.name)
        assertEquals(page.address, response.body!!.address)
    }

    @ParameterizedTest
    @MethodSource("different id")
    fun `should delete address`(id: Int) {
        val URL = "$fullURI/api/$id/delete"

        val response = restTemplate.exchange(
            URL,
            HttpMethod.DELETE,
            HttpEntity(null, headers),
            PersonPage::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `should return page with id = 0`() {
        val URL = "$fullURI/api/0/view"

        val response = restTemplate.exchange(
            URL,
            HttpMethod.GET,
            HttpEntity(null, headers),
            ConcurrentHashMap::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    companion object {
        @JvmStatic
        fun `different pages`() = listOf(
            PersonPage("John", "lennon@abberyroad.com"),
            PersonPage("Paul", "mccartney@submarine.org"),
            PersonPage("George", "harrison@letitbe.ru")
        )
        @JvmStatic
        fun `different id`() = listOf(
            2, 0, 1
        )
    }
}