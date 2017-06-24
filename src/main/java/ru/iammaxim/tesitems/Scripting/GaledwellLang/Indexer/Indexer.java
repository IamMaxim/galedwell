package ru.iammaxim.tesitems.Scripting.GaledwellLang.Indexer;

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

    public void index() {

    }
}
