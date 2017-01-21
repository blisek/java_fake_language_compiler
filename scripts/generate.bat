@ECHO OFF

echo Generating scanner...
java -jar ./lib/jflex-1.6.1.jar -d ./src/main/java/com/blisek/compiler_jftt/scanner ./src/main/resources/com/blisek/compiler_jftt/scanner/scanner.flex

echo Generating parser...
java -jar ./lib/beaver-cc-0.9.11.jar -a -d ./src/main/java/ ./src/main/resources/com/blisek/compiler_jftt/parser/parser.grammar