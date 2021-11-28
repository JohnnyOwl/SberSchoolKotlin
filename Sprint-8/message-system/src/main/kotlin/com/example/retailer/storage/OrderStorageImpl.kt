package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.storage.repository.OrderInfoRepository
import com.example.retailer.storage.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl : OrderStorage {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var orderInfoRepository: OrderInfoRepository

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = orderInfoRepository.save(OrderInfo(order.id!!, OrderStatus.SENT, "sig"))

        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        if (orderInfoRepository.findById(order.orderId).isPresent) {
            orderInfoRepository.save(order)
            return true
        }
        return false
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        val orderInfo = orderInfoRepository.findById(id)

        if (orderInfo.isPresent) {
            return orderInfo.get()
        }

        return null
    }
}