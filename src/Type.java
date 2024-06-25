public class Type {
    public byte kind;

    public static final byte BOOL = 0, INT = 1;

    public Type(byte kind) {
        this.kind = kind;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Type otherType = (Type) other;
        return this.kind == otherType.kind;
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
            throw new IllegalArgumentException("Tipo1 é NULL!");
        }
        if(tipo2 == null) {
            throw new IllegalArgumentException("Tipo2 é NULL!");
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
        if (tipoString == null) {
            throw new IllegalArgumentException("tipoString é NULL!");
        }

        switch (tipoString) {
            case "boolean":
                return new Type(Type.BOOL);
            case "integer":
                return new Type(Type.INT);
            default:
                return null;
        }
    }
}