package com.ELang;

/**
 * Created by Juliang on 1/13/16.
 */
import java.util.*;

interface Callable extends Statement{
    String getName();
    Value apply(List<Value> values) throws Exception;
}

class Function implements Callable{
    protected String name;
    protected List<String> variables;
    protected List<Statement> stmts;
    protected Memory mem;
    public Function(String functionName, List<String> parameters, List<Statement> todo, Memory m) throws Exception{
        this.name = functionName;
        this.variables = parameters;
        this.stmts = todo;
        this.mem = m;
    }
    public String getName(){
        return this.name;
    }
    public Value apply(List<Value> values) throws Exception{
        if (values.size() != this.variables.size())
            throw new Exception("Function \"" + this.name +"\" expects " + this.variables.size() + " argument(s). " +
                                "Found " + values.size() + " argument(s).");
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
            return Value.value(null);
    }

    @Override
    public void execute(Memory mem) throws Exception {
        mem.addFunction(this);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("def " + name + "(");
        char posfix = ' ';
        for (String s: variables) {
            sb.append(posfix);
            sb.append(s);
            posfix = ',';
        }
        sb.append("){\n");
        for (Statement s: stmts)
            sb.append(s);
        sb.append("\n}");
        return sb.toString();
    }
}
