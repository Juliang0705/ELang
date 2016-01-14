package com.ELang;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Memory mem = Memory.getInstance();
            new Input();
            new ToInt();
            Function addTwice = new Function("fac", Arrays.asList("n"),
                                    Arrays.asList(
                new If(new Greater(new Variable("n"),new Literal(new Value(1))),
                        new Return(new Multiply(new Variable("n"),new ApplyFunction("fac",Arrays.asList(new Minus(new Variable("n"),new Literal(new Value(1))))))),
                        new Return(new Literal(new Value(1)))
                        )
                                    ),mem);
            Block prog = new Block(Arrays.asList(
                         new Println(new ApplyFunction("fac",Arrays.asList(new Literal(new Value(5))))),
                         new VariableDeclaration("arr",new Literal(Value.array(5))),
                         new ArrayAssign(new Variable("arr"),new Literal(Value.value(0)),new Literal(Value.value(100))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(1)),new Literal(Value.value("3"))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(2)),new Literal(Value.value(true))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(3)),new Literal(Value.value(103))),
                    new ArrayAssign(new Variable("arr"),new Literal(Value.value(4)),new Literal(Value.value(104))),
                    new Println(new Variable("arr")),
                  //  new Print(new Literal(Value.value("\nEnter a line:"))),
                  //  new VariableDeclaration("a",new ApplyFunction("input",Arrays.asList())),
                  //  new Print(new Variable("a"))
                    new Println(new ApplyFunction("toInt",Arrays.asList(new Variable("arr"))))
                         ));
            prog.execute(mem);
        }catch (Exception err){
            System.out.println(err);
        }
    }
}
