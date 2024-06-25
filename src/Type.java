public class Type {
    public byte kind;

    public static final byte BOOL = 0, INT = 1;

    public Type(byte kind) {
        this.kind = kind;
    }

    public boolean equals(Object other) {
        Type otherType = (Type) other;

        return (this.kind == otherType.kind);
    }

    public static String convertTypeToString(byte tipo) {
        switch(tipo) {
            case BOOL:
                return "BOOLEAN";
            case INT:
                return "INTEGER";
            default:
                return "null";
        }
    }

    public static Type evaluate(
        Type tipo1, Type tipo2, Node.NodeOperador operador
    ) {
        if(tipo1 == null) {
            System.out.println("Tipo1 é NULL!");
        }
        if(tipo2 == null) {
            System.out.println("Tipo2 é NULL!");
        }

        byte kind1 = tipo1.kind;
        byte kind2 = tipo2.kind;

        // REGRAS
        // (boolean e integer)
        // (relacional(==), multiplicativo(*) e aditivo(+))

        // integer + integer = integer
        // integer * integer = integer
        // integer == integer = bool

        // OBS.: O BOOLEANO SÓ SE RELACIONA COM UM BOLEANO
        // E UM OPERADOR RELACIONAL
        if (kind1 == kind2 && (operador.operador == Token.IGUAL || 
            operador.operador == Token.MAIOR ||
            operador.operador == Token.MENOR)) {
            return new Type(Type.BOOL);
        }
        // INTEIRO
        if (kind1 != Type.BOOL && kind2 != Type.BOOL) {
            // CASO SEJA UMA OPERAÇÃO RELACIONAL
            if (operador.operador == Token.IGUAL || 
                operador.operador == Token.MAIOR ||
                operador.operador == Token.MENOR) {
                return new Type(Type.BOOL);
            } else if (kind1 == kind2) {
                return new Type(kind1);
            }
        }
        return null;

    }

    public static Type evaluateString(String tipoString) {
        Type tipo = null;

        if (tipoString.equals("boolean")) {
            tipo = new Type(Type.BOOL);

        } else if (tipoString.equals("integer")) {
            tipo = new Type(Type.INT);

        }
        return tipo;
    }
}