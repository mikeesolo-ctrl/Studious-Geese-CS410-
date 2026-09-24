import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tables {

    // Columns
    public static final String KEYWORD_LETTERS = "abcdefhilnorstuw";
    public static final int IN_LETTER = 16; // Any other letter (aA-zZ)
    public static final int IN_LT = 17; // <
    public static final int IN_GT = 18; // >
    public static final int IN_EQ = 19; // =
    public static final int IN_BANG = 20; // !
    public static final int IN_DIV = 21; // /
    public static final int IN_MULT = 22; // *
    public static final int IN_SUB = 23; // -
    public static final int IN_SEMI = 24; // ;
    public static final int IN_OTHER = 25; // anything else -> no transition
    public static final int IN_NUM = 26; // ;
    public static final int IN_DOT = 27; // .
    public static final int IN_PLUS = 28; // +
    public static final int IN_OPEN_PAR = 29; // (
    public static final int IN_CLOSED_PAR = 30; // )
    public static final int IN_OPEN_CURLY = 31; // {
    public static final int IN_CLOSED_CURLY = 32; // }
    public static final int NUM_INPUTS = 33;

    // Rows
    public static final int S_START = 0;
    public static final int S_VAR = 1; // variable_name
    public static final int S_LT = 2; // <
    public static final int S_LE = 3; // <=
    public static final int S_GT = 4; // >
    public static final int S_GE = 5; // >=
    public static final int S_ASSIGN = 6; // =
    public static final int S_EQ = 7; // ==
    public static final int S_BANG = 8; // ! (not accepting)
    public static final int S_NEQ = 9; // !=
    public static final int S_DIV = 10; // /
    public static final int S_MULT = 11; // *
    public static final int S_SUB = 12; // -
    public static final int S_TERM = 13; // ;
    public static final int S_NUM = 14; // nums 0-9
    public static final int S_DOT = 15; // .
    public static final int S_PLUS = 16; // +
    public static final int S_OPEN_PAR = 17; // (
    public static final int S_CLOSED_PAR = 18; // )
    public static final int S_OPEN_CURLY = 19; // {
    public static final int S_CLOSED_CURLY = 20; // }
    public static final int FIRST_KW_STATE = 21;
    public static final int NUM_STATES = 60;
    public static final int ERR = -1;

    public static final Map<Character, Integer> INPUT_MAP = new HashMap<>();

    public static final int[][] TRANSITIONS = new int[NUM_STATES][NUM_INPUTS];
    public static final boolean[] ACCEPTING = new boolean[NUM_STATES];
    public static final String[] TOKEN_NAMES = new String[NUM_STATES];
    private static int nextState = FIRST_KW_STATE;

    static {
        // Input Map
        for (char c = 'a'; c <= 'z'; c++)
            INPUT_MAP.put(c, IN_LETTER);
        for (char c = 'A'; c <= 'Z'; c++)
            INPUT_MAP.put(c, IN_LETTER);
        for (char num = '0'; num <= '9'; num++)
            INPUT_MAP.put(num, IN_NUM);
        for (int i = 0; i < KEYWORD_LETTERS.length(); i++)
            INPUT_MAP.put(KEYWORD_LETTERS.charAt(i), i);
        INPUT_MAP.put('<', IN_LT);
        INPUT_MAP.put('>', IN_GT);
        INPUT_MAP.put('=', IN_EQ);
        INPUT_MAP.put('!', IN_BANG);
        INPUT_MAP.put('/', IN_DIV);
        INPUT_MAP.put('*', IN_MULT);
        INPUT_MAP.put('-', IN_SUB);
        INPUT_MAP.put(';', IN_SEMI);
        INPUT_MAP.put('.', IN_DOT);
        INPUT_MAP.put('+', IN_PLUS);
        INPUT_MAP.put('(', IN_OPEN_PAR);
        INPUT_MAP.put(')', IN_CLOSED_PAR);
        INPUT_MAP.put('{', IN_OPEN_CURLY);
        INPUT_MAP.put('}', IN_CLOSED_CURLY);

        for (int[] row : TRANSITIONS)
            Arrays.fill(row, ERR);

        // Start transitions
        TRANSITIONS[S_START][IN_LT] = S_LT;
        TRANSITIONS[S_LT][IN_EQ] = S_LE;
        TRANSITIONS[S_START][IN_GT] = S_GT;
        TRANSITIONS[S_GT][IN_EQ] = S_GE;
        TRANSITIONS[S_START][IN_EQ] = S_ASSIGN;
        TRANSITIONS[S_ASSIGN][IN_EQ] = S_EQ;
        TRANSITIONS[S_START][IN_BANG] = S_BANG;
        TRANSITIONS[S_BANG][IN_EQ] = S_NEQ;
        TRANSITIONS[S_START][IN_DIV] = S_DIV;
        TRANSITIONS[S_START][IN_MULT] = S_MULT;
        TRANSITIONS[S_START][IN_SUB] = S_SUB;
        TRANSITIONS[S_START][IN_SEMI] = S_TERM;
        TRANSITIONS[S_START][IN_NUM] = S_NUM;
        TRANSITIONS[S_NUM][IN_NUM] = S_NUM;
        TRANSITIONS[S_START][IN_DOT] = S_DOT;
        TRANSITIONS[S_START][IN_PLUS] = S_PLUS;
        TRANSITIONS[S_START][IN_OPEN_PAR] = S_OPEN_PAR;
        TRANSITIONS[S_START][IN_CLOSED_PAR] = S_CLOSED_PAR;
        TRANSITIONS[S_START][IN_OPEN_CURLY] = S_OPEN_CURLY;
        TRANSITIONS[S_START][IN_CLOSED_CURLY] = S_CLOSED_CURLY;

        // Variable names
        allLettersTo(S_START, S_VAR);
        allLettersTo(S_VAR, S_VAR);

        // Accepting states
        accept(S_VAR, "variable_name");
        accept(S_LT, "lsthn_optr");
        accept(S_LE, "lsthaneqto_optr");
        accept(S_GT, "Grtr_op");
        accept(S_GE, "grtrthneq_optr");
        accept(S_ASSIGN, "cmprsn_optr");
        accept(S_EQ, "eql_optr");
        accept(S_NEQ, "noteq_optr");
        accept(S_DIV, "div_optr");
        accept(S_MULT, "mult_optr");
        accept(S_SUB, "subtract_optr");
        accept(S_TERM, "terminator_op");
        accept(S_NUM, "num_literal");
        accept(S_DOT, "dot_op");
        accept(S_PLUS, "plus_op");
        accept(S_OPEN_PAR, "openPar_op");
        accept(S_CLOSED_PAR, "closedPar_op");
        accept(S_OPEN_CURLY, "openCurly_op");
        accept(S_CLOSED_CURLY, "closedCurly_op");

        // Keywords
        addKeyword("const", "cnst_kwd");
        addKeyword("while", "whle_kwd");
        addKeyword("if", "if_kwd");
        addKeyword("int", "int_kwd");
        addKeyword("for", "for_loop_kwd");
        addKeyword("float", "float_kwd");
        addKeyword("double", "dble_kwd");
        addKeyword("auto", "auto_kwd");
        addKeyword("else", "else_kwd");
    }

    private static void allLettersTo(int from, int to) {
        for (int col = 0; col <= IN_LETTER; col++)
            TRANSITIONS[from][col] = to;
    }

    private static void accept(int state, String token) {
        ACCEPTING[state] = true;
        TOKEN_NAMES[state] = token;
    }

    private static void addKeyword(String word, String token) {
        int state = S_START;
        for (int k = 0; k < word.length(); k++) {
            int col = INPUT_MAP.get(word.charAt(k));
            int next = TRANSITIONS[state][col];
            if (next == S_VAR) {
                next = nextState++;
                TRANSITIONS[state][col] = next;
                allLettersTo(next, S_VAR);
                accept(next, "variable_name");
            }
            state = next;
        }
        TOKEN_NAMES[state] = token;
    }

    public static int inputOf(char c) {
        return INPUT_MAP.getOrDefault(c, IN_OTHER);
    }
}