package com.example.retailer.adapter

import com.example.retailer.AmqpConfiguration.Companion.DISTRIBUTOR_EXCHANGE
import com.example.retailer.AmqpConfiguration.Companion.RETAILER_NAME
import com.example.retailer.api.distributor.Order
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DistributorPublisherImpl : DistributorPublisher {

    @Autowired
    private lateinit var template: RabbitTemplate

    override fun placeOrder(order: Order): Boolean {
        template.convertAndSend(
            DISTRIBUTOR_EXCHANGE,
            "distributor.placeOrder.$RETAILER_NAME.${order.id}", order) { message ->

            message.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
            message.messageProperties.headers["Notify-RoutingKey"] = "retailer.$RETAILER_NAME"
            message.messageProperties.contentType = MessageProperties.CONTENT_TYPE_JSON

            return@convertAndSend message
        }
        return true
    }
}