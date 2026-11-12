import java.util.ArrayList;
import java.util.List;

public class AtomGenerator {

    private final List<Atom> atoms = new ArrayList<>();
    private int nextTemp = 1;
    private int nextLabel = 1;

    public List<Atom> generate(ParseNode block) {
        genBlock(block);
        return atoms;
    }

    private void genBlock(ParseNode node) {
        genStmtLst(node.child(1));
    }

    private void genStmtLst(ParseNode node) {
        while (node.production == 1) {
            genStmt(node.child(0));
            node = node.child(1);
        }
    }

    private void genStmt(ParseNode node) {
        switch (node.production) {
            case 3 -> genDecl(node.child(0));
            case 4 -> genAssignStmt(node.child(0));
            case 5 -> genForStmt(node.child(0));
            case 6 -> genWhileStmt(node.child(0));
            case 7 -> genIfStmt(node.child(0));
            default -> throw new IllegalStateException("unexpected Stmt production " + node.production);
        }
    }

    private void genDecl(ParseNode node) {
        String variable = node.child(1).token.lexeme;
        genInit(node.child(2), variable);
    }

    private void genInit(ParseNode node, String variable) {
        if (node.production == 12) {
            emit(Atom.mov(genExpr(node.child(1)), variable));
        } else if (node.production == 13) {
            ParseNode body = node.child(1);
            if (body.production == 15) {
                emit(Atom.mov(genExpr(body.child(0)), variable));
            }
        }
    }

    private void genAssignStmt(ParseNode node) {
        String variable = node.child(0).token.lexeme;
        ParseNode end = node.child(1);

        if (end.production == 18) {
            emit(Atom.mov(genExpr(end.child(1)), variable));
        } else {
            emit(Atom.add(variable, "1", variable));
        }
    }

    private void genForStmt(ParseNode node) {
        String top = newLabel();
        String done = newLabel();

        genDecl(node.child(2));
        emit(Atom.lbl(top));
        genCond(node.child(4), done);
        genBlock(node.child(8));
        genAssignStmt(node.child(6));
        emit(Atom.jmp(top));
        emit(Atom.lbl(done));
    }

    private void genWhileStmt(ParseNode node) {
        String top = newLabel();
        String done = newLabel();

        emit(Atom.lbl(top));
        genCond(node.child(2), done);
        genBlock(node.child(4));
        emit(Atom.jmp(top));
        emit(Atom.lbl(done));
    }

    private void genIfStmt(ParseNode node) {
        ParseNode elseNode = node.child(5);
        boolean hasElse = elseNode.production == 23;
        String skipThen = newLabel();
        genCond(node.child(2), skipThen);
        genBlock(node.child(4));

        if (hasElse) {
            String done = newLabel();
            emit(Atom.jmp(done));
            emit(Atom.lbl(skipThen));
            genBlock(elseNode.child(1));
            emit(Atom.lbl(done));
        } else {
            emit(Atom.lbl(skipThen));
        }
    }

    private void genCond(ParseNode node, String falseLabel) {
        String left = genExpr(node.child(0));
        ParseNode end = node.child(1);

        if (end.production == 26) {
            int comparison = comparisonOf(end.child(0));
            String right = genExpr(end.child(1));
            emit(Atom.tst(left, right, invert(comparison), falseLabel));
        } else {
            emit(Atom.tst(left, "0", Atom.CMP_EQUAL, falseLabel));
        }
    }

    private int comparisonOf(ParseNode relationOp) {
        return switch (relationOp.production) {
            case 28 -> Atom.CMP_EQUAL;
            case 29 -> Atom.CMP_UNEQUAL;
            case 30 -> Atom.CMP_GREATER;
            case 31 -> Atom.CMP_LESSER;
            case 32 -> Atom.CMP_GREATER_OR_EQUAL;
            case 33 -> Atom.CMP_LESSER_OR_EQUAL;
            default -> throw new IllegalStateException("unexpected RelationOp production "
                    + relationOp.production);
        };
    }

    private int invert(int comparison) {
        return switch (comparison) {
            case Atom.CMP_EQUAL -> Atom.CMP_UNEQUAL;
            case Atom.CMP_UNEQUAL -> Atom.CMP_EQUAL;
            case Atom.CMP_LESSER -> Atom.CMP_GREATER_OR_EQUAL;
            case Atom.CMP_GREATER_OR_EQUAL -> Atom.CMP_LESSER;
            case Atom.CMP_GREATER -> Atom.CMP_LESSER_OR_EQUAL;
            case Atom.CMP_LESSER_OR_EQUAL -> Atom.CMP_GREATER;
            default -> throw new IllegalStateException("unexpected comparison " + comparison);
        };
    }

    private String genExpr(ParseNode node) {
        String result = genTerm(node.child(0));
        ParseNode end = node.child(1);

        while (end.production == 35 || end.production == 36) {
            String right = genTerm(end.child(1));
            String temp = newTemp();
            emit(end.production == 35 ? Atom.add(result, right, temp)
                    : Atom.sub(result, right, temp));
            result = temp;
            end = end.child(2);
        }
        return result;
    }

    private String genTerm(ParseNode node) {
        String result = genFactor(node.child(0));
        ParseNode end = node.child(1);

        while (end.production == 39 || end.production == 40) {
            String right = genFactor(end.child(1));
            String temp = newTemp();
            emit(end.production == 39 ? Atom.mul(result, right, temp)
                    : Atom.div(result, right, temp));
            result = temp;
            end = end.child(2);
        }
        return result;
    }

    private String genFactor(ParseNode node) {
        if (node.production == 46)
            return genExpr(node.child(1));
        return node.child(0).token.lexeme;
    }

    private void emit(Atom atom) {
        atoms.add(atom);
    }

    private String newTemp() {
        return "T" + nextTemp++;
    }

    private String newLabel() {
        return "L" + nextLabel++;
    }
}
