package com.ELang;

/**
 * Created by Juliang on 1/12/16.
 */
import java.util.*;
public class Value {
    public static Value array(int n){
        return new Value(n,true);
    }
    public static Value value(Object o){
        return new Value(o);
    }
    private Object v;
    public Value(Object o){
        this.v = o;
    }
    private Value(int n,boolean isArray){
        this.v = new Value[n];
    }
    public Number getNumber() {
        return (Number) v;
    }
    public String getString(){
        return (String) v;
    }
    public Boolean getBool(){
        return (Boolean) v;
    }
    public Value[] getArray() { return (Value[]) v;}
    @Override
    public String toString(){
        if (this.v instanceof Value[]){
            Value[] array = (Value[]) this.v;
            String result = "[";
            for (Value val: array){
                if (val == null)
                    result +=("NONE,");
                else
                    result += (val.toString() + ",");
            }
            if (result.length() == 1)
                result += "]";
            else
                result = result.substring(0,result.length()-1) + "]";
            return result;
        }
        return v.toString();
    }
}
