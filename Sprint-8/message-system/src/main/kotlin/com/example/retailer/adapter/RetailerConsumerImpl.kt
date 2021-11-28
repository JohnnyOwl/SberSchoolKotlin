package com.example.retailer.adapter

import com.example.retailer.AmqpConfiguration.Companion.RETAILER_QUEUE
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import com.example.retailer.storage.OrderStorage
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class RetailerConsumerImpl(private val storage: OrderStorage) : RetailerConsumer {

    @RabbitListener(queues = [RETAILER_QUEUE])
    override fun receiveUpdate(orderInfo: OrderInfo) {
        storage.updateOrder(orderInfo)
    }
}