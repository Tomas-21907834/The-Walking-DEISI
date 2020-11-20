package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie {

    int id;
    String nome;
    int tipoCriatura;
    List<Equipamento> equipamentos = new ArrayList<>();
    int equipa;
    int coordenadaX;
    int coordenadaY;
    int totalEquipamentoDestruido = 0;

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

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String tipoCriatura() {
        return "Zombie";
    }


    public void setTotalEquipamentoDestruido(int totalEquipamentoDestruido) {
        this.totalEquipamentoDestruido = totalEquipamentoDestruido;
    }

    public int equipamentosDestruidos(){
        return totalEquipamentoDestruido;
    }

    public String equipaString() {
        if (equipa == 1) {
            return "Os Outros";
        }
        return "Os Vivos";
    }

    public String toString(){
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentosDestruidos() + " @ (" + coordenadaX + ", " + coordenadaY + ")";
    }
}
