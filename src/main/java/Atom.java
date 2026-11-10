public class Atom {

    public static final int CMP_EQUAL = 1;
    public static final int CMP_LESSER = 2;
    public static final int CMP_GREATER = 3;
    public static final int CMP_LESSER_OR_EQUAL = 4;
    public static final int CMP_GREATER_OR_EQUAL = 5;
    public static final int CMP_UNEQUAL = 6;

    private static final String BLANK = "_";

    public final String operation;
    public final String left;
    public final String right;
    public final String result;
    public final String comparison;
    public final String destination;

    private Atom(String operation, String left, String right,
            String result, String comparison, String destination) {
        this.operation = operation;
        this.left = left;
        this.right = right;
        this.result = result;
        this.comparison = comparison;
        this.destination = destination;
    }

    public static Atom add(String left, String right, String result) {
        return binary("ADD", left, right, result);
    }

    public static Atom sub(String left, String right, String result) {
        return binary("SUB", left, right, result);
    }

    public static Atom mul(String left, String right, String result) {
        return binary("MUL", left, right, result);
    }

    public static Atom div(String left, String right, String result) {
        return binary("DIV", left, right, result);
    }

    private static Atom binary(String operation, String left, String right, String result) {
        return new Atom(operation, left, right, result, BLANK, BLANK);
    }

    public static Atom neg(String left, String result) {
        return new Atom("NEG", left, BLANK, result, BLANK, BLANK);
    }

    public static Atom mov(String left, String result) {
        return new Atom("MOV", left, BLANK, result, BLANK, BLANK);
    }

    public static Atom jmp(String destination) {
        return new Atom("JMP", BLANK, BLANK, BLANK, BLANK, destination);
    }

    public static Atom lbl(String destination) {
        return new Atom("LBL", BLANK, BLANK, BLANK, BLANK, destination);
    }

    public static Atom tst(String left, String right, int comparison, String destination) {
        return new Atom("TST", left, right, BLANK, Integer.toString(comparison), destination);
    }

    @Override
    public String toString() {
        return "(" + operation + ", " + left + ", " + right + ", "
                + result + ", " + comparison + ", " + destination + ")";
    }
}
