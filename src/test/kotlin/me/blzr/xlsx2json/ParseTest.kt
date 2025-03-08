package me.blzr.xlsx2json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

class ParseTest : StringSpec({
    "Read file" {
        val input = ParseTest::class.java.getResourceAsStream("/input.xlsx")!!
        val byteArrayOutputStream = ByteArrayOutputStream()

        Parser.parse(input, byteArrayOutputStream)

        String(byteArrayOutputStream.toByteArray(), Charset.forName("utf-8")) shouldBe """[{"Header 1":1.0,"Header 2":"Foo"},{"Header 3":"Bar"},{"Header 1":2.0,"Header 2":"Baz","Header 3":"abc"}]"""
    }

})
