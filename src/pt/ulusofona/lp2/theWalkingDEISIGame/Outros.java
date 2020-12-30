package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Outros extends Creature{


    int totalEquipamentoDestruido = 0;
    boolean destruido = false;
    boolean transformado = false;

    public Outros(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY, int equipa) {
        super(id, tipoCriatura , nome, coordenadaX, coordenadaY, equipa);
    }

    public void setTotalEquipamentoDestruido(int totalEquipamentoDestruido) {
        this.totalEquipamentoDestruido = totalEquipamentoDestruido;
    }

    public void setDestruido(boolean destruido) {
        this.destruido = destruido;
    }

    public void setTransformado(boolean transformado) {
        this.transformado = transformado;
    }

    public int equipamentosDestruidos(){
        return totalEquipamentoDestruido;
    }

    public String getImagePNG() {
        return "zombie.png";
    }

    @Override
    public String toString() {
        if (destruido) {
            return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentosDestruidos() + " @ RIP";
        }
            return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentosDestruidos() + " @ (" + coordenadaX + coordenadaY + ")";
        }
    }
