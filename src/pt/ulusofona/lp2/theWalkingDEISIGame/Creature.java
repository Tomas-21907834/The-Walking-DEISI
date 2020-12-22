package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Creature {
     int id;
     String nome;
    int tipoCriatura;
    Equipamento equipamento;
    int equipa;
    int coordenadaX;
    int coordenadaY;
    int totalEquipamentoApanhado = 0;

    public Creature(int id, String nome, int tipoCriatura, int coordenadaX, int coordenadaY) {
        this.id=id;
        this.nome=nome;
        this.tipoCriatura=tipoCriatura;
        this.coordenadaX=coordenadaX;
        this.coordenadaY=coordenadaY;
    }

    public int getId() {
        return id;
    }

    public Creature(String nome) {
        this.nome=nome;
    }

    public String getImagePNG() {
        return null;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public String tipoCriatura() {
        if (tipoCriatura == 0) {
            return "Criança (Zombie)";
        } else if (tipoCriatura == 1) {
            return "Adulto (Zombie)";
        } else if (tipoCriatura == 2) {
            return "Militar (Zombie)";
        } else if (tipoCriatura == 3) {
            return "Idoso (Zombie)";
        } else if (tipoCriatura == 4) {
            return "Zombie Vampiro";
        } else if (tipoCriatura == 5) {
            return "Criança (Vivo)";
        } else if (tipoCriatura == 6) {
            return "Adulto (Vivo)";
        } else if (tipoCriatura == 7) {
            return "Militar (Vivo)";
        } else if (tipoCriatura == 8) {
            return "Idoso (Vivo)";
        } else if (tipoCriatura == 9) {
            return "Cão";
        } else {
            return ""; //??
        }
    }


    public String equipaString() {
        if (equipa == 20) {
            return "Os Outros";
        }
        return "Os Vivos";
    }

    public int equipamentoApanhados(){
        return totalEquipamentoApanhado;
    }


    public String toString() {

        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentoApanhados() +  " @ (" + coordenadaX + ", " + coordenadaY + ")";
    }

}
