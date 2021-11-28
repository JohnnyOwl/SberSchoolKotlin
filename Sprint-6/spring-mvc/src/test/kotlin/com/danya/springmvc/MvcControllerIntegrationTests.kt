package com.danya.springmvc


import com.danya.springmvc.models.PersonPage
import com.danya.springmvc.service.AddressBookService
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var addressBookService: AddressBookService

    @BeforeEach
    fun setUp() {
        addressBookService.addPage(PersonPage("John", "lennon@abberyroad.com"))
        addressBookService.addPage(PersonPage("Paul", "mccartney@submarine.org"))
        addressBookService.addPage(PersonPage("George", "harrison@letitbe.ru"))
    }

    @Test
    fun `correct add page`() {
        val note: MultiValueMap<String, String> = LinkedMultiValueMap()

        note.add("name", "John")
        note.add("address", "lennon@abberyroad.com")


        mockMvc.perform(post("/app/add")
            .params(note))
            .andExpect(status().isOk)
            .andExpect(view().name("menuPage"))
            .andExpect(content().string(containsString("Page was added")))
    }


    @Test
    fun `edit id=0 page`() {
        val note: MultiValueMap<String, String> = LinkedMultiValueMap()

        note.add("name", "John")
        note.add("address", "lennon@abbeyroad.com")

        mockMvc.perform(post("/app/0/edit")
            .params(note))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("menuPage"))
            .andExpect(content().string(containsString("Page was edited")))
    }

    @ParameterizedTest
    @MethodSource("different id")
    fun `delete different id page`() {
        mockMvc.perform(get("/app/2/delete"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("menuPage"))
            .andExpect(content().string(containsString("Page was deleted")))
    }

    @ParameterizedTest
    @MethodSource("different id")
    fun `return page with different id`() {
        mockMvc.perform(get("/app/0/view"))
            .andDo(print())
            .andExpect(status().isOk)
    }


    @Test
    fun `return addForm`() {
        mockMvc.perform(get("/app/add"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("addForm"))
    }

    companion object {
        @JvmStatic
        fun `different id`() = listOf(
            2, 0, 1
        )
    }
}