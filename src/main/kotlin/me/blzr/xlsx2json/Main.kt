package me.blzr.xlsx2json

import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.system.exitProcess
import kotlin.time.measureTime

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("app input.xlsx output.xlsx or app input.xlsx - to write to stdout")
        exitProcess(1)
    } else {
        val (input, output) = args
        FileInputStream(input).use { inputStream ->
            if (output == "-") System.out else FileOutputStream(output).use { outputStream ->
                val elapsed = measureTime {
                    Parser.parse(inputStream, outputStream)
                }
                System.err.println("Elapsed: $elapsed")
            }
        }
    }
}
