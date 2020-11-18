package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie {
    int id;
    String nome;
    int tipoCriatura;
    List<Equipamento> equipamentos = new ArrayList<>();
    int equipa;
    MapaBairro coordenadas;
    String imagePNG;


    public Zombie(int id, String nome, MapaBairro coordenadas, String imagePNG) {
        this.id=id;
        this.nome=nome;
        this.tipoCriatura=0;
        this.equipa=1;
        this.coordenadas=coordenadas;
        this.imagePNG=imagePNG;
    }

    public int getId() {
        return id;
    }


    public String getImagePNG(){
        return imagePNG;
    }

    public int nomeEquipa(){
        return equipa;
    }


    public String TipoCriatura() {
        return "Zombie";
    }


    public int addEquipamentos(Equipamento equipamento){

      if (equipamento.idTipo == 0 || equipamento.idTipo == 1){
          equipamentos.add(equipamento);
          return equipamentos.size();
      }
         return 0;


    }

    public String toString(){
        return id + " | " + TipoCriatura() + " | "  + equipa + " | " + nome + " " + addEquipamentos() + " @ (" + coordenadas.x + " , " + coordenadas.y + ")";
    }
}
