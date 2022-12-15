package org.jonasJava.view;

import org.jonasJava.exception.ExplosaoException;
import org.jonasJava.exception.SairException;
import org.jonasJava.model.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {

    private Tabuleiro tabuleiro;
    private Scanner teclado = new Scanner(System.in);

    public TabuleiroConsole (Tabuleiro tabuleiro){

        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;

            while (continuar){

                cicloDoJogo();

                System.out.println("outra partida? (S/n)");
                String resposta = teclado.nextLine();

                if ("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                }else {
                    tabuleiro.reiniciar();
                }
            }

        }catch (SairException e){
            System.out.println("Tchau fim do jogo!!!");
        }finally {
            teclado.close();
        }
    }

    private void cicloDoJogo() {
        try {

            while (!tabuleiro.objetivoAlcancadoTabuleiro()){
                System.out.println(tabuleiro.toString());

                String digitado = capturarValorDigitado("Digite (x,y): ");
                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - Abrir ou 2 - (des)Marcar: ");

                if ("1".equalsIgnoreCase(digitado)){
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if ("2".equalsIgnoreCase(digitado)) {
                    tabuleiro.alternarMarcado(xy.next(), xy.next());
                }

            }
            System.out.println(tabuleiro);
            System.out.println("Voce Ganhou!!!");
        }catch (ExplosaoException e){
            System.out.println(tabuleiro);
            System.out.println("Voce perdeu lixo!!!");
        }
    }

    private String capturarValorDigitado (String texto){
        System.out.println(texto);
        String digitado = teclado.nextLine();

        if ("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }
        return digitado;
    }


}
