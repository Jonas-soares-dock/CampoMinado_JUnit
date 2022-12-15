package org.jonasJava.model;

import org.jonasJava.exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        
        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }
    public void abrir (int linha, int coluna){
        try {
            campos.stream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrir());

        }catch (ExplosaoException e){
            campos.forEach(campo -> campo.setAberto(true));
            throw e;
        }

    }

    public void alternarMarcado (int linha, int coluna){
        campos.stream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcado());
    }

    private void associarVizinhos() {
        for (Campo c1: campos){
            for (Campo c2: campos){
                c1.adicionarvizinho(c2);
            }
        }
    }

    private void gerarCampos() {
        for (int l = 0; l< linhas; l++){
            for (int c = 0; c < colunas; c++){
                campos.add(new Campo(l,c));
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int)(Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        }while (minasArmadas < minas);
    }

    public boolean objetivoAlcancadoTabuleiro (){
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar(){
       campos.stream().forEach(c -> c.reiniciar());
       sortearMinas();
    }
    public String toString(){
        StringBuilder sb =new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < colunas; c++){
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");


        int i = 0;
        for (int l = 0; l< linhas; l++){
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < colunas; c++){

                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
