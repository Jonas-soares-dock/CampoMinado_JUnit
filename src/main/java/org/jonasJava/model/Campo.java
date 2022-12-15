package org.jonasJava.model;

import org.jonasJava.exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private boolean aberto;
    private boolean minado;
    private boolean marcado;
    private final int linha;
    private final int coluna;

    private List<Campo> vizinhos = new ArrayList<>();

    public Campo (Integer linha, Integer coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public boolean adicionarvizinho(Campo vizinho){
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        }else {
            return false;
        }

    }

    public void alternarMarcado(){
        if (!aberto){
            marcado = !marcado;
        }
    }
    public boolean abrir (){
        if (!aberto && !marcado){
            aberto = true;

            if (minado){
                throw new ExplosaoException();
            }
            if (vizinhancaSegura()){
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        }else {
            return false;
        }
    }
    public boolean vizinhancaSegura(){

        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public boolean isMarcado (){
        return marcado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public void minar (){
        minado = true;
    }

    public boolean isAberto (){
        return aberto;
    }

    public boolean isFechado (){
        return !isAberto();
    }

    public Integer getLinha() {
        return linha;
    }

    public Integer getColuna() {
        return coluna;
    }

    public boolean objetivoAlcancado (){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public long minasNaVizinhanca(){
        return vizinhos.stream().filter(v -> v.minado).count();
    }
    public void reiniciar (){
        aberto = false;
        minado = false;
        marcado = false;
    }

    public boolean isMinado(){
        return minado;
    }

    public String toString() {
        if (marcado){
            return "x";
        } else if (aberto && minado) {
            return "*";
        } else if (aberto && minasNaVizinhanca() > 0) {
            return Long.toString(minasNaVizinhanca());
        } else if (aberto) {
            return " ";
        }else {
            return "?";
        }
    }
}
