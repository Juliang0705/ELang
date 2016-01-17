package com.ELang;

/**
 * Built-in Library support
 * Created by Juliang on 1/14/16.
 */

import java.io.*;
import java.util.List;

/**
 * Function name: input
 * get input from keyboard as a string.
 */
class Input implements Callable{
    public Input() {
    }
    @Override
    public String getName() {
        return "input";
    }

    @Override
    public Value apply(List<Value> values) throws Exception {
        if (values.size() != 1)
            throw new Exception("Function \"" + this.getName() +"\" expects 1 argument. " +
                    "Found " + values.size() + " argument(s).");
        String prompt = values.get(0).getString();
        System.out.print(prompt);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        return Value.value(str);
    }
    @Override
    public void execute(Memory mem) throws Exception {
        mem.addFunction(this);
    }
}

class ToInt implements Callable{
    public ToInt(){
    }
    @Override
    public String getName() {
        return "toInt";
    }

    @Override
    public Value apply(List<Value> values) throws Exception {
        if (values.size() != 1)
            throw new Exception("Function \"" + this.getName() +"\" expects 1 argument. " +
                    "Found " + values.size() + " argument(s).");
        Value oldValue = values.get(0);
        switch (oldValue.getType()){
            case ARRAY:
                Value[] array = oldValue.getArray();
                for (int i = 0; i < array.length; ++i){
                    array[i] = Value.value(convertToInt(array[i]));
                }
                return Value.value(array);
            default:
                return convertToInt(oldValue);

        }
    }
    private Value convertToInt(Value v) throws Exception{
        switch (v.getType()) {
            case NUMBER:
                return Value.value(v.getNumber().intValue());
            case STRING:
                return Value.value(Integer.parseInt(v.getString()));
            case BOOL:
                return Value.value(v.getBool() ? 1 : 0);
            default:
                throw new Exception("ToInt Failed: unsupported type" + v.getType());
        }
    }
    @Override
    public void execute(Memory mem) throws Exception {
        mem.addFunction(this);
    }
}