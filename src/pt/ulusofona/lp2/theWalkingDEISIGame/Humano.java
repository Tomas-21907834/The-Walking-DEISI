package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Humano {

    int id;
    String nome;
    int tipoCriatura;
    Equipamento equipamento;
    int equipa;
    MapaBairro coordenadas;


    public Humano(int id, String nome, int tipoCriatura, Equipamento equipamento) {
        this.id=id;
        this.nome=nome;
        this.tipoCriatura=tipoCriatura;
        this.equipamento=equipamento;
        this.equipa=0;
    }

    // id do humano
    public int getId(){
        return id;
    }


    public String getImagePNG(){
        return null;
    }

    public String TipoCriatura() {
       return "Humano";
    }

    public int equipamentos(){
        int quantidade = equipamento.idTipo;
        if (tipoCriatura == 0){
            if (quantidade > 0){
                quantidade -=equipamento.idTipo;
            }

        }
        return quantidade;

    }

    public String toString(){
        return id + " | " + TipoCriatura() + " | "  + equipa + " | " + nome + " " + equipamentos() + " @ (" + coordenadas.x + " , " + coordenadas.y + ")";
    }
}
