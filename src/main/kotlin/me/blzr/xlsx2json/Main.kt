package me.blzr.xlsx2json

import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("app input.xlsx output.xlsx or app input.xlsx - to write to stdout")
        exitProcess(1)
    } else {
        // val dtddvFactoryImpl = org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl()
        // dtddvFactoryImpl.equals(null)
        // val factory = DTDDVFactory.getInstance()
        // factory.toString()

        val (input, output) = args
        FileInputStream(input).use { inputStream ->
            if (output == "-") System.out else FileOutputStream(output).use { outputStream ->
                Parser.parse(inputStream, outputStream)
            }
        }
    }
}
