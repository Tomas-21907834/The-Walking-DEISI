package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Vivos extends Creature{


    int totalEquipamentoApanhado = 0;
    Equipamento equipamento;


    public Vivos(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY) {
        super(id, tipoCriatura, nome , coordenadaX, coordenadaY);
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
