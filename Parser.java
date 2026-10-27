import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Parser {

    public ParseNode parse(List<Token> tokens, boolean trace) {
        ParseNode root = new ParseNode(ParseTables.START_SYMBOL);
        ParseNode endOfInput = new ParseNode(ParseTables.t(ParseTables.T_EOF));

        Deque<ParseNode> stack = new ArrayDeque<>();
        stack.push(endOfInput);
        stack.push(root);

        int index = 0;

        while (!stack.isEmpty()) {
            ParseNode top = stack.pop();
            int lookahead = lookaheadAt(tokens, index);

            if (ParseTables.isTerminal(top.symbol)) {
                int expected = ParseTables.terminalOf(top.symbol);
                if (expected != lookahead) {
                    throw new RuntimeException(errorMessage(tokens, index,
                            "expected " + ParseTables.TERMINAL_NAMES[expected]));
                }
                if (expected != ParseTables.T_EOF) {
                    top.token = tokens.get(index);
                    if (trace) System.out.println("  match  " + top.token);
                    index++;
                }

            } else {
                int production = ParseTables.PARSE_TABLE[top.symbol][lookahead];
                if (production == ParseTables.NONE) {
                    throw new RuntimeException(errorMessage(tokens, index,
                            "expected one of " + expectedAfter(top.symbol)));
                }

                top.production = production;
                if (trace) {
                    System.out.println("  apply  [" + production + "] "
                            + ParseTables.PRODUCTION_TEXT[production]);
                }

                int[] rhs = ParseTables.PRODUCTIONS[production];
                for (int symbol : rhs) top.children.add(new ParseNode(symbol));
                for (int k = rhs.length - 1; k >= 0; k--) stack.push(top.child(k));
            }
        }

        if (index < tokens.size()) {
            throw new RuntimeException(errorMessage(tokens, index, "expected end of input"));
        }
        return root;
    }

    private int lookaheadAt(List<Token> tokens, int index) {
        if (index >= tokens.size()) return ParseTables.T_EOF;

        Token token = tokens.get(index);
        int terminal = ParseTables.terminalOfToken(token);
        if (terminal == ParseTables.NONE) {
            throw new RuntimeException("Syntax error at position " + token.pos + ": the token "
                    + token + " is not used by the grammar");
        }
        return terminal;
    }

    private String expectedAfter(int nonterminal) {
        StringBuilder out = new StringBuilder();
        for (int terminal = 0; terminal < ParseTables.NUM_TERMINALS; terminal++) {
            if (ParseTables.PARSE_TABLE[nonterminal][terminal] == ParseTables.NONE) continue;
            if (out.length() > 0) out.append(", ");
            out.append(ParseTables.TERMINAL_NAMES[terminal]);
        }
        return out.toString();
    }

    private String errorMessage(List<Token> tokens, int index, String detail) {
        if (index >= tokens.size()) {
            return "Syntax error at end of input: " + detail;
        }
        Token token = tokens.get(index);
        return "Syntax error at position " + token.pos + ": found " + token + ", " + detail;
    }
}
