package com.ELang;

/**
 * Created by Juliang on 1/12/16.
 */
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
        return new Value(l.doubleValue() + r.doubleValue());
    }
}