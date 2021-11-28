package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.services.FirstService
import ru.sber.services.SecondService
import ru.sber.services.ThirdService

@Configuration
@ComponentScan
class ServicesConfig {
    @Bean("firstService")
    fun service(): FirstService {
        return FirstService()
    }

    @Bean("secondService")
    fun secondService(): SecondService {
        return SecondService()
    }

    @Bean("thirdService")
    fun thirdService(): ThirdService {
        return ThirdService()
    }
}

@Configuration
@ComponentScan("ru.sber.services")
class AnotherServicesConfig