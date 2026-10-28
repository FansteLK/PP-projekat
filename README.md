# MicroJava compiler

[![CI](https://github.com/FansteLK/PP-projekat/actions/workflows/ci.yml/badge.svg)](https://github.com/FansteLK/PP-projekat/actions/workflows/ci.yml)

A complete compiler for **MicroJava**, a small Java-like teaching language. It turns source code into bytecode for the MicroJava virtual machine, covering lexical analysis, LALR(1) parsing with error recovery, semantic analysis over a scoped symbol table, and code generation.

It was built for the *Compiler Construction 1* course (Programski prevodioci 1) at the School of Electrical Engineering, University of Belgrade.

```text
$ scripts/mjc examples/sort.mj && echo "8 42 7 19 -3 100 0 7 55" | tr ' ' '\n' | scripts/mjrun examples/sort.obj
examples/sort.mj -> examples/sort.obj
  -3   0   7   7  19  42  55 100
  227
  100
```

## Pipeline

```mermaid
flowchart LR
    SRC[".mj source"] --> LEX["Lexer<br/>JFlex · spec/mjlexer.lex"]
    LEX -- tokens --> PAR["LALR(1) parser<br/>CUP · spec/mjparser.cup"]
    PAR -- AST --> SEM["SemanticAnalyzer<br/>visitor + symbol table"]
    SEM -- "typed AST" --> GEN["CodeGenerator<br/>visitor"]
    GEN --> OBJ[".obj bytecode"] --> VM["MicroJava VM"]
```

| Phase | Implementation | Highlights |
| --- | --- | --- |
| Lexical analysis | [`spec/mjlexer.lex`](spec/mjlexer.lex) → JFlex | Keywords, operators, int/char/bool literals, line comments; reports bad characters with line numbers and continues |
| Syntax analysis | [`spec/mjparser.cup`](spec/mjparser.cup) → CUP (~70 productions) | Builds an AST (~200 generated node classes). **Error recovery** at declarations, assignments, formal parameters and `extends` clauses, so one syntax error doesn't hide the rest |
| Semantic analysis | [`SemanticAnalyzer.java`](src/rs/ac/bg/etf/pp1/SemanticAnalyzer.java) | Scopes, redeclarations, type compatibility of expressions, assignments and arguments, method signatures and return types, `break`/`continue` placement, `record` vs `class` rules, inheritance checks |
| Code generation | [`CodeGenerator.java`](src/rs/ac/bg/etf/pp1/CodeGenerator.java) | Stack-machine bytecode with backpatched jumps for `if`/`else`, `do`–`while`, `break`, `continue`, `goto`, and short-circuit `&&`/`||` |

## Language support

| Supported end to end | Notes |
| --- | --- |
| `int`, `char`, `bool` and arrays of them | `new T[n]`, indexing, `len(arr)` |
| Global constants and variables | `const int MAX = 100;` |
| Methods with parameters, local variables, return values, recursion | [`examples/recursion.mj`](examples/recursion.mj) |
| `if`/`else`, `do { } while (c);`, `break`, `continue` | |
| Labels and `goto` | |
| `&&`, `\|\|` with short-circuit evaluation; relational operators | |
| `read(x)`, `print(e)`, `print(e, width)`, `ord`, `chr` | |
| `record` types with field access | |
| `class` declarations with `extends` and constructors | **parsed and type-checked only**; code generation for methods on objects (`this`, virtual calls) was not implemented (see [`examples/unsupported/`](examples/unsupported)) |

## Usage

Requirements: JDK 8 or newer and Apache Ant.

```bash
ant              # generates lexer + parser, compiles, builds build/mjc.jar
ant test         # runs the golden-file test suite

scripts/mjc examples/test302.mj                  # → examples/test302.obj
scripts/mjrun examples/test302.obj               # run on the MicroJava VM
scripts/mjrun --disasm examples/test302.obj      # disassemble
scripts/mjc --ast --symbols -v examples/sort.mj  # dump AST, symbol table and trace
```

Diagnostics go to stderr. The messages are in Serbian, as required by the course. For example:

```text
$ scripts/mjc examples/errors/typeserrors.mj
ERROR Greska na liniji 11 :ime C nije deklarisano!   # "name C is not declared"
...
examples/errors/typeserrors.mj: compilation failed
```

## Tests

[`tests/run-tests.sh`](tests/run-tests.sh) is a golden-file suite that CI runs on every push:

- every program in `examples/` must compile cleanly, and its output on the VM, with stdin from `tests/input/`, must match `tests/expected/`
- every program in `examples/errors/` must be rejected with exactly the diagnostics in `tests/expected/errors/`; these cover lexical, syntax (with recovery), type and scope errors

Run `UPDATE=1 tests/run-tests.sh` to regenerate the expected files after an intentional change.

## Project layout

```
spec/            lexer and grammar specifications (the generated Java is not committed)
src/             compiler driver, semantic analyzer, code generator
lib/             course-provided tools: JFlex, CUP, symbol table, MicroJava VM, log4j
examples/        sample programs, programs with errors, unsupported features
tests/           test runner, inputs and expected outputs
docs/            original project documentation (Serbian)
```

## Changes since the course submission

- **Lexer fixes:**
  - Line comments only ended at `\r\n`, so the compiler failed on any file with Unix line endings.
  - Identifiers could contain `|`, so `a||b` lexed as a single identifier.
- **Semantic analysis fix:** recursive calls were rejected, because a method's parameters weren't visible while its own body was analyzed.
- **Code generation fixes:**
  - `len(arr)` returned the array reference instead of its length.
  - Function calls inside expressions made the generator try to load the method as a value.
  - Built-ins called as statements left a value on the stack.
- **Build and tooling:**
  - The build now regenerates the lexer.
  - A broken classpath entry was fixed.
  - Generated sources, class files and 90 MB of logs are no longer committed.
  - Added a command-line driver with exit codes, a test suite, and CI.
