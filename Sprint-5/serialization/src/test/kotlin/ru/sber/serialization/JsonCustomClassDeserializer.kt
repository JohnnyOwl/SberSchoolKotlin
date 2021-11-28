package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val simpleModule = SimpleModule()
            .addDeserializer(Client7::class.java, CustomClient7Deserializer())
        val objectMapper = ObjectMapper()
            .registerModule(simpleModule)

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }

    class CustomClient7Deserializer : StdDeserializer<Client7>(Client7::class.java) {
        override fun deserialize(jasonParser: JsonParser?, context: DeserializationContext?): Client7 {
            val node: JsonNode? = jasonParser?.codec?.readTree(jasonParser)
            val fields = node?.get("client").toString().trim('"').split(" ")

            return Client7(firstName = fields[1], middleName = fields[2], lastName = fields[0])
        }
    }
}
