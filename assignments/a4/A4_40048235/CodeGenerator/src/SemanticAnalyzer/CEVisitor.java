package SemanticAnalyzer;

import SyntacticAnalyzer.ASTNode;
import Error.Error;

import java.util.HashSet;

// Construct Expression Visitor
public class CEVisitor extends Visitor {
    private SymbolTable globalSymbolTable;

    public CEVisitor(SymbolTable globalSymbolTable) { this.globalSymbolTable = globalSymbolTable; }

    public void visit(ASTNode node) {
//        System.out.println("Visiting " + node.getType() + " " + node.getValue() + "...");
        String nodeType = node.getType();
        switch (nodeType) {
            case "prog" : {
                ASTNode statBlock = node.getChildren().getSibling().getSibling();
                SymbolTable programSymbolTable = new SymbolTable("main");
                for (SymbolTableRecord record : statBlock.getSymbolTable().getSymbolTableRecords()) programSymbolTable.insert(record);

                SymbolTableRecord symbolTableRecord = new SymbolTableRecord("main", "main", "", programSymbolTable);
                globalSymbolTable.insert(symbolTableRecord);
                node.setSymbolTable(globalSymbolTable);
                break;
            }
            case "classList": {
                globalSymbolTable.checkClassInherExistence();
                break;
            }
            case "funcDefList": {
                globalSymbolTable.checkFuncDefExistence();
                break;
            }
            case "classDecl": {
                SymbolTable symbolTable = new SymbolTable(node.getChildren().getValue());

                ASTNode inherList = node.getChildren().getSibling();        // get inherList
                String inherListType = getClassInherListType(inherList);    // ":" is included
                symbolTable.setInher(inherListType);

                ASTNode memberList = inherList.getSibling();                // get memberList
                ASTNode memberListChild = memberList.getChildren();
                while (null != memberListChild) {
                    symbolTable.insert(memberListChild.getSymbolTableRecord());
                    memberListChild = memberListChild.getSibling();
                }

                node.setSymbolTable(symbolTable);
                SymbolTableRecord symbolTableRecord = new SymbolTableRecord(node.getChildren().getValue(), "class", "", node.getSymbolTable());
                node.setSymbolTableRecord(symbolTableRecord);
                globalSymbolTable.insert(symbolTableRecord);
                break;
            }
            case "varDecl": {
                ASTNode tempChild = node.getChildren(); // get var type
                String type = tempChild.getValue();

                tempChild = tempChild.getSibling();     // get var name
                String name = tempChild.getValue();

                tempChild = tempChild.getSibling();     // get var dimList
                String dimListType = getDimListType(tempChild);

                node.setSymbolTableRecord(new SymbolTableRecord(name, "variable", type + dimListType, node.getSymbolTable()));
//                if (type.contains("integer")) {
//                    node.getSymbolTableRecord().setMemorySize(tempChild.getDimension() * 4);
//                } else if (type.contains("float")) {
//                    node.getSymbolTableRecord().setMemorySize(tempChild.getDimension() * 8);
//                } else {
//                    // TODO: type is class, check global table
//                }
                break;
            }
            case "funcDecl": {
                ASTNode tempChild = node.getChildren(); // get func return type
                String type = tempChild.getValue();

                tempChild = tempChild.getSibling();     // get func name
                String name = tempChild.getValue();

                tempChild = tempChild.getSibling();     // get func paramList
                String fparmListType = getFuncParamListType(tempChild);

                node.setSymbolTableRecord(new SymbolTableRecord(name, "function", type+fparmListType, node.getSymbolTable()));
                break;
            }
            case "funcDef": {
                ASTNode typeNode = node.getChildren();                      // get func return type
                String returnType = typeNode.getValue();

                ASTNode scopeSpecNode = node.getChildren().getSibling();    // get func scopeSpec

                ASTNode idNode = scopeSpecNode.getSibling();                // get func name
                String tableName = idNode.getValue();
                SymbolTable symbolTable = new SymbolTable(tableName);
                symbolTable.setReturnType(returnType);

                ASTNode fparamList = idNode.getSibling();                   // get func paramList
                String fparamListType = getFuncParamListType(fparamList);
                ASTNode fparam = fparamList.getChildren();
                while (null != fparam) {
                    symbolTable.insert(fparam.getSymbolTableRecord());
                    fparam = fparam.getSibling();
                }

                ASTNode statBlock = fparamList.getSibling();                // get func body
                for (SymbolTableRecord record : statBlock.getSymbolTable().getSymbolTableRecords()) symbolTable.insert(record);

                node.setSymbolTable(symbolTable);
                if (null != scopeSpecNode.getChildren()) { // link member function to a class table
                    String scopeSpecName = scopeSpecNode.getChildren().getValue();
                    symbolTable.setScopeSpec(scopeSpecName);
                    if (!symbolTable.linkMemberFunction(scopeSpecName, globalSymbolTable)) {
                        System.out.println("|\n|  cev, invalid member function " + tableName +  ", no declaration in class " + scopeSpecName);
                        SymbolTableRecord tempRecord = new SymbolTableRecord(tableName, "function", returnType+fparamListType, node.getSymbolTable());
                        System.out.println("|  " + tempRecord);
                    }
                } else { // insert record to global symbol table for free function
                    SymbolTableRecord symbolTableRecord = new SymbolTableRecord(tableName, "function", returnType+fparamListType, node.getSymbolTable());
                    node.setSymbolTableRecord(symbolTableRecord);
                    globalSymbolTable.insert(symbolTableRecord);
                }
                break;
            }
            case "statBlock": {
                ASTNode varDeclNode = node.getChildren();
                SymbolTable symbolTable = new SymbolTable("statBlock");
                while (null != varDeclNode) {
                    if (varDeclNode.getType().equals("varDecl")) {
                        symbolTable.insertWithoutErrorCheck(varDeclNode.getSymbolTableRecord());
                    } else {
                        // check undeclared variable for funDef and main only
                        String parentNodeType = node.getParent().getType();
                        if (parentNodeType.equals("funcDef") || parentNodeType.equals("prog")) {
                            HashSet<String> varDeclCheckList = new HashSet<>();
                            for (SymbolTableRecord record : symbolTable.getSymbolTableRecords()) { varDeclCheckList.add(record.getName()); }
                            for (SymbolTableRecord record : globalSymbolTable.getSymbolTableRecords()) { varDeclCheckList.add(record.getName()); }
                            if (parentNodeType.equals("funcDef")) {
                                // add function parameters as new varDecl
                                ASTNode funcScopeSpecNode = varDeclNode.getParent().getParent().getChildren().getSibling();
                                ASTNode funcNameNode = funcScopeSpecNode.getSibling();
                                ASTNode fParamListNode = funcNameNode.getSibling();
                                ASTNode fParamNode = fParamListNode.getChildren();
                                while (null != fParamNode) {
                                    varDeclCheckList.add(fParamNode.getChildren().getSibling().getValue());
                                    fParamNode = fParamNode.getSibling();
                                }

                                // add inher class varDecl
                                if (null != funcScopeSpecNode.getChildren()) {
                                    String scopeSpecName = funcScopeSpecNode.getChildren().getValue();
                                    addInerClassVarDecl(varDeclCheckList, scopeSpecName);
                                }
                            }
                            findUndeclaredVariable(varDeclCheckList, varDeclNode.getParent());
                        }
                        break;
                    }
                    varDeclNode = varDeclNode.getSibling();
                }
                node.setSymbolTable(symbolTable);
            }
        }
    }

    // when this function is call at the beginning, inherClassesName represents the scopeSpec name
    private void addInerClassVarDecl(HashSet<String> varDeclCheckList, String inherClassesName) {
        // TODO: deal with multi-inher class name
        for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()){
            if (globalRecord.getName().equals(inherClassesName)) {
                SymbolTable parentClassSymbolTable = globalRecord.getLink();
                for (SymbolTableRecord parentClassRecord : parentClassSymbolTable.getSymbolTableRecords()) {
                    varDeclCheckList.add(parentClassRecord.getName());
                }
                String furtherInherClasses = parentClassSymbolTable.getInher();
                if (!furtherInherClasses.isEmpty()) {
                    furtherInherClasses = furtherInherClasses.substring(3);
                    addInerClassVarDecl(varDeclCheckList, furtherInherClasses);
                }
                break;
            }
        }
    }

    // symbolTable contains all declared variable record
    private void findUndeclaredVariable(HashSet<String> varDeclCheckList, ASTNode node) {
        if (null == node) return;

        String scope = getScopeMessage(node);

        switch (node.getType()) {
            case "var":
                String varName = node.getChildren().getChildren().getValue();
                if (!varDeclCheckList.contains(varName)) {
//                    System.out.println("|\n|  Undeclared variable " + varName + " in scope" + scope + ", line " + node.getChildren().getChildren().getLineNumber());
                    String errorMessage = "cev, 4.2.10, undeclared variable " + varName;
                    Error.getInstance().insert(node.getChildren().getChildren().getLineNumber(), errorMessage, 4);
                }
                break;
            case "forStat":
                ASTNode newVarDeclNameNode = node.getChildren().getSibling();
                String newVarDeclName = newVarDeclNameNode.getValue();
                varDeclCheckList.add(newVarDeclName);
                while (null != newVarDeclNameNode) {
                    findUndeclaredVariable(varDeclCheckList, newVarDeclNameNode);
                    newVarDeclNameNode = newVarDeclNameNode.getSibling();
                }
                // out of the for scope, remove the additional varDecl
                varDeclCheckList.remove(newVarDeclName);
                break;
            default:
                ASTNode childNode = node.getChildren();
                while (null != childNode) {
                    findUndeclaredVariable(varDeclCheckList, childNode);
                    childNode = childNode.getSibling();
                }
                break;
        }
    }





    private String getClassInherListType(ASTNode inherListNode) {
        StringBuilder inherListType = new StringBuilder();
        String separate = " : ";
        ASTNode inherID = inherListNode.getChildren();
        while (null != inherID) {
            inherListType.append(separate);
            separate = ", ";

            inherListType.append(inherID.getValue());
            inherID = inherID.getSibling();
        }
        return inherListType.toString();
    }
}
