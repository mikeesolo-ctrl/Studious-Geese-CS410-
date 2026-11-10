import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Compiler {

    public static List<Token> tokenize(String src) {
        List<Token> tokens = new ArrayList<>();
        int pos = 0;

        while (pos < src.length()) {
            char c = src.charAt(pos);
            if (Character.isWhitespace(c)) {
                pos++;
                continue;
            }

            int state = Tables.S_START;
            int i = pos;
            int lastAcceptState = Tables.ERR;
            int lastAcceptEnd = pos;

            while (i < src.length()) {
                int next = Tables.TRANSITIONS[state][Tables.inputOf(src.charAt(i))];
                if (next == Tables.ERR)
                    break;
                state = next;
                i++;
                if (Tables.ACCEPTING[state]) {
                    lastAcceptState = state;
                    lastAcceptEnd = i;
                }
            }

            if (lastAcceptState == Tables.ERR) {
                throw new RuntimeException("Unexpected token '" + c + "' at position " + pos);
            }

            tokens.add(new Token(Tables.TOKEN_NAMES[lastAcceptState],
                    src.substring(pos, lastAcceptEnd), pos));
            pos = lastAcceptEnd;
        }
        return tokens;
    }

    public static List<Atom> compile(String src, boolean trace) {
        List<Token> tokens = tokenize(src);
        if (trace) {
            System.out.println("--- tokens (" + tokens.size() + ") ---");
            for (Token token : tokens)
                System.out.println("  " + token);
            System.out.println();
            System.out.println("--- parse ---");
        }

        ParseNode tree = new Parser().parse(tokens, trace);
        if (trace)
            System.out.println();

        return new AtomGenerator().generate(tree);
    }

    private static final String SAMPLE = """
            {
                int sum = 0;
                int limit = 10;
                for ( int i = 1; i <= limit; i++ ) {
                    sum = sum + i * 2;
                }
                while ( sum > limit ) {
                    sum = sum - limit;
                }
                if ( sum == 0 ) {
                    sum = limit;
                } else {
                    sum = sum / 2;
                }
            }
            """;

    public static void main(String[] args) throws IOException {
        boolean trace = false;
        String source = null;

        for (String arg : args) {
            if (arg.equals("-trace") || arg.equals("--trace"))
                trace = true;
            else
                source = Files.readString(Path.of(arg));
        }
        if (source == null)
            source = SAMPLE;

        for (Atom atom : compile(source, trace)) {
            System.out.println(atom);
        }
    }
}
