package com.ELang;

/**
 * Created by Juliang on 1/17/16.
 */

/**
 * Parsing expression
 * Order of Precedence
 * Parenthesis > Literal >= Variable >= ArrayElement >= ApplyFunction > Not > Multiply
 * >= Divide > plus >= Minus > Comparisons > And > Or
 */
class ExpressionParser{

}
public class Parser {
    private TokenStream ts;
    public Parser(TokenStream source){
        this.ts = source;
    }

    //functions for parsing expression
    private Expression parseOr(){
        return parseOrSeq(parseAnd());
    }

    private Expression parseOrSeq(Expression left){
        if (this.ts.getWord("||") || this.ts.getWord("or")){
            Expression right = parseAnd();
            return parseOrSeq(new Or(left,right));
        }else
            return left;
    }

    private Expression parseAnd(){
        return parseAndSeq(parseComparison());
    }

    private Expression parseAndSeq(Expression left){
        if (this.ts.getWord("&&") || this.ts.getWord("and")){
            Expression right = parseComparison();
            return parseAndSeq(new And(left,right));
        }else
            return left;
    }

    private Expression parseComparison(){
        return parseComparisonSeq(parsePlusMinus());
    }

    private Expression parseComparisonSeq(Expression left){
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

    private Expression parsePlusMinus(){
        return parsePlusMinusSeq(parseMultiplyDivide());
    }

    private Expression parsePlusMinusSeq(Expression left){
        if (this.ts.getWord("+")){
            Expression right = parseMultiplyDivide();
            return parsePlusMinusSeq(new Plus(left,right));
        }else if (this.ts.getWord("-")){
            Expression right = parseMultiplyDivide();
            return parsePlusMinusSeq(new Minus(left,right));
        }else
            return left;
    }

    private Expression parseMultiplyDivide(){
        return parseMultiplyDivideSeq(parseNot());
    }

    private Expression parseMultiplyDivideSeq(Expression left){
        if (this.ts.getWord("*")){
            Expression right = parseNot();
            return parseMultiplyDivideSeq(new Multiply(left,right));
        }else if (this.ts.getWord("/")){
            Expression right = parseNot();
            return parseMultiplyDivideSeq(new Divide(left,right));
        }else
            return left;
    }

    private Expression parseNot(){
        if (this.ts.getWord("!") || this.ts.getWord("not")){
            return new Not(parseArrayElement());
        }else
            return parseArrayElement();
    }


    private Expression parseArrayElement(){
        int currentIndex = this.ts.getCurrentState();
        String arrayName = this.ts.getIdentifier();
        boolean fail = false;
        if (arrayName != null){
            if (this.ts.getWord("[")){
                Expression position = parseVariable();
                if (this.ts.getWord("]")){
                    return new ArrayElement(arrayName,position);
                }
            }
        }
        //fail
        this.ts.restoreState(currentIndex);
        return parseVariable();
    }

    private Expression parseVariable(){
        int currentIndex = this.ts.getCurrentState();
        String name = this.ts.getIdentifier();
        if (name != null){
            return new Variable(name);
        }
        //fail
        this.ts.restoreState(currentIndex);
        return parseLiteral();
    }

    private Expression parseLiteral(){
     //   int currentIndex = this.ts.getCurrentState();

    }

    private Expression parseParenthesis(){

    }
    private Expression parseFunction(){

    }


}
