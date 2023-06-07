package chucknorris

fun main() {
    outer@ while (true) {

        println("Please input operation (encode/decode/exit):")
        val operation = readln()
        if (operation == "encode") {
            println("Input string:")
            val input = readln()

            var output = mutableListOf<String>()
            for (c in input.toList()) {
                val initial = Integer.toBinaryString(c.code).padStart(7, '0')
                output.add(initial)
            }
            println("Encoded string:")
            println(finalize(output.joinToString("")))
        } else if (operation == "decode") {
            println("Input encoded string:")
            val s = readln().trim()
            var str = ""
            if (s.split(" ").size % 2 != 0) {
                println("Encoded string is not valid.")
                continue
            }
            for (l in s.split(" ").chunked(2)) {
                if (l[0] == "00") {
                    repeat(l[1].length) {
                        str += "0"
                    }
                } else if (l[0] == "0") {
                    repeat(l[1].length) {
                        str += "1"
                    }
                } else {
                    println("Encoded string is not valid.")
                    continue@outer
                }
            }
            if (str.length % 7 != 0) {
                println("Encoded string is not valid.")
                continue
            }
            println("Decoded string:")
            println(str.chunked(7).map { toDecimal(it).toChar() }.joinToString(""))
        } else if (operation == "exit") {
            println("Bye!")
            return
        } else {
            println("There is no '$operation' operation")
        }

    }
}

fun toDecimal(str: String): Int {
    var num = 0
    for ((i, c) in str.reversed().toList().withIndex()) {
        num += c.digitToInt() * Math.pow(2.0, i.toDouble()).toInt()
    }
    return num
}

fun finalize(s: String): String {
    var str = ""
    var zeros = 0
    var ones = 0

    s.toList().forEach {
        if (it == '0') {
            if (ones != 0) {
                str += "0 "
                repeat(ones) {
                    str += "0"
                }
                str += " "
                ones = 0
            }
            zeros += 1
        } else if (it == '1') {
            if (zeros != 0) {
                str += "00 "
                repeat(zeros) {
                    str += "0"
                }
                str += " "
                zeros = 0
            }
            ones += 1
        }
    }

    if (zeros != 0) {
        str += "00 "
        repeat(zeros) {
            str += "0"
        }
    }
    if (ones != 0) {
        str += "0 "
        repeat(ones) {
            str += "0"
        }
    }
    return str
}
