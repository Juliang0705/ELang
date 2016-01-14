package com.ELang;

/**
 * Created by Juliang on 1/13/16.
 */
import java.util.*;

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
class ArrayAssign implements Statement{
    private Variable arrayName;
    private Expression pos;
    private Expression value;
    public ArrayAssign(Variable name, Expression position, Expression newValue){
        this.arrayName = name;
        this.pos = position;
        this.value = newValue;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        this.arrayName.evaluate(mem).getArray()[this.pos.evaluate(mem).getNumber().intValue()] = this.value.evaluate(mem);
    }
}
class Empty implements Statement{

    public Empty(){}
    @Override
    public void execute(Memory mem) throws Exception {
        //.. just empty
    }
}

class If implements Statement{
    private Expression cond;
    private Statement trueStmt, falseStmt;
    public If(Expression condtion, Statement trueStatement, Statement falseStatement){
        this.cond = condtion;
        this.trueStmt = trueStatement;
        this.falseStmt = falseStatement;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        if (this.cond.evaluate(mem).getBool())
            this.trueStmt.execute(mem);
        else
            this.falseStmt.execute(mem);
    }
}

class While implements Statement{
    private Expression cond;
    private Statement stmt;
    public While(Expression condition, Statement statement){
        this.cond = condition;
        this.stmt = statement;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        while (this.cond.evaluate(mem).getBool())
            this.stmt.execute(mem);
    }
}

class Block implements Statement{
    private List<Statement> stmts;
    public Block(List<Statement> statements){
        this.stmts = statements;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        mem.pushScope();
        for (Statement s: this.stmts)
            s.execute(mem);
        mem.popScope();
    }
}

class Print implements Statement{
    private Expression expr;
    public Print(Expression e){
        this.expr = e;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        System.out.print(this.expr.evaluate(mem));
    }
}

class Return implements Statement{
    private Expression expr;
    public Return(Expression e){
        this.expr = e;
    }
    @Override
    public void execute(Memory mem) throws Exception {
        mem.addReturnedValue(this.expr.evaluate(mem));
    }
}