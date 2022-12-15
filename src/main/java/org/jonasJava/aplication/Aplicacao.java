package org.jonasJava.aplication;

import org.jonasJava.model.Tabuleiro;
import org.jonasJava.view.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6,6,6);
        new TabuleiroConsole(tabuleiro);

    }
}
