package ru.iammaxim.tesitems.Scripting.GaledwellLang.Indexer;

import java.util.ArrayList;

/**
 * Created by maxim on 6/24/17.
 */
public class NodeScope {
    public ArrayList<NodeVariable> variables = new ArrayList<>();
    public ArrayList<NodeScope> scopes = new ArrayList<>();

    public void index(String src) {

    }
}
