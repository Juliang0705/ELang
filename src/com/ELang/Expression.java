package com.ELang;

/**
 * Created by Juliang on 1/12/16.
 */
import java.util.*;

interface Expression {
    Value evaluate(Memory mem) throws Exception;
}

class Literal implements Expression{
    private Value val;
    public Literal(Value v){
        this.val = v;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception{
        return this.val;
    }
    @Override
    public String toString(){
        return val.toString();
    }
}

class Variable implements Expression{
    private String varName;
    public Variable(String name){
        this.varName = name;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception{
        return mem.lookUp(this.varName);
    }
    @Override
    public String toString(){
        return varName;
    }
}
class ArrayElement implements Expression{
    private Expression pos;
    private Variable arrayName;
    public ArrayElement(String name, Expression position){
        this.arrayName = new Variable(name);
        this.pos = position;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        return this.arrayName.evaluate(mem).getArray()[this.pos.evaluate(mem).getNumber().intValue()];
    }
    @Override
    public String toString(){
        return arrayName + "[" + pos + "]";
    }
}

class Plus implements Expression{
    private Expression left,right;
    public Plus(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        if (l.intValue() == l.doubleValue() && r.intValue() == r.doubleValue()){
            return Value.value(l.intValue() + r.intValue());
        }else
            return Value.value(l.doubleValue() + r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + "+" + right + ")";
    }
}

class Minus implements Expression{
    private Expression left,right;
    public Minus(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        if (l.intValue() == l.doubleValue() && r.intValue() == r.doubleValue()){
            return Value.value(l.intValue() - r.intValue());
        }else
            return Value.value(l.doubleValue() - r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + "-" + right + ")";
    }
}


class Multiply implements Expression{
    private Expression left,right;
    public Multiply(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        if (l.intValue() == l.doubleValue() && r.intValue() == r.doubleValue()){
            return Value.value(l.intValue() * r.intValue());
        }else
            return Value.value(l.doubleValue() * r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + "*" + right + ")";
    }
}


class Divide implements Expression{
    private Expression left,right;
    public Divide(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        if (l.intValue() == l.doubleValue() && r.intValue() == r.doubleValue()){
            return Value.value(l.intValue() / r.intValue());
        }else
            return Value.value(l.doubleValue() / r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + "/" + right + ")";
    }
}

class Equal implements Expression{
    private Expression left,right;
    public Equal(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Value l = this.left.evaluate(mem);
        Value r = this.right.evaluate(mem);
        return Value.value(l.equals(r));
    }
    @Override
    public String toString(){
        return "(" + left + "==" + right + ")";
    }
}

class NotEqual implements Expression{
    private Expression left,right;
    public NotEqual(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Value l = this.left.evaluate(mem);
        Value r = this.right.evaluate(mem);
        return Value.value(!l.equals(r));
    }
    @Override
    public String toString(){
        return "(" + left + "!=" + right + ")";
    }
}

class Greater implements Expression{
    private Expression left,right;
    public Greater(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        return Value.value(l.doubleValue() > r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + ">" + right + ")";
    }
}

class GreaterEqual implements Expression{
    private Expression left,right;
    public GreaterEqual(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        return Value.value(l.doubleValue() >= r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + ">=" + right + ")";
    }
}

class Less implements Expression{
    private Expression left,right;
    public Less(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        return Value.value(l.doubleValue() < r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + "<" + right + ")";
    }
}

class LessEqual implements Expression{
    private Expression left,right;
    public LessEqual(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Number l = this.left.evaluate(mem).getNumber();
        Number r = this.right.evaluate(mem).getNumber();
        return Value.value(l.doubleValue() <= r.doubleValue());
    }
    @Override
    public String toString(){
        return "(" + left + "<=" + right + ")";
    }
}

class And implements Expression{
    private Expression left,right;
    public And(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Boolean l = this.left.evaluate(mem).getBool();
        Boolean r = this.right.evaluate(mem).getBool();
        return Value.value(l.booleanValue() && r.booleanValue());
    }
    @Override
    public String toString(){
        return "(" + left + "&&" + right + ")";
    }
}

class Or implements Expression{
    private Expression left,right;
    public Or(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Boolean l = this.left.evaluate(mem).getBool();
        Boolean r = this.right.evaluate(mem).getBool();
        return Value.value(l.booleanValue() || r.booleanValue());
    }
    @Override
    public String toString(){
        return "(" + left + "||" + right + ")";
    }
}

class Not implements Expression{
    private Expression expr;
    public Not(Expression left){
        this.expr = left;
    }
    @Override
    public Value evaluate(Memory mem) throws Exception {
        Boolean l = this.expr.evaluate(mem).getBool();
        return Value.value(!l.booleanValue());
    }
    @Override
    public String toString(){
        return "!" + expr;
    }
}
class ApplyFunction implements Expression, Statement{
    private String name;
    private List<Expression> args;
    public ApplyFunction(String functionName, List<Expression> arguments){
        this.name = functionName;
        this.args = arguments;
    }


    @Override
    public Value evaluate(Memory mem) throws Exception {
        List<Value> valueList = new ArrayList<>();
        if (this.args != null) {
            for (Expression e : this.args)
                valueList.add(e.evaluate(mem));
        }
        return mem.getFunction(this.name).apply(valueList);
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(name);
        result.append("(");
        String postfix = "";
        for (Expression e: args){
            result.append(postfix);
            result.append(e);
            postfix = ",";
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public void execute(Memory mem) throws Exception {
        List<Value> valueList = new ArrayList<>();
        if (this.args != null) {
            for (Expression e : this.args)
                valueList.add(e.evaluate(mem));
        }
        mem.getFunction(this.name).apply(valueList);
    }
}
