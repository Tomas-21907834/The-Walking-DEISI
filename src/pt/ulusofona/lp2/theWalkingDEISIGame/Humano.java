package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Humano {

    static int totalEquipamentoApanhado = 0;
    int id;
    String nome;
    int tipoCriatura;
    List<Equipamento> equipamentos = new ArrayList<>();
    int equipa;
    int coordenadaX;
    int coordenadaY;


    public Humano(int id, String nome, int tipoCriatura, int coordenadaX, int coordenadaY) {
        this.id=id;
        this.nome=nome;
        this.tipoCriatura = tipoCriatura;
        this.equipa = 0;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }


    public int getId(){
        return id;
    }


    public String getImagePNG(){
        return "human.png";
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String TipoCriatura() {
       return "Humano";
    }

    public int equipamentoApanhados(){
        return totalEquipamentoApanhado++;
    }

    public String toString(){
        return id + " | " + TipoCriatura() + " | "  + equipa + " | " + nome + " " + " @ (" + coordenadaX + " , " + coordenadaY + ")";
    }
}
