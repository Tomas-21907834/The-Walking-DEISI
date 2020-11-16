package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Zombie {
    int id;
    String nome;
    int tipoCriatura;
    Equipamento equipamento;
    int equipa;
    MapaBairro coordenadas;


    public Zombie(int id, String nome, int tipoCriatura, Equipamento equipamento, MapaBairro coordenadas) {
        this.id=id;
        this.nome=nome;
        this.tipoCriatura=tipoCriatura;
        this.equipamento=equipamento;
        this.equipa=1;
        this.coordenadas=coordenadas;
    }


    public int getId() {
        return id;
    }


    public String getImagePNG(){
        return null;
    }

    public int nomeEquipa(){
        return equipa;
    }


    public String TipoCriatura() {
        return "Zombie";
    }


    public int equipamentos(){
        int quantidade = equipamento.idTipo;
        if (tipoCriatura == 1){
           if (quantidade < 0){
               return -1;
           }
            quantidade -=equipamento.idTipo;
        }
        return quantidade;
    }

    public String toString(){
        return id + " | " + TipoCriatura() + " | "  + equipa + " | " + nome + " " + equipamentos() + " @ (" + coordenadas.x + " , " + coordenadas.y + ")";
    }
}
