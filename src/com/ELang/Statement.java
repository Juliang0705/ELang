package com.ELang;

/**
 * Created by Juliang on 1/13/16.
 */
interface Statement {
    void execute(Memory mem) throws Exception;
}

class VariableDeclaration implements Statement{
    private String name;
    private Expression expr;
    public VariableDeclaration(String identifier,Expression e){
        this.name = identifier;
        this.expr = e;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        mem.addToCurrentScope(this.name,this.expr.evaluate(mem));
    }
}

class Assignment implements Statement{
    private String name;
    private Expression expr;
    public Assignment(String identifier,Expression e){
        this.name = identifier;
        this.expr = e;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        mem.updateValue(this.name,this.expr.evaluate(mem));
    }
}