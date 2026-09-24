import java.util.ArrayList;
import java.util.List;

public class Compiler {

    public static List<String> tokenize(String src) {
        List<String> tokens = new ArrayList<>();
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

            String lexeme = src.substring(pos, lastAcceptEnd);
            tokens.add(Tables.TOKEN_NAMES[lastAcceptState] + "(" + lexeme + ")");
            pos = lastAcceptEnd;
        }
        return tokens;
    }

}
