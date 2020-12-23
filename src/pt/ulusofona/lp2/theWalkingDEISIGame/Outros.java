package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Outros extends Creature{


    int totalEquipamentoDestruido = 0;

    public Outros(int id, String nome, int tipoCriatura, int coordenadaX, int coordenadaY) {
        super(id, nome, tipoCriatura, coordenadaX, coordenadaY);
    }

    public void setTotalEquipamentoDestruido(int totalEquipamentoDestruido) {
        this.totalEquipamentoDestruido = totalEquipamentoDestruido;
    }

    public int equipamentosDestruidos(){
        return totalEquipamentoDestruido;
    }


    @Override
    public String toString() {
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @ ("  + ")"; // por fazer
    }
}
