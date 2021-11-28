package com.danya.springmvc.service

import com.danya.springmvc.models.PersonPage
import com.danya.springmvc.repository.AddressBookRepository
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class AddressBookService {
    private val addressBookRepository = AddressBookRepository(ConcurrentHashMap())

    fun addPage(personPage: PersonPage) = addressBookRepository.savePage(personPage)

    fun getPageById(query: Map<String, String>): ConcurrentHashMap<Int, PersonPage> {
        return if (query.isEmpty())
            getAllPages()
        else
            addressBookRepository.getPageById(query)
    }

    fun getAllPages(): ConcurrentHashMap<Int, PersonPage> {
        return addressBookRepository.getAllPages()
    }

    fun editPage(id: Int, personPage: PersonPage): PersonPage? {
       return addressBookRepository.editPage(id, personPage)
    }


    fun deletePage(id: Int): PersonPage? = addressBookRepository.deletePage(id)
}