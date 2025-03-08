package me.blzr.xlsx2json

import org.slf4j.LoggerFactory
import java.io.FileOutputStream
import kotlin.system.exitProcess
import kotlin.time.measureTime

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("app input.xlsx output.xlsx") // or app input.xlsx - to write to stdout")
        exitProcess(1)
    } else {
        val (input, output) = args
        if (false /* output == "-" */) System.out else FileOutputStream(output).use { outputStream ->
            val elapsed = measureTime {
                Parser.parse(input, outputStream)
            }
            LoggerFactory.getLogger(Parser::class.java).info("Elapsed: $elapsed")
        }
    }
}
