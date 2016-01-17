package com.ELang;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            /*Memory mem = Memory.getInstance();
            Block prog = new Block(Arrays.asList(
                                    new Function("fac", Arrays.asList("n"),
                            Arrays.asList(
                                    new If(new Greater(new Variable("n"),new Literal(Value.value(1))),
                                            new Return(new Multiply(new Variable("n"),new ApplyFunction("fac",Arrays.asList(new Minus(new Variable("n"),new Literal(Value.value(1))))))),
                                            new Return(new Literal(Value.value(1)))
                                    )
                            ),mem),
                         new Input(),
                    new ToInt(),
                         new Println(new ApplyFunction("fac",Arrays.asList(new Literal(Value.value(5))))),
                         new Assignment("arr",new Literal(Value.array(5))),
                         new ArrayAssign("arr",new Literal(Value.value(0)),new Literal(Value.value(100))),
                    new ArrayAssign("arr",new Literal(Value.value(1)),new Literal(Value.value("3"))),
                    new ArrayAssign("arr",new Literal(Value.value(2)),new Literal(Value.value(true))),
                    new ArrayAssign("arr",new Literal(Value.value(3)),new Literal(Value.value(11.3))),
                    new ArrayAssign("arr",new Literal(Value.value(4)),new Literal(Value.value(104))),
                    new Println(new Variable("arr")),
                  //  new Print(new Literal(Value.value("\nEnter a line:"))),
                  //  new VariableDeclaration("a",new ApplyFunction("input",Arrays.asList())),
                  //  new Print(new Variable("a"))
                    new Println(new ApplyFunction("toInt",Arrays.asList(new Variable("arr"))))
                         ));
            prog.execute(mem);*/
            String s = "\u2705";
            TokenStream ts = new TokenStream("a342HelloWorld sdf");
            System.out.println(ts.getIdentifier());
            System.out.println(ts);
        }catch (Exception err){
            System.out.println(err);
        }
    }
}
