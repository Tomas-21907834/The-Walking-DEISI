package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Vivos extends Creature{


    int totalEquipamentoApanhado = 0;
    Equipamento equipamento;
    boolean salvo = false;
    boolean envenenado = false;
    boolean morto = false;
    int numRondasEnvenenado = 0;

    public Vivos(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY, int equipa) {
        super(id, tipoCriatura, nome , coordenadaX, coordenadaY, equipa);
    }

    public Vivos(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY, int equipa, int totalEquipamentoApanhado, Equipamento equipamento, boolean salvo, boolean envenenado, boolean morto, int numRondasEnvenenado) {
        super(id, tipoCriatura, nome, coordenadaX, coordenadaY, equipa);
        this.totalEquipamentoApanhado = totalEquipamentoApanhado;
        this.equipamento = equipamento;
        this.salvo = salvo;
        this.envenenado = envenenado;
        this.morto = morto;
        this.numRondasEnvenenado = numRondasEnvenenado;
    }

    public Vivos(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY, int equipa, int totalEquipamentoApanhado, boolean salvo, boolean envenenado, boolean morto, int numRondasEnvenenado) {
        super(id, tipoCriatura, nome, coordenadaX, coordenadaY, equipa);
        this.totalEquipamentoApanhado = totalEquipamentoApanhado;
        this.salvo = salvo;
        this.envenenado = envenenado;
        this.morto = morto;
        this.numRondasEnvenenado = numRondasEnvenenado;
    }

    public int getTotalEquipamentoApanhado() {
        return totalEquipamentoApanhado;
    }

    public void setNumRondasEnvenenado(int numRondasEnvenenado) {
        this.numRondasEnvenenado = numRondasEnvenenado;
    }

    public int getNumRondasEnvenenado() {
        return numRondasEnvenenado;
    }

    public boolean isEnvenenado() {
        return envenenado;
    }

    public void setMorto(boolean morto) {
        this.morto = morto;
    }

    public boolean isMorto() {
        return morto;
    }

    public boolean isSalvo() {
        return salvo;
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

    public void setEnvenenado(boolean envenenado) {
        this.envenenado = envenenado;
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
            return id + " | " + tipoCriatura() + " | " + equipaString() + " | " + nome + " " + equipamentoApanhados() + " @ (" + coordenadaX + ", " + coordenadaY + ")";
        }
    }


}
