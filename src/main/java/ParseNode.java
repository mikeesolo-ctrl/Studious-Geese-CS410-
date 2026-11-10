import java.util.ArrayList;
import java.util.List;

public class ParseNode {

    public final int symbol;                              
    public Token token;                                   
    public int production = ParseTables.NONE;             
    public final List<ParseNode> children = new ArrayList<>();

    public ParseNode(int symbol) {
        this.symbol = symbol;
    }

    public ParseNode child(int index) {
        return children.get(index);
    }

    public String toTree() {
        StringBuilder out = new StringBuilder();
        append(out, "");
        return out.toString();
    }

    private void append(StringBuilder out, String indent) {
        out.append(indent).append(ParseTables.symbolName(symbol));
        if (token != null) out.append(" = ").append(token.lexeme);
        out.append('\n');
        for (ParseNode kid : children) kid.append(out, indent + "  ");
    }
}
