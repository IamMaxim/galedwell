package ru.iammaxim.tesitems.Scripting.GaledwellLang.Indexer;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.*;

import java.util.ArrayList;

/**
 * Created by maxim on 6/24/17.
 */
public class Indexer {
    private String src;
    public ArrayList<NodeClass> classes = new ArrayList<>();
    public ArrayList<NodeMethod> methods = new ArrayList<>();
    public ArrayList<NodeVariable> variables = new ArrayList<>();

    public Indexer(String src) {
        this.src = src;
    }

    public void index() throws InvalidTokenException {
        ArrayList<Token> tokens = TokenParser.parse(src);
        Tokener tokener = new Tokener(tokens);
        tokens.forEach(System.out::println);
        while (tokener.left() > 0)
            read(tokener);
    }

    private void read(Tokener tokener) throws InvalidTokenException {
        Token t1 = tokener.eat();
        if (tokener.left() == 0) {
            System.out.println("No tokens left");
            return;
        }
        Token t2 = tokener.eat();
        if (tokener.left() == 0) {
            System.out.println("No tokens left");
            return;
        }
        Token t3 = tokener.eat();
        if (t2 instanceof TokenScope) {
            if (t3.equals(new Token(";"))) {
                System.out.println("function call");
            } else {
                System.out.println("function declaration");
                System.out.println("    name: " + t1.token);
                System.out.println("    args: " + ((TokenScope) t2).tokens);
            }
        }
    }
}
