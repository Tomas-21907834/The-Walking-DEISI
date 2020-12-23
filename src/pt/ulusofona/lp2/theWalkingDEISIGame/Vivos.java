package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Vivos extends Creature{


    int totalEquipamentoApanhado = 0;
    Equipamento equipamento;

    public Vivos(int id, String nome, int tipoCriatura, int coordenadaX, int coordenadaY) {
        super(id, nome, tipoCriatura, coordenadaX, coordenadaY);
    }



    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento=equipamento;
    }

    public void setTotalEquipamentoApanhado(int totalEquipamentoApanhado) {
        this.totalEquipamentoApanhado = totalEquipamentoApanhado;
    }

    public int equipamentoApanhados(){
        return totalEquipamentoApanhado;
    }

    @Override
    public String toString() {
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @ ("  + ")";
    }


}
