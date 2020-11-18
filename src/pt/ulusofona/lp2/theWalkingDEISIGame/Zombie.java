package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie {
    static int totalEquipamentoDestruido = 0;
    int id;
    String nome;
    int tipoCriatura;
    List<Equipamento> equipamentos = new ArrayList<>();
    int equipa;
    int coordenadaX;
    int coordenadaY;


    public Zombie(int id, String nome, int tipoCriatura, int coordenadaX, int coordenadaY) {
        this.id = id;
        this.nome = nome;
        this.tipoCriatura= tipoCriatura;
        this.equipa = 1;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public int getId() {
        return id;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public String getImagePNG(){
        return "zombie.png";
    }

    public int nomeEquipa(){
        return equipa;
    }


    public String TipoCriatura() {
        return "Zombie";
    }


    public int addEquipamentos(){
        return totalEquipamentoDestruido++;


    }

    public String toString(){
        return id + " | " + TipoCriatura() + " | "  + equipa + " | " + nome + " " + addEquipamentos() + " @ (" + coordenadaX + " , " + coordenadaY + ")";
    }
}
