package com.codedisaster.aoc

fun main(args: Array<String>) {

    "/Day01.txt".asTextFromResource {

        var sum = 0

        for (index in 0 until it.length) {

            val c0 = it[index]
            val c1 = it[(index + 1) % it.length]

            if (c0 == c1) {
                sum += c0 - '0'
            }
        }

        println("part 1: sum=$sum")

        sum = 0
        val halfLen = it.length / 2

        for (index in 0 until halfLen) {

            val c0 = it[index]
            val c1 = it[index + halfLen]

            if (c0 == c1) {
                sum += 2 * (c0 - '0')
            }
        }

        println("part 2: sum=$sum")
    }
}

fun String.asTextFromResource(consumer: (String) -> Unit) {
    val content = this.javaClass::class.java.getResource(this).readText()
    consumer(content)
}
