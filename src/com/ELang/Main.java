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
                         new Print(new ApplyFunction("fac",Arrays.asList(new Literal(new Value(5))))),
                         new VariableDeclaration("arr",new Literal(Value.array(5))),
                         new ArrayAssign(new Variable("arr"),new Literal(Value.value(0)),new Literal(Value.value(100))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(1)),new Literal(Value.value(101))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(2)),new Literal(Value.value(102))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(3)),new Literal(Value.value(103))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(4)),new Literal(Value.value(104))),
                    new Print(new Literal(Value.value("\n"))),
                    new Print(new Variable("arr"))
                         ));
            prog.execute(mem);
        }catch (Exception err){
            System.out.println(err);
        }
    }
}
