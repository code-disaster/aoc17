package com.codedisaster.aoc

import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {

    "/Day02.txt".asLineSequenceFromResource {

        var hash = 0

        it.forEach {

            val numbers: List<Int> = it
                    .split(Regex("\\s+"))
                    .map { value -> value.toInt() }

            hash += (numbers.max() ?: 0) - (numbers.min() ?: 0)
        }

        println("part 1: hash=$hash")

        hash = 0

        it.forEach {

            val numbers: List<Int> = it
                    .split(Regex("\\s+"))
                    .map { value -> value.toInt() }

            var found = false

            numbers.forEachIndexed { i0, value0 ->
                run {
                    if (!found) {
                        numbers.forEachIndexed { i1, value1 ->
                            run {
                                if (!found && i0 != i1) {

                                    val x = min(value0, value1)
                                    val y = max(value0, value1)

                                    if (y % x == 0) {
                                        hash += y / x
                                        found = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        println("part 1: hash=$hash")
    }

}

fun String.asLineSequenceFromResource(consumer: (Sequence<String>) -> Unit) {
    val content = this.javaClass::class.java.getResource(this).readText().lineSequence()
    consumer(content)
}
