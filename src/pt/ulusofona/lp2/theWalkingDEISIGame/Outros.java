package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Outros extends Creature{


    int totalEquipamentoDestruido = 0;

    public Outros(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY) {
        super(id, tipoCriatura , nome, coordenadaX, coordenadaY);
    }

    public void setTotalEquipamentoDestruido(int totalEquipamentoDestruido) {
        this.totalEquipamentoDestruido = totalEquipamentoDestruido;
    }

    public int equipamentosDestruidos(){
        return totalEquipamentoDestruido;
    }


    @Override
    public String toString() {
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentosDestruidos() + " @ ("  + ")"; // por fazer
    }
}
