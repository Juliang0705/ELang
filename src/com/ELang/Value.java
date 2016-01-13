package com.ELang;

/**
 * Created by Juliang on 1/12/16.
 */
public class Value {
    private Object v;
    public Value(Object o){
        this.v = o;
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
    @Override
    public String toString(){
        return v.toString();
    }
}
