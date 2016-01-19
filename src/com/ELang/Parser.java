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
//to do: ignore space
public class Parser {
    private TokenStream ts;
    public Parser(TokenStream source){
        this.ts = source;
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
        }else{
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
        throw new Exception("Parsing Error");
    }


}
