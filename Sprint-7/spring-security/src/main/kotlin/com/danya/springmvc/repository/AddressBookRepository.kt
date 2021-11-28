package com.danya.springmvc.repository

import com.danya.springmvc.models.PersonPage
import java.util.concurrent.ConcurrentHashMap

class AddressBookRepository(private val addressBook: ConcurrentHashMap<Int, PersonPage>) {

    fun savePage(personPage: PersonPage) {
        addressBook[addressBook.size] = personPage
    }

    fun getAllPages(): ConcurrentHashMap<Int, PersonPage> {
        return if (addressBook.isEmpty())
            ConcurrentHashMap()
        else
            addressBook
    }
    fun editPage(id: Int, newPersonPage: PersonPage): PersonPage? {
        if (newPersonPage.name.isNotEmpty())
            addressBook[id]!!.name = newPersonPage.name

        if (newPersonPage.address.isNotEmpty())
            addressBook[id]!!.address = newPersonPage.address

        return addressBook[id]
    }

    fun deletePage(id: Int): PersonPage? {
        addressBook.remove(id)
        return addressBook[id]
    }

    fun getPageById(query: Map<String, String>): ConcurrentHashMap<Int, PersonPage>{
        val resultSearch = ConcurrentHashMap<Int, PersonPage>()
        val id = query["id"]!!.toInt()

        addressBook[id]?.let { resultSearch.put(id, it) }

        return resultSearch
    }
}