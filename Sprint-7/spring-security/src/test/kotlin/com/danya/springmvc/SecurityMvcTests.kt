package com.danya.springmvc

import com.danya.springmvc.models.PersonPage
import com.danya.springmvc.service.AddressBookService
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@SpringBootTest
@AutoConfigureMockMvc
class SecurityMvcTests {

    @Autowired
    private val context: WebApplicationContext? = null

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var addressBookService: AddressBookService

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context!!)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()

        addressBookService.addPage(PersonPage("John", "lennon@abberyroad.com"))
        addressBookService.addPage(PersonPage("Paul", "mccartney@submarine.org"))
        addressBookService.addPage(PersonPage("George", "harrison@letitbe.ru"))
    }

    @WithMockUser(username = "app", password = "app", authorities = ["APP_ROLE"])
    @Test
    fun `should succesfull return page for user with authorities = APP_ROLE`() {
        mockMvc.perform(get("/app/list"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @WithMockUser(username = "app", password = "app", authorities = ["ADMIN"])
    @Test
    fun `should succesfull return page for user with authorities = ADMIN`() {
        mockMvc.perform(get("/app/list"))
            .andDo(print())
            .andExpect(status().isOk)

        mockMvc.perform(get("/api/list"))
            .andDo(print())
            .andExpect(status().is4xxClientError)

    }

    @WithMockUser(username = "app", password = "app", authorities = ["APP_ROLE"])
    @Test
    fun `should return 403 error for user with authorities = APP_ROLE`() {
        mockMvc.perform(get("/api/list"))
            .andDo(print())
            .andExpect(status().is4xxClientError)

    }

    @WithMockUser(username = "api", password = "app", authorities = ["API_ROLE"])
    @Test
    fun `should return 403 error for user with authorities = API_ROLE`() {
        mockMvc.perform(get("/app/list"))
            .andDo(print())
            .andExpect(status().is4xxClientError)

    }

    @WithMockUser(username = "app", password = "app", authorities = ["APP_ROLE"])
    @Test
    fun `should correct add page with authorities = APP_ROLE`() {
        val page: MultiValueMap<String, String> = LinkedMultiValueMap()

        page.add("name", "John")
        page.add("address", "lennon@abbeyroad.com")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .params(page))
            .andExpect(status().isOk)
            .andExpect(view().name("menuPage"))
            .andExpect(content().string(StringContains.containsString("Page was added")))
    }


    companion object {
        @JvmStatic
        fun `different id`() = listOf(
            2, 0, 1
        )
    }
}