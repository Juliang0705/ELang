package com.ELang;

/**
 * Created by Juliang on 1/17/16.
 */

import java.util.*;

/**
 * Parsing expression
 * Order of Precedence
 * Parenthesis > Literal >= Variable >= ArrayElement >= ApplyFunction > Not > Multiply
 * >= Divide > plus >= Minus > Comparisons > And > Or
 */
public class Parser {
    private TokenStream ts;
    public Parser(TokenStream source){
        this.ts = source;
    }

    //functions for parsing statement
    public Statement parseStatement() throws Exception{
        Statement result = parseAssignment();
        if (result == null){
            result = parseArrayAssign();
            if (result == null){
                result = parseIf();
                if (result == null){
                    result = parseWhile();
                    if (result == null){
                        result = parsePrint();
                        if (result == null){
                            result = parseFunctionStatement();
                            if (result == null){
                                result = parseReturn();
                                if (result == null) {
                                    result = parseBlock();
                                    if (result == null) {
                                        result = parseFunctionDeclaration();
                                        if (result == null) {
                                            result = parsePrintln();
                                            if (result == null)
                                                return null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    private Statement parseAssignment() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        String name = this.ts.getIdentifier();
        if (name != null){
            if (this.ts.getWord("=")){
                Expression expr = parseExpression();
                if (this.ts.hasRemovedNewLine()) {
                    return new Assignment(name, expr);
                }
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }
    private Statement parseArrayAssign() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        String name = this.ts.getIdentifier();
        if (name != null){
            if (this.ts.getWord("[")){
                Expression position = parseExpression();
                if (this.ts.getWord("]") && this.ts.getWord("=")){
                    Expression expr = parseExpression();
                    if (this.ts.hasRemovedNewLine()) {
                        return new ArrayAssign(name, position, expr);
                    }
                }
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }

    private Statement parseIf() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        if (this.ts.getWord("if")){
            if (this.ts.getWord("(")) {
                Expression condition = parseExpression();
                if (this.ts.getWord(")")) {
                    Statement trueStat = parseStatement();
                    this.ts.hasRemovedSpaceAndNewLine();
                    if (this.ts.getWord("else if")) {
                        for (int i = 0; i < 3; ++i) this.ts.backSpace();
                        Statement falseStat = parseStatement();
                        return new If(condition, trueStat, falseStat);
                    } else if (this.ts.getWord("else") && (this.ts.hasRemovedSpaceAndNewLine() || this.ts.peak() == '{')) {
                        Statement falseStat = parseStatement();
                        return new If(condition, trueStat, falseStat);
                    }
                    else {
                        return new If(condition, trueStat, new Empty());
                    }
                }
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }
    private Statement parseWhile() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        if (this.ts.getWord("while")){
            if(this.ts.getWord("(")) {
                Expression condition = parseExpression();
                if (this.ts.getWord(")")){
                    Statement stat = parseStatement();
                    return new While(condition,stat);
                }
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }
    private Statement parseBlock() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        if (this.ts.getWord("{")){
            List<Statement> statementList = new ArrayList<>();
            Statement stat = parseStatement();
            while (stat != null){
                statementList.add(stat);
                stat = parseStatement();
            }
            this.ts.hasRemovedSpaceAndNewLine();
            if (this.ts.getWord("}")){
                return new Block(statementList);
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }

    private Statement parsePrint() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        if (this.ts.getWord("print") && this.ts.hasRemovedSpaceAndNewLine()){
            Expression expr = parseExpression();
            if (this.ts.hasRemovedNewLine()){
                return new Print(expr);
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }
    private Statement parsePrintln() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        if (this.ts.getWord("println") && this.ts.hasRemovedSpaceAndNewLine()){
            Expression expr = parseExpression();
            if (this.ts.hasRemovedNewLine()){
                return new Println(expr);
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }

    private Statement parseReturn() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        if (this.ts.getWord("return") && this.ts.hasRemovedSpace()){
            Expression expr = parseExpression();
            if (this.ts.hasRemovedNewLine()){
                return new Return(expr);
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }

    private Statement parseFunctionDeclaration() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        if (this.ts.getWord("def") && this.ts.hasRemovedSpace()){
            String functionName = this.ts.getIdentifier();
            if (this.ts.getWord("(")){
                List<String> arguments = new ArrayList<>();
                String paremeter;
                do{
                    paremeter = this.ts.getIdentifier();
                    if (paremeter != null)
                        arguments.add(paremeter);
                }while(this.ts.getWord(",") && paremeter != null);
                if (this.ts.getWord(")")){
                    this.ts.hasRemovedSpaceAndNewLine();
                    if (this.ts.getWord("{")){
                        List<Statement> statementList = new ArrayList<>();
                        Statement stat = parseStatement();
                        while (stat != null){
                            statementList.add(stat);
                            stat = parseStatement();
                        }
                        this.ts.hasRemovedSpaceAndNewLine();
                        if (this.ts.getWord("}")){
                            return new Function(functionName,arguments,statementList,Memory.getInstance());
                        }
                    }
                }
            }
        }
        this.ts.restoreState(currentIndex);
        return null;

    }
    private Statement parseFunctionStatement() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        this.ts.hasRemovedSpaceAndNewLine();
        String functionName = this.ts.getIdentifier();
        if (this.ts.getWord("(")){
            List<Expression> arguments = new ArrayList<>();
            do{
                try {
                    Expression e = parseExpression();
                    arguments.add(e);
                }catch (Exception e){}
            }while(this.ts.getWord(","));
            if (this.ts.getWord(")") && this.ts.hasRemovedNewLine()){
                return new ApplyFunction(functionName,arguments);
            }
        }
        this.ts.restoreState(currentIndex);
        return null;
    }
    //functions for parsing expression
    public Expression parseExpression() throws Exception{
        return parseOr();
    }
    private Expression parseOr() throws Exception{
        return parseOrSeq(parseAnd());
    }

    private Expression parseOrSeq(Expression left) throws Exception{
        if (this.ts.getWord("||") || this.ts.getWord("or")){
            Expression right = parseAnd();
            return parseOrSeq(new Or(left,right));
        }else
            return left;
    }

    private Expression parseAnd() throws Exception{
        return parseAndSeq(parseComparison());
    }

    private Expression parseAndSeq(Expression left) throws Exception{
        if (this.ts.getWord("&&") || this.ts.getWord("and")){
            Expression right = parseComparison();
            return parseAndSeq(new And(left,right));
        }else
            return left;
    }

    private Expression parseComparison() throws Exception{
        return parseComparisonSeq(parsePlusMinus());
    }

    private Expression parseComparisonSeq(Expression left) throws Exception{
        if (this.ts.getWord("<=")){
            Expression right = parsePlusMinus();
            return parseComparisonSeq(new LessEqual(left,right));
        }else if (this.ts.getWord(">=")){
            Expression right = parsePlusMinus();
            return parseComparisonSeq(new GreaterEqual(left,right));
        }else if (this.ts.getWord("<")){
            Expression right = parsePlusMinus();
            return parseComparisonSeq(new Less(left,right));
        }else if (this.ts.getWord(">")){
            Expression right = parsePlusMinus();
            return parseComparisonSeq(new Greater(left,right));
        }else if (this.ts.getWord("==")){
            Expression right = parsePlusMinus();
            return parseComparisonSeq(new Equal(left,right));
        }else if (this.ts.getWord("!=")){
            Expression right = parsePlusMinus();
            return parseComparisonSeq(new NotEqual(left,right));
        }else
            return left;
    }

    private Expression parsePlusMinus() throws Exception{
        return parsePlusMinusSeq(parseMultiplyDivide());
    }

    private Expression parsePlusMinusSeq(Expression left) throws Exception{
        if (this.ts.getWord("+")){
            Expression right = parseMultiplyDivide();
            return parsePlusMinusSeq(new Plus(left,right));
        }else if (this.ts.getWord("-")){
            Expression right = parseMultiplyDivide();
            return parsePlusMinusSeq(new Minus(left,right));
        }else
            return left;
    }

    private Expression parseMultiplyDivide() throws Exception{
        return parseMultiplyDivideSeq(parseNot());
    }

    private Expression parseMultiplyDivideSeq(Expression left) throws Exception{
        if (this.ts.getWord("*")){
            Expression right = parseNot();
            return parseMultiplyDivideSeq(new Multiply(left,right));
        }else if (this.ts.getWord("/")){
            Expression right = parseNot();
            return parseMultiplyDivideSeq(new Divide(left,right));
        }else
            return left;
    }

    private Expression parseNot() throws Exception{
        if (this.ts.getWord("!") || this.ts.getWord("not")){
            return new Not(parseArrayElement());
        }else
            return parseArrayElement();
    }


    private Expression parseArrayElement() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        String arrayName = this.ts.getIdentifier();
        boolean fail = false;
        if (arrayName != null){
            if (this.ts.getWord("[")){
                Expression position = parseExpression();
                if (this.ts.getWord("]")){
                    return new ArrayElement(arrayName,position);
                }
            }
        }
        //fail
        this.ts.restoreState(currentIndex);
        return parseFunction();
    }
    private Expression parseFunction() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        String functionName = this.ts.getIdentifier();
        if (this.ts.getWord("(")){
            List<Expression> arguments = new ArrayList<>();
            do{
                try {
                    Expression e = parseExpression();
                    arguments.add(e);
                }catch (Exception e){}
            }while(this.ts.getWord(","));
            if (this.ts.getWord(")")){
                return new ApplyFunction(functionName,arguments);
            }
        }
        this.ts.restoreState(currentIndex);
        return parseVariable();
    }

    private Expression parseVariable() throws Exception{
        int currentIndex = this.ts.getCurrentState();
        String name = this.ts.getIdentifier();
        if (name != null){
            return new Variable(name);
        }
        //fail
        this.ts.restoreState(currentIndex);
        return parseLiteral();
    }

    private Expression parseLiteral() throws Exception{
        if (this.ts.getWord("true")){
            return new Literal(Value.value(true));
        }else if (this.ts.getWord("false")){
            return new Literal(Value.value(false));
        }else if (this.ts.getWord("none")){
            return new Literal((Value.value(null)));
        }/*else if (this.ts.getWord("array")){
            if (this.ts.getWord("(")){
                Number size = this.ts.getNumber();
                if (size != null && this.ts.getWord(")")){
                    return new Literal(Value.array(size.intValue()));
                }
            }
        }*/
        else{
            Number n = this.ts.getNumber();
            if (n != null){
                return new Literal(Value.value(n));
            }else{
                String s = this.ts.getString();
                if (s != null){
                    return new Literal(Value.value(s));
                }
            }
        }
        return parseParenthesis();
    }

    private Expression parseParenthesis() throws Exception {
        if (this.ts.getWord("(")){
            Expression inside = parseExpression();
            if (this.ts.getWord(")")){
                return inside;
            }
        }
        throw new Exception("Expression cannot be parsed correctly");
    }


}
