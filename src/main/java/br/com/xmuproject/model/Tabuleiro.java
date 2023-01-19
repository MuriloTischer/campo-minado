package br.com.xmuproject.model;

import br.com.xmuproject.exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @AUTHOR MURILO TADEU D. TISCHER ON 1/12/2023
 */
public class Tabuleiro {
    private final List<Campo> campos = new ArrayList<>();
    private int linha;
    private int coluna;
    private int mina;

    public Tabuleiro(int linha, int coluna, int mina) {
        this.linha = linha;
        this.coluna = coluna;
        this.mina = mina;

        gerarCampo();
        associarVizinho();
        sortearMina();
    }

    public void abrir(int linha, int coluna) {
       try{
           campos.parallelStream()
                   .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                   .findFirst()
                   .ifPresent( c -> c.abrir());
       }catch (ExplosaoException ex){
           campos.forEach(c -> c.setAberto(true));
           throw ex;
       }
    }

    public void alterarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent( c -> c.alternarMarcacao());
    }


    private void gerarCampo() {
        for (int l = 0; l < linha; l++) {
            for (int c = 0; c < coluna; c++) {
                campos.add(new Campo(l, c));
            }
        }

    }

    private void associarVizinho() {
        for (Campo campo1 : campos) {
            for (Campo campo2 : campos) {
                campo1.adicionarVizinho(campo2);
            }
        }
    }

    private void sortearMina() {
        long minaArmada = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minaArmada = campos.stream().filter(minado).count();
        } while (minaArmada < mina);
    }

    public boolean objetivoAlcancado() {

        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(c -> c.reiniciar());
        sortearMina();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
            sb.append("  ");
        for (int i = 0; i < coluna; i++) {
            sb.append(" ");
            sb.append(i);
            sb.append(" ");
        }
            sb.append("\n");
        int i = 0;
        for (int l = 0; l < linha; l++) {
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < coluna; c++) {
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
