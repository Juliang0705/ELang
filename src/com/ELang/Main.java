package com.ELang;

import java.nio.charset.*;
import java.nio.file.*;
import java.util.Arrays;

public class Main {
    static String readFile(String path, Charset encoding)
            throws Exception
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    public static void main(String[] args) {
        try {
            new Block(Arrays.asList(new Input(),new ToInt(),new Len(),new Array())).execute(Memory.getInstance());
            String program = readFile(args[0], StandardCharsets.UTF_8);
            program = "{" + program + "\n}";
            TokenStream ts = new TokenStream(program);
            Parser p = new Parser(ts);
            Statement exe = p.parseStatement();
            if (exe != null) {
                exe.execute(Memory.getInstance());
            }
            else
                System.out.println("Statements cannot be parsed correctly");
        }catch (Exception err){
            System.out.println(err.getMessage());
        }
    }
}
