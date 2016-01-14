package com.ELang;

/**
 * Created by Juliang on 1/12/16.
 */
import java.util.*;

public class Memory {
    private static Memory ourInstance = new Memory();
    private LinkedList<HashMap<String,Value>> valueMemory;
    private HashMap<String,Function> functionMemory;
    private Value returnedValue;
    public static Memory getInstance() {
        return ourInstance;
    }
    private Memory() {
        this.valueMemory = new LinkedList<HashMap<String,Value>>();
        this.functionMemory = new HashMap<>();
    }

    /**
     * create a new scope in memory
     */
    public void pushScope(){
        this.valueMemory.addFirst(new HashMap<>());
    }

    /**
     * delete a scope in memory
     */
    public void popScope(){
        this.valueMemory.removeFirst();
    }

    /**
     * add a variable to top most scope
     * @param identifier variable name
     * @param v variable value
     * @throws Exception if identifier is already defined in the scope
     */
    public void addToCurrentScope(String identifier, Value v) throws Exception{
        if (isInCurrentScope(identifier))
            throw new Exception("Identifier: \"" + identifier +"\" is already defined in the scope");
        this.valueMemory.getFirst().put(identifier,v);
    }

    /**
     * update the value of variable in current scope
     * @param identifier variable name
     * @param v new variable value
     * @throws Exception if variable is not defined in memory
     */
    public void updateValue(String identifier, Value v) throws Exception{
        boolean hasUpdated = false;
        for (HashMap<String, Value> m : this.valueMemory) {
            if (m.containsKey(identifier)){
                m.replace(identifier,v);
                hasUpdated = true;
                break;
            }
        }
        if (!hasUpdated)
            throw new Exception("Identifier: \"" + identifier +"\" is not defined.");
    }

    /**
     * get value from the memory
     * If multiple variables are defined, only the top most variable will be returned.
     * @param identifier variable name
     * @return variable value
     * @throws Exception
     */
    public Value lookUp(String identifier) throws Exception{
        for (HashMap<String,Value> m: this.valueMemory){
            Value v = m.get(identifier);
            if (v != null)
                return v;
        }
        throw new Exception("Identifier: \"" + identifier + "\" is not defined.");
    }

    /**
     * check if a variable is defined in current scope
     * @param identifier variable name
     * @return
     */
    public boolean isInCurrentScope(String identifier){
        return this.valueMemory.getFirst().containsKey(identifier);
    }

    /**
     * add a function definition to the memory
     * @param f the function to be added
     * @throws Exception if function is already defined
     */
    public void addFunction(Function f) throws Exception{
        if (this.functionMemory.containsKey(f.getName()))
            throw new Exception("Function: \"" + f.getName() + "\" has multiply definitions.");
        this.functionMemory.put(f.getName(),f);
    }

    /**
     * get the function definition from the memory
     * @param functionName name of function
     * @return a function object
     * @throws Exception if function is not defined
     */
    public Function getFunction(String functionName) throws Exception{
        if (!this.functionMemory.containsKey(functionName))
            throw new Exception("Function: \"" + functionName + "\" is not defined.");
        return this.functionMemory.get(functionName);
    }

    /**
     * add the value to be returned to memory
     * @param v the value
     * @throws Exception if there is already a value to be returned
     */
    public void addReturnedValue(Value v) throws Exception{
        if (this.returnedValue != null)
            throw new Exception("Returned value is already made.");
        this.returnedValue = v;
    }

    /**
     *
     * @return true if there is something to be returned
     */
    public boolean hasReturnedValue(){
        return this.returnedValue != null;
    }
    /**
     * remove the value to be returned from memory
     * @return the value
     * @throws Exception if there is not a value to be returned
     */
    public Value getReturnedValue() throws Exception{
        if (this.returnedValue == null)
            throw new Exception("Returned value is empty.");
        Value v = this.returnedValue;
        this.returnedValue = null;
        return v;
    }


}
