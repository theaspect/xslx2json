# Benchmark

## JVM
```
java -Xmx8g -jar xlsx2json-1.0-SNAPSHOT-all.jar in.xlsx out.json
01:28:38.942 [main] INFO  Parser - Will read in.xlsx
01:28:55.673 [main] INFO  Parser - Book is loaded
01:28:56.967 [main] INFO  Parser - Parsed 10000 rows
01:28:57.808 [main] INFO  Parser - Parsed 20000 rows
01:28:58.704 [main] INFO  Parser - Parsed 30000 rows
01:28:59.549 [main] INFO  Parser - Parsed 40000 rows
01:29:00.378 [main] INFO  Parser - Parsed 50000 rows
01:29:01.146 [main] INFO  Parser - Parsed 60000 rows
01:29:01.889 [main] INFO  Parser - Parsed 70000 rows
01:29:02.617 [main] INFO  Parser - Parsed 80000 rows
01:29:03.355 [main] INFO  Parser - Parsed 90000 rows
...
01:29:55.837 [main] INFO  Parser - Parsed 790000 rows
01:29:56.612 [main] INFO  me.blzr.xlsx2json.Parser - Elapsed: 1m 17.825965300s
```

## Native
```
.\xlsx2json.exe in.xlsx out.json
01:23:16.776 [main] INFO  Parser - Will read in.xlsx
01:24:07.455 [main] INFO  Parser - Book is loaded
01:24:08.587 [main] INFO  Parser - Parsed 10000 rows
01:24:09.511 [main] INFO  Parser - Parsed 20000 rows
01:24:10.410 [main] INFO  Parser - Parsed 30000 rows
01:24:11.350 [main] INFO  Parser - Parsed 40000 rows
01:24:12.299 [main] INFO  Parser - Parsed 50000 rows
01:24:13.266 [main] INFO  Parser - Parsed 60000 rows
01:24:14.321 [main] INFO  Parser - Parsed 70000 rows
01:24:15.358 [main] INFO  Parser - Parsed 80000 rows
01:24:16.343 [main] INFO  Parser - Parsed 90000 rows
...
01:25:27.418 [main] INFO  Parser - Parsed 790000 rows
01:25:28.301 [main] INFO  me.blzr.xlsx2json.Parser - Elapsed: 2m 11.527032400s
```
