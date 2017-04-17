# Compiler
Compiler in java for fake language defined in [grammar.txt](docs/grammar.txt).

## Usage
```bash
java -jar <compiled_lib> -i <input_files> -o <output_dir>
```

Arguments:
- *compiled_lib* - jar library with compiled classes. Either **compiler.jar** or **compiler-jar-with-dependencies.jar** can be used. First lib contains only compiler classes where the second one comes with dependencies out of the box;
- *input_files* - list of input files. Items must be separated with space;
- *output_dir* - place where all successfully compiled files are stored.
