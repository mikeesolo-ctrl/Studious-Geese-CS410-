import java.io.IOException;
import java.io.OutputStream;

public class Instruction {

    public static final int OP_CLR = 1;
    public static final int OP_ADD = 2;

    public static final int IREG0 = 0;
    public static final int IREG1 = 1;
    public static final int IREG2 = 2;
    public static final int IREG3 = 3;
    public static final int IREG4 = 4;
    public static final int IREG5 = 5;
    public static final int IREG6 = 6;
    public static final int IREG7 = 7;
    public static final int FPREG0 = 8;
    public static final int FPREG1 = 9;
    public static final int FPREG2 = 10;
    public static final int FPREG3 = 11;
    public static final int FPREG4 = 12;
    public static final int FPREG5 = 13;
    public static final int FPREG6 = 14;
    public static final int FPREG7 = 15;

    public static final int FIRST_FLOAT_REGISTER = 8;
    public static final int MAX_ADDRESS = 65535;

    private final int word;

    private Instruction(int word) {
        this.word = word;
    }

    public static Instruction clr(int rt) {
        return new Instruction(encode(OP_CLR, rt, 0, 0, 0));
    }

    public static Instruction add(int rt, int rx, int ry) {
        requireSameRegisterKind(rt, rx, ry);
        return new Instruction(encode(OP_ADD, rt, rx, ry, 0));
    }

    public static int encode(int opcode, int rt, int rx, int ry, int address) {
        requireRange(opcode, 15, "opcode");
        requireRange(rt, 15, "transfer register");
        requireRange(rx, 15, "left operand register");
        requireRange(ry, 15, "right operand register");
        requireRange(address, MAX_ADDRESS, "memory address");

        return (opcode << 28) | (rt << 24) | (rx << 20) | (ry << 16) | address;
    }

    public int word() {
        return word;
    }

    public byte[] bytes() {
        return new byte[] {
                (byte) (word >>> 24),
                (byte) (word >>> 16),
                (byte) (word >>> 8),
                (byte) word
        };
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(bytes());
    }

    private static void requireRange(int value, int max, String field) {
        if (value < 0 || value > max) {
            throw new IllegalArgumentException(field + " must be 0 to " + max + ", got " + value);
        }
    }

    private static boolean isIntegerRegister(int register) {
        return register < FIRST_FLOAT_REGISTER;
    }

    private static void requireSameRegisterKind(int rt, int rx, int ry) {
        boolean t = isIntegerRegister(rt);
        if (t != isIntegerRegister(rx) || t != isIntegerRegister(ry)) {
            throw new IllegalArgumentException(
                    "cannot mix integer and floating point registers in one instruction: rt="
                            + rt + ", rx=" + rx + ", ry=" + ry);
        }
    }

    @Override
    public String toString() {
        return String.format("%08X", word);
    }
}