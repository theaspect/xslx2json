package me.blzr.xlsx2json

import com.fasterxml.jackson.core.JsonEncoding
import com.fasterxml.jackson.core.JsonFactory
import com.github.pjfanning.xlsx.SharedStringsImplementationType
import com.github.pjfanning.xlsx.StreamingReader
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType.*
import org.apache.poi.ss.usermodel.Workbook
import org.slf4j.LoggerFactory
import java.io.File
import java.io.OutputStream


object Parser {
    private val log = LoggerFactory.getLogger("Parser")
    fun parse(input: String, outputStream: OutputStream) {
        log.info("Will read $input")
        // See more info regarding configuration
        // https://github.com/monitorjbl/excel-streaming-reader/issues/104
        // graal 4094b 100 rows 4m 1s
        // graal 1mb 1000 rows 4m 38s

        // https://github.com/pjfanning/excel-streaming-reader?tab=readme-ov-file#reading-very-large-excel-files
        // ZipInputStreamZipEntrySource.setThresholdBytesForTempFiles(16384); //16KB
        // ZipPackage.setUseTempFilePackageParts(true);

        val workbook: Workbook = StreamingReader.builder()
            .setSharedStringsImplementationType(SharedStringsImplementationType.CUSTOM_MAP_BACKED)
            .setFullFormatRichText(false)
            .setReadStyles(false)
            //.setAvoidTempFiles(false)
            .setAvoidTempFiles(true)
            .rowCacheSize(10) // number of rows to keep in memory (defaults to 10)
            .bufferSize(1024) // buffer size to use when reading InputStream to file (defaults to 1024)
            // Most of the time took sst loading
            // .sstCacheSize(1024 * 1024 * 1024)
            .open(File(input)) // InputStream or File for XLSX file (required)
        log.info("Book is loaded")
        val sheet = workbook.getSheetAt(0)
        // System.out.println("Total ${sheet.lastRowNum} rows")

        val rowIterator = sheet.iterator()

        val headerRow = rowIterator.next()
        val header = HashMap<Int, String?>()
        for (c in headerRow) {
            header[c.columnIndex] = c.getCellValueOrNull()
        }

        JsonFactory().createGenerator(outputStream, JsonEncoding.UTF8).use { jsonGenerator ->
            jsonGenerator.writeStartArray()

            for (row in rowIterator) {
                if (row.rowNum % 10_000 == 0) log.info("Parsed ${row.rowNum} rows")

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
