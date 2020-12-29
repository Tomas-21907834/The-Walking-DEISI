package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Outros extends Creature{


    int totalEquipamentoDestruido = 0;

    public Outros(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY, int equipa) {
        super(id, tipoCriatura , nome, coordenadaX, coordenadaY, equipa);
    }

    public void setTotalEquipamentoDestruido(int totalEquipamentoDestruido) {
        this.totalEquipamentoDestruido = totalEquipamentoDestruido;
    }

    public int equipamentosDestruidos(){
        return totalEquipamentoDestruido;
    }

    public String getImagePNG() {
        return "zombie.png";
    }

    @Override
    public String toString() {
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentosDestruidos() + " @ ("  + ")"; // por fazer
    }
}
