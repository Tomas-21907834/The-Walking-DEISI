package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Vivos extends Creature{


    int totalEquipamentoApanhado = 0;
    Equipamento equipamento;
    boolean salvo = false;
    boolean envenenado = false;
    boolean morto = false;

    public Vivos(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY, int equipa) {
        super(id, tipoCriatura, nome , coordenadaX, coordenadaY, equipa);
    }


    public Equipamento getEquipamento() {
        return equipamento;
    }

    public String getImagePNG() {
        return "human.png";
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento=equipamento;
    }

    public void setTotalEquipamentoApanhado(int totalEquipamentoApanhado) {
        this.totalEquipamentoApanhado = totalEquipamentoApanhado;
    }

    public void setSalvo(boolean salvo) {
        this.salvo = salvo;
    }


    public int equipamentoApanhados(){
        return totalEquipamentoApanhado;
    }

    @Override
    public String toString() {
        if (salvo) {
            return id + " | " + tipoCriatura() + " | " + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @  A salvo";
        } else if (morto){
            return id + " | " + tipoCriatura() + " | " + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @ RIP";
        } else {
            return id + " | " + tipoCriatura() + " | " + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @ (" + coordenadaX + "," + coordenadaY + ")";
        }
    }


}
