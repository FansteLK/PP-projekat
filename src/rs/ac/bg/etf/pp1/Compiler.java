package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

/**
 * Command-line entry point: lexing, parsing, semantic analysis and MicroJava bytecode generation.
 *
 * <pre>
 * mjc [-v] [--ast] [--symbols] &lt;source.mj&gt; [output.obj]
 * </pre>
 *
 * Exit codes: 0 success, 1 compilation errors, 2 usage error.
 */
public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
	}

	private static final Logger log = Logger.getLogger(Compiler.class);

	public static void main(String[] args) throws Exception {
		boolean printAst = false, printSymbols = false;
		String source = null, output = null;
		for (String arg : args) {
			switch (arg) {
			case "-v": case "--verbose": Logger.getRootLogger().setLevel(Level.DEBUG); break;
			case "--ast": printAst = true; break;
			case "--symbols": printSymbols = true; break;
			default:
				if (source == null) source = arg;
				else if (output == null) output = arg;
				else usage();
			}
		}
		if (source == null) usage();
		if (output == null) output = source.replaceAll("\\.mj$", "") + ".obj";

		System.exit(compile(new File(source), new File(output), printAst, printSymbols) ? 0 : 1);
	}

	public static boolean compile(File source, File output, boolean printAst, boolean printSymbols) throws Exception {
		log.info("Compiling " + source.getPath());
		Program program;
		MJParser parser;
		try (Reader reader = new BufferedReader(new FileReader(source))) {
			parser = new MJParser(new Yylex(reader));
			Symbol root = parser.parse();
			program = root == null ? null : (Program) root.value;
		}
		if (program == null) {
			System.err.println(source.getPath() + ": parsing failed");
			return false;
		}
		if (printAst) System.out.println(program.toString(""));

		Tab.init();
		SemanticAnalyzer analyzer = new SemanticAnalyzer();
		program.traverseBottomUp(analyzer);
		if (printSymbols) Tab.dump();

		if (parser.errorDetected || !analyzer.passed()) {
			System.err.println(source.getPath() + ": compilation failed");
			return false;
		}

		CodeGenerator generator = new CodeGenerator();
		program.traverseBottomUp(generator);
		Code.dataSize = analyzer.nVars;
		Code.mainPc = generator.getMainPc();
		if (output.getParentFile() != null) output.getParentFile().mkdirs();
		try (OutputStream out = new FileOutputStream(output)) {
			Code.write(out);
		}
		System.err.println(source.getPath() + " -> " + output.getPath());
		return true;
	}

	private static void usage() {
		System.err.println("usage: mjc [-v] [--ast] [--symbols] <source.mj> [output.obj]");
		System.exit(2);
	}
}
