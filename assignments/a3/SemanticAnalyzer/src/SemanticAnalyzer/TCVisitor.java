package SemanticAnalyzer;

import SyntacticAnalyzer.ASTNode;

import java.util.ArrayList;

public class TCVisitor extends Visitor {

    private SymbolTable globalSymbolTable;

    public TCVisitor(SymbolTable globalSymbolTable) { this.globalSymbolTable = globalSymbolTable; }

    public void visit(ASTNode node) {
        String nodeType = node.getType();
        switch (nodeType) {
            case "var":
                ASTNode varElement = node.getChildren();
                String semanticType = "";
                while (null != varElement) {
                    // TODO: check parents, find type from scope func, inher funcs, and global table
                    String id = varElement.getChildren().getValue();
                    if (id.equals("integer") || id.equals("float")) {
                        semanticType = id;
                        break;
                    }
                    if (varElement.getType().equals("fCall")) {
                        // check if it's just a free function call
                        if (node.getParent().getChildren().equal(node)) {
                            // check function paramList with table
                            checkFuncParamList(semanticType, id, varElement);
                            semanticType = findIdType(id, node, semanticType);
                        } else {
                            semanticType = semanticType + "::" + varElement.getChildren().getValue();
                        }
                    } else {
                        semanticType = findIdType(id, node, semanticType);
                    }
                    if (semanticType.equals("Unknown")) break;
                    if ((semanticType.equals("float") || semanticType.equals("integer")) && null != varElement.getSibling()) {
                        semanticType = "Unknown";
                        System.out.println("|\n|  Invalid member call ." + varElement.getSibling().getChildren().getValue() + " in scope" + getScopeMessage(node) + ", variable " + id + " is not a class type");
                        break;
                    }
                    varElement = varElement.getSibling();
                }

                // set member function call semantic type to it's return type
                if (semanticType.contains("::")) {
                    String scopeClassName = semanticType.substring(0, semanticType.indexOf("::"));
                    String functionName = semanticType.substring(semanticType.indexOf("::")+2);
                    for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()) {
                        if (globalRecord.getName().equals(scopeClassName) && globalRecord.getKind().equals("class")) {
                            for (SymbolTableRecord scopeClassRecord : globalRecord.getLink().getSymbolTableRecords()) {
                                if (scopeClassRecord.getName().equals(functionName) && scopeClassRecord.getKind().equals("function")) {
                                    String funcReturnType = scopeClassRecord.getType();
                                    if (funcReturnType.contains(":")) {
                                        funcReturnType = funcReturnType.substring(0, funcReturnType.indexOf(":")-1);
                                    }
                                    semanticType = funcReturnType;
                                }
                            }
                        }
                    }
                }

                node.setSemanticType(semanticType);
                break;
            case "varDecl":
                String className = node.getChildren().getValue();
                if (!className.equals("integer") && !className.equals("float")) {
                    boolean exist = false;
                    for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()) {
                        if (globalRecord.getName().equals(className) && globalRecord.getKind().equals("class")) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) System.out.println("|\n|  Undefined class " + className + " in scope" + getScopeMessage(node));
                }
                break;
            case "expr": case "arithExpr": case "term": case "factor": case "not": case "sign":
                node.setSemanticType(node.getChildren().getSemanticType());
                break;
            case "multOp": case "addOp": case "assignStat": case "relExpr":
                int rightMostChildIndex = nodeType.equals("relExpr") ? 3 : 2;
                String leftChildSemanticType = node.getChildren().getSemanticType();
                String rightChildSemanticType = node.getChildren(rightMostChildIndex).getSemanticType();
                if (leftChildSemanticType.equals("Unknown") || rightChildSemanticType.equals("Unknown")) break;
                if (!leftChildSemanticType.equals(rightChildSemanticType)) {
                    System.out.println("|\n|  Unmatched " + nodeType + " type in scope" + getScopeMessage(node) + ", left is " + leftChildSemanticType + ", right is " + rightChildSemanticType);
                }
                break;
            case "funcDef":
                // check return type
                String declaredReturnType = node.getChildren().getValue();
                String actualReturnType = node.getReturnType();
                String scope = null == node.getChildren(2).getChildren() ? "" : node.getChildren(2).getChildren().getValue() + "::";
                if (null == actualReturnType) {
                    System.out.println("|\n|  Missing returnStat in function " + scope + node.getChildren(3).getValue() + ", expect " + declaredReturnType);
                } else if (!declaredReturnType.equals(actualReturnType)) {
                    System.out.println("|\n|  Unmatched return type in function " + scope + node.getChildren(3).getValue() + ", expect " + declaredReturnType + ", find " + actualReturnType);
                }
                break;
            case "returnStat":
                String returnType = node.getChildren().getSemanticType();
                ASTNode parent = node.getParent();
                while (null != parent) {
                    if (parent.getType().equals("funcDef")) {
                        parent.setReturnType(returnType);
                        break;
                    } else if (parent.getType().equals("prog")) {
                        System.out.println("|\n|  Invalid returnStat in main");

                        break;
                    }
                    parent = parent.getParent();
                }
            case "classDecl":
                circularClassDependencies(node.getChildren().getValue());
                break;
            case "fCall":
                // TODO: ???
                break;
            case "dataMember":
                ASTNode indexNode = null != node.getChildren(2) ? node.getChildren(2).getChildren() : null;
                int dimSize = 0;
                while (null != indexNode) {
                    ++dimSize;
                    if (!indexNode.getSemanticType().equals("integer")) {
                        System.out.println("|\n|  Invalid array index type in scope" + getScopeMessage(node) + ", find " + indexNode.getSemanticType());
                    }
                    indexNode = indexNode.getSibling();
                }
                if (0 != dimSize) checkArrayDimSize(node.getChildren().getValue(), dimSize, node);
                break;
        }
    }

    private String findIdType(String id, ASTNode node, String tableName) {
        if (tableName.isEmpty()) {
            ASTNode parent = node.getParent();
            while (null != parent) {
                switch (parent.getType()) {
                    case "funcDef":
                        // TODO: in funcDec, also check the varDecl type in funcParmList, scopClass and scopClassInher
                        return "Unknown";
//                        break;
                    case "forStat":
                        return "Unknown";
//                        break;
                    case "prog":
                        if (node.getChildren().getType().equals("fCall")) { // it's free member function
                            for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()) {
                                if (globalRecord.getName().equals(id) && globalRecord.getKind().equals("function")) {
                                    String freeFuncRecordType = globalRecord.getType();
                                    if (freeFuncRecordType.contains(":")) {
                                        return freeFuncRecordType.substring(0, freeFuncRecordType.indexOf(":")-1);
                                    } else {
                                        return freeFuncRecordType;
                                    }
                                }
                            }
                        } else {
                            for (SymbolTableRecord record : parent.getChildren(3).getSymbolTable().getSymbolTableRecords()) {
                                if (record.getName().equals(id)) {
                                    if (record.getLinkName().equals("X")) {
                                        String recordType = record.getType();
                                        String varDeclType;
                                        if (recordType.contains("[")) {
                                            varDeclType = recordType.substring(0, recordType.indexOf("["));
                                        } else {
                                            varDeclType = recordType;
                                        }
                                        return varDeclType;
                                    } else {
                                        return record.getLinkName();
                                    }
//                                    return record.getLinkName().equals("X") ? record.getType() : record.getLinkName();
                                }
                            }
                        }
                }
                parent = parent.getParent();
            }

            if (node.getChildren().getType().equals("fCall")) {
                System.out.println("|\n|  Undefined free function " + id + " in scope" + getScopeMessage(node));
            } else {
                System.out.println("|\n|  Undefined local variable " + id + " in scope" + getScopeMessage(node));
            }
            return "Unknown";
        } else {
            // if id represent dataMember or freeFunc
            if (tableName.contains("[")) { // array variable, check array dimension type
                // TODO: unreached if block, why
                tableName = tableName.substring(0, tableName.indexOf("["));
            }
            if (tableName.contains("::")) { // A::f1_A
                String className = tableName.substring(0, tableName.indexOf("::"));
                String funcName = tableName.substring(className.length()+2);
                for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()) {
                    if (globalRecord.getName().equals(className)) {
                        for (SymbolTableRecord classRecord : globalRecord.getLink().getSymbolTableRecords()) {
                            if (classRecord.getName().equals(funcName)) {
                                for (SymbolTableRecord record : classRecord.getLink().getSymbolTableRecords()) {
                                    if (record.getName().equals(id)) {
                                        return record.getLinkName().equals("X") ? record.getType() : record.getLinkName();
                                    }
                                }
                            }
                        }
                        System.out.println("|\n|  Undefined member function " + funcName + " in scope" + getScopeMessage(node));
                        return "Unknown";
                    }
                }
                System.out.println("|\n|  Undefined member variable " + id + " in scope" + getScopeMessage(node));
            } else {
                for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()) {
                    if (globalRecord.getName().equals(tableName)) {
                        for (SymbolTableRecord record : globalRecord.getLink().getSymbolTableRecords()) {
                            if (record.getName().equals(id)) {
                                return record.getLinkName().equals("X") ? record.getType() : record.getLinkName();
                            }
                        }
                    }
                }
                ASTNode dataMemberNode  = node.getChildren();
                while (null != dataMemberNode) {
                    if (dataMemberNode.getType().equals("fCall") && dataMemberNode.getChildren().getValue().equals(id)) {
                        System.out.println("|\n|  Undefined member function " + id + " in scope" + getScopeMessage(node));
                        return "Unknown";
                    }
                    dataMemberNode = dataMemberNode.getSibling();
                }
                System.out.println("|\n|  Undefined member variable " + id + " in scope" + getScopeMessage(node));
            }

            return "Unknown";
        }
    }

    private void circularClassDependencies(String className) {
        ArrayList<String> dependenciesList = new ArrayList<>();
        for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()) {
            if (globalRecord.getName().equals(className) && globalRecord.getKind().equals("class")) {
                SymbolTable classTable = globalRecord.getLink();
                String classInherList = classTable.getInher();
                if (!classInherList.isEmpty()) {
                    // TODO: deal with multi-inher
                    String inher = classInherList.substring(3);
                    dependenciesList.add(inher);
                }
                // all all class var which is class type, but not the class type itself
                for (SymbolTableRecord dependencyTableRecord : classTable.getSymbolTableRecords()) {
                    String recordType = dependencyTableRecord.getType();
                    if (!recordType.equals(className) && !recordType.equals("float") && ! recordType.equals("integer") && dependencyTableRecord.getKind().equals("variable")) {
                        dependenciesList.add(recordType);
                    }
                }
                break;
            }
        }
        for (String dependency : dependenciesList) {
            for (SymbolTableRecord globalRecord : globalSymbolTable.getSymbolTableRecords()) {
                if (globalRecord.getName().equals(dependency) && globalRecord.getKind().equals("class")) {
                    SymbolTable dependencyTable = globalRecord.getLink();
                    String dependencyInherList = dependencyTable.getInher();
                    if (!dependencyInherList.isEmpty()) {
                        // TODO: deal with multi-inher
                        String inher = dependencyInherList.substring(3);
                        if (inher.equals(className)) {
                            System.out.println("|\n|  Circular class dependencies in class " + className);
                        }
                    }
                    for (SymbolTableRecord dependencyTableRecord : dependencyTable.getSymbolTableRecords()) {
                        if (dependencyTableRecord.getType().equals(className) && dependencyTableRecord.getKind().equals("variable")) {
                            System.out.println("|\n|  Circular class dependencies in class " + className);
                        }
                    }
                }
            }
        }
    }

    private void checkFuncParamList(String scopeClassName, String funcName, ASTNode fCall) {
        SymbolTableRecord scopeClassRecord = null;
        if (!scopeClassName.isEmpty()) {
            scopeClassRecord = globalSymbolTable.findRecord(scopeClassName, "class");
        }
        SymbolTableRecord funcRecord;
        if (null != scopeClassRecord) { // member function call
            funcRecord = scopeClassRecord.getLink().findRecord(funcName, "function");
        } else { // free function call
            funcRecord = globalSymbolTable.findRecord(funcName, "function");
        }
        if (null != funcRecord) {
            String funcType = funcRecord.getType();
            String funcDefParamType = funcType.contains(":") ? funcType.substring(funcType.indexOf(":")+2) : "";
            StringBuilder funcCallParamType = new StringBuilder();
            ASTNode aParam = fCall.getChildren(2).getChildren();

            boolean firstTimeLoop = true;
            while (null != aParam) {
                if (!firstTimeLoop) {
                    funcCallParamType.append(", ");
                }
                firstTimeLoop = false;
                funcCallParamType.append(aParam.getSemanticType());
                aParam = aParam.getSibling();
            }
            // TODO: check function param dim if it's array type
            if (!funcDefParamType.equals(funcCallParamType.toString())) {
                while (funcDefParamType.contains("[")) {
                    int index = funcDefParamType.indexOf("[");
                    funcDefParamType = funcDefParamType.substring(0, index) + funcDefParamType.substring(index+3);
                }
                String scopedFuncName = scopeClassName.isEmpty() ? funcName : scopeClassName + "::" + funcName;
                System.out.println("|\n|  Unmatched function call parameter in scope" + getScopeMessage(fCall) + ", expect " + scopedFuncName + "(" + funcDefParamType + "), find " + scopedFuncName + "(" + funcCallParamType + ")");
            }
        }
    }

    // TODO: right now it only works in main scope
    private void checkArrayDimSize(String varName, int varDimSize, ASTNode node) {
        // TODO: right now only works in main
        ASTNode statBlock = node.getParent();
        while (null != statBlock) {
            if (statBlock.getType().equals("statBlock")) break;
            statBlock = statBlock.getParent();
        }
        if (null == statBlock) return;
        SymbolTableRecord varRecord = statBlock.getSymbolTable().findRecord(varName, "variable");
        if (null == varRecord) return;
        String varDeclDimType = varRecord.getType();
        varDeclDimType = varDeclDimType.substring(varDeclDimType.indexOf("["));
        int varDeclDimSize = varDeclDimType.length() - varDeclDimType.replaceAll("\\[", "").length();
        if (varDimSize != varDeclDimSize) {
            System.out.println("|\n|  Unmatched variable dimension in scope" + getScopeMessage(node) + ", expect " + varDeclDimSize + ", find " + varDimSize);
        }
    }
}






















