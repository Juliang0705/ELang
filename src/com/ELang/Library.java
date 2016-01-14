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
        try {
            Memory.getInstance().addFunction(this);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @Override
    public String getName() {
        return "input";
    }

    @Override
    public Value apply(List<Value> values) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        return new Value(str);
    }
}

class ToInt implements Callable{
    public ToInt(){
        try {
            Memory.getInstance().addFunction(this);
        }catch(Exception e){
            System.out.println(e);
        }
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
                    array[i] = new Value(convertToInt(array[i]));
                }
                return new Value(array);
            default:
                return convertToInt(oldValue);

        }
    }
    private Value convertToInt(Value v) throws Exception{
        switch (v.getType()) {
            case NUMBER:
                return new Value(v.getNumber().intValue());
            case STRING:
                return new Value(Integer.parseInt(v.getString()));
            case BOOL:
                return new Value(v.getBool() ? 1 : 0);
            default:
                throw new Exception("Impossible");
        }
    }
}