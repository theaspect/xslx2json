package me.blzr.xlsx2json

import com.fasterxml.jackson.core.JsonEncoding
import com.fasterxml.jackson.core.JsonFactory
import com.monitorjbl.xlsx.StreamingReader
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType.*
import org.apache.poi.ss.usermodel.Workbook
import java.io.InputStream
import java.io.OutputStream


object Parser {
    fun parse(inputStream: InputStream, outputStream: OutputStream) {
        val workbook: Workbook = StreamingReader.builder()
            .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
            .bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
            .open(inputStream) // InputStream or File for XLSX file (required)

        val sheet = workbook.getSheetAt(0)
        System.err.println("Total ${sheet.lastRowNum} rows")

        val rowIterator = sheet.iterator()

        val headerRow = rowIterator.next()
        val header = HashMap<Int, String?>()
        for (c in headerRow) {
            header[c.columnIndex] = c.getCellValueOrNull()
        }

        JsonFactory().createGenerator(outputStream, JsonEncoding.UTF8).use { jsonGenerator ->
            jsonGenerator.writeStartArray()

            for (row in rowIterator) {
                if (row.rowNum % 1000 == 0) System.err.println("Parsed ${row.rowNum} rows")

                jsonGenerator.writeStartObject()

                for (cell in row) {
                    when (cell.cellType) {
                        STRING -> jsonGenerator.writeStringField(header[cell.columnIndex], cell.stringCellValue)
                        NUMERIC -> jsonGenerator.writeNumberField(header[cell.columnIndex], cell.numericCellValue)
                        BOOLEAN -> jsonGenerator.writeBooleanField(header[cell.columnIndex], cell.booleanCellValue)
                        _NONE, FORMULA, BLANK, ERROR -> {/* Do nothing */
                        }
                    }
                }

                jsonGenerator.writeEndObject()
            }

            jsonGenerator.writeEndArray()
        }
    }

    fun Cell.getCellValueOrNull(): String? =
        when (this.cellType) {
            NUMERIC -> this.numericCellValue.toString()
            STRING -> this.stringCellValue
            else -> null
        }
}
