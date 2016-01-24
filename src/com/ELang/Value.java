package com.ELang;

/**
 * Created by Juliang on 1/12/16.
 */
import java.util.*;
enum ValueType{
    NUMBER,STRING,BOOL,ARRAY,NONE
}
public class Value {
    public static Value array(int n){
        return new Value(n,true);
    }
    public static Value value(Object o){
        return new Value(o);
    }
    private Object v;
    private ValueType type;
    private Value(Object o){
        this.v = o;
        if (this.v instanceof Number)
            this.type = ValueType.NUMBER;
        else if (this.v instanceof String)
            this.type = ValueType.STRING;
        else if (this.v instanceof Boolean)
            this.type = ValueType.BOOL;
        else if (this.v instanceof Value[])
            this.type = ValueType.ARRAY;
        else if (this.v == null)
            this.type = ValueType.NONE;
    }
    private Value(int n,boolean isArray){
        this.v = new Value[n];
        this.type = ValueType.ARRAY;
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
    public ValueType getType(){return this.type;}
    @Override
    public String toString(){
        if (this.type == ValueType.ARRAY){
            Value[] array = (Value[]) this.v;
            String result = "[";
            for (Value val: array){
                if (val == null)
                    result +=("NONE,");
                else
                    result += (val.toString() + ", ");
            }
            if (result.length() == 1)
                result += "]";
            else
                result = result.substring(0,result.length()-2) + "]";
            return result;
        }else if (this.type == ValueType.NONE){
            return "NONE";
        }
        return v.toString();
    }
    @Override
    public boolean equals(Object other){
        if (other instanceof Value){
            Value otherValue = (Value) other;
            if (this.type == otherValue.type){
                switch (this.type){

                    case NUMBER:
                        return ((Number)v).equals((Number)otherValue.v);
                    case STRING:
                        return ((String)v).equals((String)otherValue.v);
                    case BOOL:
                        return ((Boolean)v).equals((Boolean)otherValue.v);
                    case ARRAY:
                       Value[] thisArray = (Value[]) this.v;
                        Value[] otherArray = (Value[]) otherValue.v ;
                        for (int i = 0; i< thisArray.length; ++i){
                            if (!thisArray[i].equals(otherArray[i]))
                                return false;
                        }
                        return true;
                    case NONE:
                        return true;
                }
            }
        }
        return false;
    }
}
