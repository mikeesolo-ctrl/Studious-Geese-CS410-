public class Token {

    public final String type;  
    public final String lexeme; 
    public final int pos;       

    public Token(String type, String lexeme, int pos) {
        this.type = type;
        this.lexeme = lexeme;
        this.pos = pos;
    }

    @Override
    public String toString() {
        return type + "(" + lexeme + ")";
    }
}
