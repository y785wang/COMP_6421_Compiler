import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

class FirstFollowSet {

    private static FirstFollowSet instance;
    private HashMap<String, HashSet<String>> firstSets;
    private HashMap<String, HashSet<String>> followSets;

    private FirstFollowSet() {
        firstSets = new HashMap<>();
//        firstSets.put("prog", new HashSet<>(Arrays.asList("main","class","integer","float","id","EPSILON")));
//        firstSets.put("classDeclRpt", new HashSet<>(Arrays.asList("class","EPSILON")));
//        firstSets.put("funcDefRpt", new HashSet<>(Arrays.asList("integer","float","id","EPSILON")));
//        firstSets.put("classDecl", new HashSet<>(Arrays.asList("class")));
//        firstSets.put("parentDeclOpt", new HashSet<>(Arrays.asList(":","EPSILON")));
//        firstSets.put("parentRpt", new HashSet<>(Arrays.asList(",","EPSILON")));
//        firstSets.put("varDeclRpt", new HashSet<>(Arrays.asList("integer","float","id","EPSILON")));
//        firstSets.put("funcDeclRpt", new HashSet<>(Arrays.asList("integer","float","id","EPSILON")));
//        firstSets.put("scopeOpt", new HashSet<>(Arrays.asList("id","EPSILON")));
//        firstSets.put("funcBody", new HashSet<>(Arrays.asList("{")));
//        firstSets.put("statementRpt", new HashSet<>(Arrays.asList("if","for","read","write","return","id","EPSILON")));
//        firstSets.put("arraySizeRpt", new HashSet<>(Arrays.asList("[","EPSILON")));
//        firstSets.put("statement", new HashSet<>(Arrays.asList("if","for","read","write","return","id","EPSILON")));
//        firstSets.put("statBlock", new HashSet<>(Arrays.asList("{","if","for","read","write","return","id","EPSILON")));
//        firstSets.put("arithExprLR", new HashSet<>(Arrays.asList("+","-","or","EPSILON")));
//        firstSets.put("sign", new HashSet<>(Arrays.asList("+","-")));
//        firstSets.put("termLR", new HashSet<>(Arrays.asList("*","/","and","EPSILON")));
//        firstSets.put("factor", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-","EPSILON")));
//        firstSets.put("variable", new HashSet<>(Arrays.asList("id","EPSILON")));
//        firstSets.put("idnestRpt", new HashSet<>(Arrays.asList("id","EPSILON")));
//        firstSets.put("indiceRpt", new HashSet<>(Arrays.asList("[","EPSILON")));
//        firstSets.put("functionCall", new HashSet<>(Arrays.asList("id","EPSILON")));
//        firstSets.put("idnest", new HashSet<>(Arrays.asList("id")));
//        firstSets.put("idnestNonD", new HashSet<>(Arrays.asList(",","(","[","EPSILON")));
//        firstSets.put("indice", new HashSet<>(Arrays.asList("[")));
//        firstSets.put("arraySize", new HashSet<>(Arrays.asList("[")));
//        firstSets.put("type", new HashSet<>(Arrays.asList("integer","float","id")));
//        firstSets.put("fParams", new HashSet<>(Arrays.asList("integer","float","id","EPSILON")));
//        firstSets.put("fParamsTailRpt", new HashSet<>(Arrays.asList(",","EPSILON")));
//        firstSets.put("aParams", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-","EPSILON")));
//        firstSets.put("aParamsTailRpt", new HashSet<>(Arrays.asList(",","EPSILON")));
//        firstSets.put("fParamsTail", new HashSet<>(Arrays.asList(",")));
//        firstSets.put("aParamsTail", new HashSet<>(Arrays.asList(",")));
//        firstSets.put("assignOp", new HashSet<>(Arrays.asList("=")));
//        firstSets.put("relOp", new HashSet<>(Arrays.asList("eq","neq","lt","gt","leq","geq")));
//        firstSets.put("addOp", new HashSet<>(Arrays.asList("+","-","or")));
//        firstSets.put("multOp", new HashSet<>(Arrays.asList("*","/","and")));
//        firstSets.put("funcDecl", new HashSet<>(Arrays.asList("integer","float","id")));
//        firstSets.put("funcHead", new HashSet<>(Arrays.asList("integer","float","id")));
//        firstSets.put("varDecl", new HashSet<>(Arrays.asList("integer","float","id")));
//        firstSets.put("funcDef", new HashSet<>(Arrays.asList("integer","float","id")));
//        firstSets.put("assignStat", new HashSet<>(Arrays.asList("id","EPSILON")));
//        firstSets.put("term", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-","EPSILON")));
//        firstSets.put("arithExpr", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-","EPSILON")));
//        firstSets.put("expr", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-","EPSILON")));
//        firstSets.put("relExpr", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-","EPSILON")));
        firstSets.put("E", new HashSet<>(Arrays.asList("(","0","1")));
        firstSets.put("E'", new HashSet<>(Arrays.asList("+","EPSILON")));
        firstSets.put("T", new HashSet<>(Arrays.asList("(","0","1")));
        firstSets.put("T'", new HashSet<>(Arrays.asList("*","EPSILON")));
        firstSets.put("F", new HashSet<>(Arrays.asList("(","0","1")));

        followSets = new HashMap<>();
//        followSets.put("prog", new HashSet<>(Arrays.asList("$")));
//        followSets.put("classDeclRpt", new HashSet<>(Arrays.asList("main","integer","float","id")));
//        followSets.put("funcDefRpt", new HashSet<>(Arrays.asList("main")));
//        followSets.put("classDecl", new HashSet<>(Arrays.asList("class","main","integer","float","id")));
//        followSets.put("parentDeclOpt", new HashSet<>(Arrays.asList("{")));
//        followSets.put("parentRpt", new HashSet<>(Arrays.asList("{")));
//        followSets.put("varDeclRpt", new HashSet<>(Arrays.asList("if","for","read","write","return","id",":","integer","float")));
//        followSets.put("funcDeclRpt", new HashSet<>(Arrays.asList("}")));
//        followSets.put("funcDecl", new HashSet<>(Arrays.asList("integer","float","id","}")));
//        followSets.put("funcHead", new HashSet<>(Arrays.asList("{")));
//        followSets.put("scopeOpt", new HashSet<>(Arrays.asList("id")));
//        followSets.put("funcDef", new HashSet<>(Arrays.asList("integer","float","id","main")));
//        followSets.put("funcBody", new HashSet<>(Arrays.asList(";")));
//        followSets.put("statementRpt", new HashSet<>(Arrays.asList("}")));
//        followSets.put("varDecl", new HashSet<>(Arrays.asList("integer","float","id","if","for","read","write","return",":")));
//        followSets.put("arraySizeRpt", new HashSet<>(Arrays.asList(",",";",")")));
//        followSets.put("statement", new HashSet<>(Arrays.asList("if","for","read","write","return","id","else",";","}")));
//        followSets.put("assignStat", new HashSet<>(Arrays.asList(";",")")));
//        followSets.put("statBlock", new HashSet<>(Arrays.asList("else",";")));
//        followSets.put("expr", new HashSet<>(Arrays.asList(",",")",";")));
//        followSets.put("relExpr", new HashSet<>(Arrays.asList(";",",",")")));
//        followSets.put("arithExpr", new HashSet<>(Arrays.asList("]",")","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("arithExprLR", new HashSet<>(Arrays.asList("]",")","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("sign", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-")));
//        followSets.put("term", new HashSet<>(Arrays.asList("+","-","or","]",")","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("termLR", new HashSet<>(Arrays.asList("+","-","or","]",")","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("factor", new HashSet<>(Arrays.asList("*","/","and","+","-","or","]", ")","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("variable", new HashSet<>(Arrays.asList("=",")","*","/","and","+","-","or","]","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("idnestRpt", new HashSet<>(Arrays.asList("id")));
//        followSets.put("indiceRpt", new HashSet<>(Arrays.asList(".","=",")","*","/","and","+","-","or","]","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("functionCall", new HashSet<>(Arrays.asList("*","/","and","+","-","or","]","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("idnest", new HashSet<>(Arrays.asList("id")));
//        followSets.put("idnestNonD", new HashSet<>(Arrays.asList("id")));
//        followSets.put("indice", new HashSet<>(Arrays.asList("[",".","=",")","*","/","and","+","-","or","]","eq","neq","lt","gt","leq","geq",";",",")));
//        followSets.put("arraySize", new HashSet<>(Arrays.asList("[",",",";",")")));
//        followSets.put("type", new HashSet<>(Arrays.asList("id")));
//        followSets.put("fParams", new HashSet<>(Arrays.asList(")")));
//        followSets.put("fParamsTailRpt", new HashSet<>(Arrays.asList(")")));
//        followSets.put("aParams", new HashSet<>(Arrays.asList(")")));
//        followSets.put("aParamsTailRpt", new HashSet<>(Arrays.asList(")")));
//        followSets.put("fParamsTail", new HashSet<>(Arrays.asList(",",")")));
//        followSets.put("aParamsTail", new HashSet<>(Arrays.asList(",",")")));
//        followSets.put("assignOp", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-")));
//        followSets.put("relOp", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-")));
//        followSets.put("addOp", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-")));
//        followSets.put("multOp", new HashSet<>(Arrays.asList("intNum","floatNum","(","not","id","+","-")));
        followSets.put("E", new HashSet<>(Arrays.asList("$",")")));
        followSets.put("E'", new HashSet<>(Arrays.asList("$",")")));
        followSets.put("T", new HashSet<>(Arrays.asList("$",")","+")));
        followSets.put("T'", new HashSet<>(Arrays.asList("$",")","+")));
        followSets.put("F", new HashSet<>(Arrays.asList("$",")","*","+")));

    }

    static FirstFollowSet getInstance() {
        if (null == instance) instance = new FirstFollowSet();
        return instance;
    }

    boolean belongFirstSet(String symbol, String terminal) { // symbol is x, terminal is a
//        if (symbol.equals("$"))
        TerminalSet terminalSet = TerminalSet.getInstance();
        if (terminalSet.isTerminal(symbol) || symbol.equals("EPSILON")) { // TODO ???
            // first set contain only the symbol itself
            return symbol.equals(terminal);
        } else {
            return firstSets.get(symbol).contains(terminal);
        }
    }

    boolean belongFollowSet(String symbol, String nonTerminal) {
        if (symbol.equals("$")) return false;
        return followSets.get(symbol).contains(nonTerminal);
    }
}
