package br.pucrs.classes;

public class Nodo {

    private int x;
    private int y;
    private Integer funcao;
    private Nodo nodoPai;

    public Nodo(int x, int y, Nodo nodoPai) {
        this.x = x;
        this.y = y;
        this.nodoPai = nodoPai;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFuncao(Integer funcao) {
        this.funcao = funcao;
    }

    public Nodo getNodoPai() {
        return nodoPai;
    }

    public void setNodoPai(Nodo nodoPai) {
        this.nodoPai = nodoPai;
    }

    public Integer getFuncao() {
        return funcao;
    }

    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }
}
