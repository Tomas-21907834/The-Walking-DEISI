package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Humano {


    int id;
    String nome;
    int tipoCriatura;
    Equipamento equipamento;
    int equipa;
    int coordenadaX;
    int coordenadaY;
    int totalEquipamentoApanhado = 0;

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
        return "rick.png";
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

    public int getTipoCriatura() {
        return tipoCriatura;
    }

    public int getEquipa() {
        return equipa;
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

    public String toString(){
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @ (" + coordenadaX + ", " + coordenadaY + ")";
    }
}
