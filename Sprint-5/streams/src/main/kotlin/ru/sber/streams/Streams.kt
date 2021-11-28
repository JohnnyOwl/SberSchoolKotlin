package ru.sber.streams

fun getSumWithIndexDivisibleByThree(list: List<Long>): Long =
    list.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }

fun generateFibonacciSequence(): Sequence<Int> =
    generateSequence(Pair(0, 1)) { Pair(it.second, it.second + it.first) }.map { it.first }

fun Shop.getCustomersCities(): Set<City> =
    customers.map { customer -> customer.city }.toSet()

fun Shop.allOrderedProducts(): Set<Product> =
    customers.flatMap { customer -> customer.orders.flatMap { it.products } }.toSet()

fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? =
    customers.maxByOrNull { customer -> customer.orders.size }

fun Customer.getMostExpensiveProduct(): Product? =
    orders.flatMap { order -> order.products }
        .maxByOrNull { product -> product.price }

fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers.map { customer ->
        Pair(
            customer.city,
            customer.orders.sumOf { order -> if (order.isDelivered) order.products.size else 0 }
        )
    }
        .groupBy { customer -> customer.first }
        .map { entry -> Pair(entry.key, entry.value.sumOf { customer -> customer.second }) }
        .toMap()

fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers.groupBy { customer -> customer.city }
        .mapValues { entry ->
            entry.value
                .flatMap { it.orders }
                .flatMap { it.products }
                .groupingBy { it }
                .eachCount()
        }.map { entry -> entry.key to entry.value.maxByOrNull { it.value }!!.key }
        .toMap()

fun Shop.getProductsOrderedByAll(): Set<Product> =
    customers.map { customer ->
        customer.orders.map { order ->
            order.products
        }.flatten().toSet()
    }.reduce { acc, item -> item.intersect(acc) }
