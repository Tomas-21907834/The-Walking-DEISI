package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Humano {

    int id;
    String nome;
    int tipoCriatura;
    List<Equipamento> equipamentos = new ArrayList<>();
    int equipa;
    MapaBairro coordenadas;
    String imagePNG;


    public Humano(int id, String nome, int tipoCriatura, MapaBairro coordenadas, String imagePNG) {
        this.id=id;
        this.nome=nome;
        this.tipoCriatura=tipoCriatura;
        this.equipa=0;
        this.coordenadas=coordenadas;
        this.imagePNG=imagePNG;
    }


    public int getId(){
        return id;
    }


    public String getImagePNG(){
        return imagePNG;
    }

    public int nomeEquipa(){
        return equipa;
    }

    public String TipoCriatura() {
       return "Humano";
    }

    public boolean addEquipamentos(Equipamento equipamento){

        return true;

    }

    public String toString(){
        return id + " | " + TipoCriatura() + " | "  + equipa + " | " + nome + " " + e + " @ (" + coordenadas.x + " , " + coordenadas.y + ")";
    }
}
