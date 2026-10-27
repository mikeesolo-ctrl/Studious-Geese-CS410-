import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseTables {

    public static final int T_INT = 0; // int
    public static final int T_FLOAT = 1; // float
    public static final int T_DOUBLE = 2; // double
    public static final int T_IF = 3; // if
    public static final int T_ELSE = 4; // else
    public static final int T_WHILE = 5; // while
    public static final int T_FOR = 6; // for
    public static final int T_VAR = 7; // variable_name
    public static final int T_INTLIT = 8; // intLiteral
    public static final int T_FLOATLIT = 9; // floatLiteral
    public static final int T_DOUBLELIT = 10; // doubleLiteral
    public static final int T_ASSIGN = 11; // =
    public static final int T_INCR = 12; // ++
    public static final int T_PLUS = 13; // +
    public static final int T_MINUS = 14; // -
    public static final int T_STAR = 15; // *
    public static final int T_SLASH = 16; // /
    public static final int T_EQ = 17; // ==
    public static final int T_NEQ = 18; // !=
    public static final int T_GT = 19; // >
    public static final int T_LT = 20; // <
    public static final int T_GE = 21; // >=
    public static final int T_LE = 22; // <=
    public static final int T_SEMI = 23; // ;
    public static final int T_LPAREN = 24; // (
    public static final int T_RPAREN = 25; // )
    public static final int T_LBRACE = 26; // {
    public static final int T_RBRACE = 27; // }
    public static final int T_EOF = 28; // end of input
    public static final int NUM_TERMINALS = 29;

    public static final int N_BLOCK = 0;
    public static final int N_STMTLST = 1;
    public static final int N_STMT = 2;
    public static final int N_DECL = 3;
    public static final int N_TYPE = 4;
    public static final int N_INIT = 5;
    public static final int N_INITBODY = 6;
    public static final int N_ASSIGNSTMT = 7;
    public static final int N_ASSIGNEND = 8;
    public static final int N_FORSTMT = 9;
    public static final int N_WHILESTMT = 10;
    public static final int N_IFSTMT = 11;
    public static final int N_ELSE = 12;
    public static final int N_COND = 13;
    public static final int N_CONDEND = 14;
    public static final int N_RELOP = 15;
    public static final int N_EXPR = 16;
    public static final int N_EXPREND = 17;
    public static final int N_TERM = 18;
    public static final int N_TERMEND = 19;
    public static final int N_FACTOR = 20;
    public static final int NUM_NONTERMINALS = 21;

    public static final int START_SYMBOL = N_BLOCK;

    public static final int TERMINAL_BASE = 100;

    public static final int NONE = -1;

    public static final int NUM_PRODUCTIONS = 47;

    public static final Map<String, Integer> TERMINAL_OF_TOKEN = new HashMap<>();

    public static final String[] TERMINAL_NAMES = new String[NUM_TERMINALS];

    public static final String[] NONTERMINAL_NAMES = new String[NUM_NONTERMINALS];

    public static final int[][] PRODUCTIONS = new int[NUM_PRODUCTIONS][];

    public static final String[] PRODUCTION_TEXT = new String[NUM_PRODUCTIONS];

    public static final int[][] PARSE_TABLE = new int[NUM_NONTERMINALS][NUM_TERMINALS];

    static {
        name(T_INT, "int", "int_kwd");
        name(T_FLOAT, "float", "float_kwd");
        name(T_DOUBLE, "double", "dble_kwd");
        name(T_IF, "if", "if_kwd");
        name(T_ELSE, "else", "else_kwd");
        name(T_WHILE, "while", "whle_kwd");
        name(T_FOR, "for", "for_loop_kwd");
        name(T_VAR, "variable_name", "variable_name");
        name(T_INTLIT, "intLiteral", "intLiteral");
        name(T_FLOATLIT, "floatLiteral", "floatLiteral");
        name(T_DOUBLELIT, "doubleLiteral", "doubleLiteral");
        name(T_ASSIGN, "=", "cmprsn_optr");
        name(T_INCR, "++", "incr_optr");
        name(T_PLUS, "+", "add_optr");
        name(T_MINUS, "-", "subtract_optr");
        name(T_STAR, "*", "mult_optr");
        name(T_SLASH, "/", "div_optr");
        name(T_EQ, "==", "eql_optr");
        name(T_NEQ, "!=", "noteq_optr");
        name(T_GT, ">", "Grtr_op");
        name(T_LT, "<", "lsthn_optr");
        name(T_GE, ">=", "grtrthneq_optr");
        name(T_LE, "<=", "lsthaneqto_optr");
        name(T_SEMI, ";", "terminator_op");
        name(T_LPAREN, "(", "lparen_op");
        name(T_RPAREN, ")", "rparen_op");
        name(T_LBRACE, "{", "lbrace_op");
        name(T_RBRACE, "}", "rbrace_op");
        TERMINAL_NAMES[T_EOF] = "end of input";

        NONTERMINAL_NAMES[N_BLOCK] = "Block";
        NONTERMINAL_NAMES[N_STMTLST] = "StmtLst";
        NONTERMINAL_NAMES[N_STMT] = "Stmt";
        NONTERMINAL_NAMES[N_DECL] = "Decl";
        NONTERMINAL_NAMES[N_TYPE] = "Type";
        NONTERMINAL_NAMES[N_INIT] = "Init";
        NONTERMINAL_NAMES[N_INITBODY] = "InitBody";
        NONTERMINAL_NAMES[N_ASSIGNSTMT] = "AssignStmt";
        NONTERMINAL_NAMES[N_ASSIGNEND] = "AssignEnd";
        NONTERMINAL_NAMES[N_FORSTMT] = "ForStmt";
        NONTERMINAL_NAMES[N_WHILESTMT] = "WhileStmt";
        NONTERMINAL_NAMES[N_IFSTMT] = "IfStmt";
        NONTERMINAL_NAMES[N_ELSE] = "Else";
        NONTERMINAL_NAMES[N_COND] = "Cond";
        NONTERMINAL_NAMES[N_CONDEND] = "CondEnd";
        NONTERMINAL_NAMES[N_RELOP] = "RelationOp";
        NONTERMINAL_NAMES[N_EXPR] = "Expr";
        NONTERMINAL_NAMES[N_EXPREND] = "ExprEnd";
        NONTERMINAL_NAMES[N_TERM] = "Term";
        NONTERMINAL_NAMES[N_TERMEND] = "TermEnd";
        NONTERMINAL_NAMES[N_FACTOR] = "Factor";

        prod(0, "Block -> { StmtLst }", t(T_LBRACE), N_STMTLST, t(T_RBRACE));
        prod(1, "StmtLst -> Stmt StmtLst", N_STMT, N_STMTLST);
        prod(2, "StmtLst -> epsilon");
        prod(3, "Stmt -> Decl ;", N_DECL, t(T_SEMI));
        prod(4, "Stmt -> AssignStmt ;", N_ASSIGNSTMT, t(T_SEMI));
        prod(5, "Stmt -> ForStmt", N_FORSTMT);
        prod(6, "Stmt -> WhileStmt", N_WHILESTMT);
        prod(7, "Stmt -> IfStmt", N_IFSTMT);
        prod(8, "Decl -> Type variable_name Init", N_TYPE, t(T_VAR), N_INIT);
        prod(9, "Type -> int", t(T_INT));
        prod(10, "Type -> float", t(T_FLOAT));
        prod(11, "Type -> double", t(T_DOUBLE));
        prod(12, "Init -> = Expr", t(T_ASSIGN), N_EXPR);
        prod(13, "Init -> { InitBody }", t(T_LBRACE), N_INITBODY, t(T_RBRACE));
        prod(14, "Init -> epsilon");
        prod(15, "InitBody -> Expr", N_EXPR);
        prod(16, "InitBody -> epsilon");
        prod(17, "AssignStmt -> variable_name AssignEnd", t(T_VAR), N_ASSIGNEND);
        prod(18, "AssignEnd -> = Expr", t(T_ASSIGN), N_EXPR);
        prod(19, "AssignEnd -> ++", t(T_INCR));
        prod(20, "ForStmt -> for ( Decl ; Cond ; AssignStmt ) Block",
                t(T_FOR), t(T_LPAREN), N_DECL, t(T_SEMI), N_COND, t(T_SEMI),
                N_ASSIGNSTMT, t(T_RPAREN), N_BLOCK);
        prod(21, "WhileStmt -> while ( Cond ) Block",
                t(T_WHILE), t(T_LPAREN), N_COND, t(T_RPAREN), N_BLOCK);
        prod(22, "IfStmt -> if ( Cond ) Block Else",
                t(T_IF), t(T_LPAREN), N_COND, t(T_RPAREN), N_BLOCK, N_ELSE);
        prod(23, "Else -> else Block", t(T_ELSE), N_BLOCK);
        prod(24, "Else -> epsilon");
        prod(25, "Cond -> Expr CondEnd", N_EXPR, N_CONDEND);
        prod(26, "CondEnd -> RelationOp Expr", N_RELOP, N_EXPR);
        prod(27, "CondEnd -> epsilon");
        prod(28, "RelationOp -> ==", t(T_EQ));
        prod(29, "RelationOp -> !=", t(T_NEQ));
        prod(30, "RelationOp -> >", t(T_GT));
        prod(31, "RelationOp -> <", t(T_LT));
        prod(32, "RelationOp -> >=", t(T_GE));
        prod(33, "RelationOp -> <=", t(T_LE));
        prod(34, "Expr -> Term ExprEnd", N_TERM, N_EXPREND);
        prod(35, "ExprEnd -> + Term ExprEnd", t(T_PLUS), N_TERM, N_EXPREND);
        prod(36, "ExprEnd -> - Term ExprEnd", t(T_MINUS), N_TERM, N_EXPREND);
        prod(37, "ExprEnd -> epsilon");
        prod(38, "Term -> Factor TermEnd", N_FACTOR, N_TERMEND);
        prod(39, "TermEnd -> * Factor TermEnd", t(T_STAR), N_FACTOR, N_TERMEND);
        prod(40, "TermEnd -> / Factor TermEnd", t(T_SLASH), N_FACTOR, N_TERMEND);
        prod(41, "TermEnd -> epsilon");
        prod(42, "Factor -> variable_name", t(T_VAR));
        prod(43, "Factor -> intLiteral", t(T_INTLIT));
        prod(44, "Factor -> floatLiteral", t(T_FLOATLIT));
        prod(45, "Factor -> doubleLiteral", t(T_DOUBLELIT));
        prod(46, "Factor -> ( Expr )", t(T_LPAREN), N_EXPR, t(T_RPAREN));

        for (int[] row : PARSE_TABLE)
            Arrays.fill(row, NONE);

        set(N_BLOCK, 0, T_LBRACE);

        set(N_STMTLST, 1, T_INT, T_FLOAT, T_DOUBLE, T_VAR, T_FOR, T_WHILE, T_IF);
        set(N_STMTLST, 2, T_RBRACE);

        set(N_STMT, 3, T_INT, T_FLOAT, T_DOUBLE);
        set(N_STMT, 4, T_VAR);
        set(N_STMT, 5, T_FOR);
        set(N_STMT, 6, T_WHILE);
        set(N_STMT, 7, T_IF);

        set(N_DECL, 8, T_INT, T_FLOAT, T_DOUBLE);

        set(N_TYPE, 9, T_INT);
        set(N_TYPE, 10, T_FLOAT);
        set(N_TYPE, 11, T_DOUBLE);

        set(N_INIT, 12, T_ASSIGN);
        set(N_INIT, 13, T_LBRACE);
        set(N_INIT, 14, T_SEMI);

        set(N_INITBODY, 15, T_VAR, T_INTLIT, T_FLOATLIT, T_DOUBLELIT, T_LPAREN);
        set(N_INITBODY, 16, T_RBRACE);

        set(N_ASSIGNSTMT, 17, T_VAR);

        set(N_ASSIGNEND, 18, T_ASSIGN);
        set(N_ASSIGNEND, 19, T_INCR);

        set(N_FORSTMT, 20, T_FOR);
        set(N_WHILESTMT, 21, T_WHILE);
        set(N_IFSTMT, 22, T_IF);

        set(N_ELSE, 23, T_ELSE);
        set(N_ELSE, 24, T_INT, T_FLOAT, T_DOUBLE, T_VAR, T_FOR, T_WHILE, T_IF, T_RBRACE);

        set(N_COND, 25, T_VAR, T_INTLIT, T_FLOATLIT, T_DOUBLELIT, T_LPAREN);

        set(N_CONDEND, 26, T_EQ, T_NEQ, T_GT, T_LT, T_GE, T_LE);
        set(N_CONDEND, 27, T_SEMI, T_RPAREN);

        set(N_RELOP, 28, T_EQ);
        set(N_RELOP, 29, T_NEQ);
        set(N_RELOP, 30, T_GT);
        set(N_RELOP, 31, T_LT);
        set(N_RELOP, 32, T_GE);
        set(N_RELOP, 33, T_LE);

        set(N_EXPR, 34, T_VAR, T_INTLIT, T_FLOATLIT, T_DOUBLELIT, T_LPAREN);

        set(N_EXPREND, 35, T_PLUS);
        set(N_EXPREND, 36, T_MINUS);
        set(N_EXPREND, 37, T_EQ, T_NEQ, T_GT, T_LT, T_GE, T_LE,
                T_SEMI, T_RPAREN, T_RBRACE);

        set(N_TERM, 38, T_VAR, T_INTLIT, T_FLOATLIT, T_DOUBLELIT, T_LPAREN);

        set(N_TERMEND, 39, T_STAR);
        set(N_TERMEND, 40, T_SLASH);
        set(N_TERMEND, 41, T_PLUS, T_MINUS, T_EQ, T_NEQ, T_GT, T_LT, T_GE, T_LE,
                T_SEMI, T_RPAREN, T_RBRACE);

        set(N_FACTOR, 42, T_VAR);
        set(N_FACTOR, 43, T_INTLIT);
        set(N_FACTOR, 44, T_FLOATLIT);
        set(N_FACTOR, 45, T_DOUBLELIT);
        set(N_FACTOR, 46, T_LPAREN);
    }

    public static int t(int terminal) {
        return TERMINAL_BASE + terminal;
    }

    public static boolean isTerminal(int symbol) {
        return symbol >= TERMINAL_BASE;
    }

    public static int terminalOf(int symbol) {
        return symbol - TERMINAL_BASE;
    }

    public static String symbolName(int symbol) {
        return isTerminal(symbol) ? TERMINAL_NAMES[terminalOf(symbol)] : NONTERMINAL_NAMES[symbol];
    }

    public static int terminalOfToken(Token token) {
        return TERMINAL_OF_TOKEN.getOrDefault(token.type, NONE);
    }

    private static void name(int terminal, String display, String tokenType) {
        TERMINAL_NAMES[terminal] = display;
        TERMINAL_OF_TOKEN.put(tokenType, terminal);
    }

    private static void prod(int number, String text, int... rhs) {
        PRODUCTION_TEXT[number] = text;
        PRODUCTIONS[number] = rhs;
    }

    private static void set(int nonterminal, int production, int... terminals) {
        for (int terminal : terminals)
            PARSE_TABLE[nonterminal][terminal] = production;
    }
}
