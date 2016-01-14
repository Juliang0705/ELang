package com.ELang;

/**
 * Created by Juliang on 1/13/16.
 */
import java.util.*;

class Function {
    private String name;
    private List<String> variables;
    private List<Statement> stmts;
    private Memory mem;
    public Function(String functionName, List<String> parameters, List<Statement> todo, Memory m) throws Exception{
        this.name = functionName;
        this.variables = parameters;
        this.stmts = todo;
        this.mem = m;
        this.mem.addFunction(this);
    }
    public String getName(){
        return this.name;
    }
    public Value apply(List<Value> values) throws Exception{
        if (values.size() != this.variables.size())
            throw new Exception("Function \"" + this.name +"\" expects " + this.variables.size() + "arguments." +
                                "Found " + values.size() + " arguments.");
        this.mem.pushScope();
        Iterator<String> nameIt = this.variables.iterator();
        Iterator<Value> valueIt= values.iterator();
        while (nameIt.hasNext() && valueIt.hasNext())
            this.mem.addToCurrentScope(nameIt.next(), valueIt.next());
        for (Statement s: this.stmts){
            s.execute(this.mem);
            if (this.mem.hasReturnedValue())
                break;
        }
        this.mem.popScope();
        if (this.mem.hasReturnedValue())
            return this.mem.getReturnedValue();
        else
            throw new Exception("Function \""  + this.name +"\" may not have a return value.");
    }
}
