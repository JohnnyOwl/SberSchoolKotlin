package com.danya.springmvc.controller

import com.danya.springmvc.models.PersonPage
import com.danya.springmvc.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class MvcController @Autowired constructor(val addressBookService: AddressBookService) {

    @RequestMapping
    fun getMainMenu(): String{
        return "menuPage"
    }

    @GetMapping("/add")
    fun getAddForm(): String{
        return "addForm"
    }

    @PostMapping("/add")
    fun addPage(@ModelAttribute personPage: PersonPage, model: Model): String {
        addressBookService.addPage(personPage)
        model.addAttribute("result", "Page was added")

        return "menuPage"
    }

    @GetMapping("/list")
    fun getPage(@RequestParam query: Map<String, String>, model: Model): String {
        val searchResult = addressBookService.getPageById(query)

        model.addAttribute("result", searchResult)
        model.addAttribute("msg", "Search result:")

        return "pageList"
    }

    @GetMapping("/{id}/view")
    fun getPageById(@PathVariable id: String, model: Model): String {
        val searchResult = addressBookService.getPageById(mapOf("id" to id))
        model.addAttribute("result", searchResult)

        return "personPage"
    }

    @GetMapping("/{id}/edit")
    fun getEditForm(@PathVariable id: String): String{
        return "editForm"
    }

    @PostMapping("/{id}/edit")
    fun editPage(@PathVariable id: String, @ModelAttribute personPage: PersonPage, model: Model): String {
        addressBookService.editPage(id.toInt(), personPage)
        model.addAttribute("result", "Page was edited")

        return "menuPage"
    }

    @GetMapping("/{id}/delete")
    fun confirmDelete(@PathVariable id: String): String{
        return "deleteForm"
    }

    @PostMapping("/{id}/delete")
    fun deletePage(@PathVariable id: String, model: Model): String {
        addressBookService.deletePage(id.toInt())
        model.addAttribute("result", "Page was deleted")

        return "menuPage"
    }
}