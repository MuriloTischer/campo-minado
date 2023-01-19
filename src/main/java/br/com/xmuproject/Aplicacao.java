package br.com.xmuproject;

import br.com.xmuproject.model.Tabuleiro;

/**
 * @AUTHOR MURILO TADEU D. TISCHER ON ${DATE}
 */
public class Aplicacao {
    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6,6,6);
        System.out.println(tabuleiro);
    }
}