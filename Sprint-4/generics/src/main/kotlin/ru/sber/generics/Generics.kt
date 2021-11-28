package ru.sber.generics

import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.*

fun <T> compare(p1: Pair<T, T>, p2: Pair<T, T>): Boolean = p1.first == p2.first && p1.second == p2.second

fun <T : Comparable<T>> countGreaterThan(anArray: Array<T>, elem: T): Int {
    var counter = 0

    anArray.forEach { arrayElement -> if (elem < arrayElement) counter++ }

    return counter
}

class Sorter<T : Comparable<T>> {
    val list: MutableList<T> = mutableListOf()

    fun add(value: T) {
        for (i in 0..list.size) {
            if (i == list.size || value < list[i]) {
                list.add(i, value)
                break
            }
        }
    }
}

class Stack<T> {
    val list: MutableList<T> = mutableListOf()

    fun push(value: T) = list.add(0, value)

    fun pop() = list.removeAt(0)

    fun isEmpty(): Boolean = list.isEmpty()
}