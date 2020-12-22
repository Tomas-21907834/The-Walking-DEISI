package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Humano extends Creature{



    int totalEquipamentoApanhado = 0;
    boolean isSafeHeaven = true;
    int equipa;


    public Humano(int id, String nome, int tipoCriatura, int coordenadaX, int coordenadaY) {
        super(id, nome, tipoCriatura, coordenadaX, coordenadaY);
        this.equipa=10;
    }

    public int getId(){
        return id;
    }


    public String getImagePNG(){
        return "rick.png";
    }

    public String getNome() {
        return nome;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
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

    public String tipoCriatura() {
       return "Humano";
    }

    public void setTotalEquipamentoApanhado(int totalEquipamentoApanhado) {
        this.totalEquipamentoApanhado = totalEquipamentoApanhado;
    }

    public int equipamentoApanhados(){
        return totalEquipamentoApanhado;
    }

    public String equipaString() {
        if (equipa == 1) {
            return "Os Outros";
        }
        return "Os Vivos";
    }

    public String safeHeaven(){ // ??
        if (isSafeHeaven) {
            return "A salvo";
        }
        return "RIP";
    }

    public String toString(){
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @ (" + safeHeaven() + ")";
    }
}
