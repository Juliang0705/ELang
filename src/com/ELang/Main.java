package com.ELang;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Memory mem = Memory.getInstance();
            Function addTwice = new Function("fac", Arrays.asList("n"),
                                    Arrays.asList(
                new If(new Greater(new Variable("n"),new Literal(new Value(1))),
                        new Return(new Multiply(new Variable("n"),new ApplyFunction("fac",Arrays.asList(new Minus(new Variable("n"),new Literal(new Value(1))))))),
                        new Return(new Literal(new Value(1)))
                        )
                                    ),mem);
            Block prog = new Block(Arrays.asList(
                         new Print(new ApplyFunction("fac",Arrays.asList(new Literal(new Value(5)))))
                         ));
            prog.execute(mem);
        }catch (Exception err){
            System.out.println(err);
        }
    }
}
