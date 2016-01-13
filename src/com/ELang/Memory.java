package com.ELang;

/**
 * Created by Juliang on 1/12/16.
 */
import java.util.*;

public class Memory {
    private static Memory ourInstance = new Memory();
    private LinkedList<HashMap<String,Value>> valueMemory;
    public static Memory getInstance() {
        return ourInstance;
    }
    private Memory() {
        this.valueMemory = new LinkedList<HashMap<String,Value>>();
    }
    public void pushScope(){
        this.valueMemory.addFirst(new HashMap<>());
    }
    public void popScope(){
        this.valueMemory.removeFirst();
    }
    public void addToCurrentScope(String identifier, Value v) throws Exception{
        if (isInCurrentScope(identifier))
            throw new Exception("Identifier: \"" + identifier +"\" is already defined in the scope");
        this.valueMemory.getFirst().put(identifier,v);
    }
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
    public Value lookUp(String identifier) throws Exception{
        for (HashMap<String,Value> m: this.valueMemory){
            Value v = m.get(identifier);
            if (v != null)
                return v;
        }
        throw new Exception("Identifier: \"" + identifier + "\" is not defined.");
    }
    public boolean isInCurrentScope(String identifier){
        return this.valueMemory.getFirst().containsKey(identifier);
    }

}
