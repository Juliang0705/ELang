package com.ELang;

public class Main {

    public static void main(String[] args) {
        try {
            Memory mem = Memory.getInstance();
            mem.pushScope();
            VariableDeclaration dec1 = new VariableDeclaration("var1",
                                            new Plus(new Literal(new Value(20)),new Literal(new Value(20))));
            dec1.execute(mem);
            VariableDeclaration dec2 = new VariableDeclaration("var2",
                                            new Plus(new Variable("var1"),new Literal(new Value(10))));
            dec2.execute(mem);
            Assignment ass1 = new Assignment("var1",new Variable("var2"));
            ass1.execute(mem);
            System.out.println(new Variable("var1").evaluate(mem));
            mem.popScope();
        }catch (Exception err){
            System.out.println(err);
        }
    }
}
