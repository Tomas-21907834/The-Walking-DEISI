package pt.ulusofona.lp2.theWalkingDEISIGame;

abstract public class Creature {
    int id;
    String nome;
    int tipoCriatura;
    int equipa;
    int coordenadaX;
    int coordenadaY;

    public Creature(int id, int tipoCriatura, String nome, int coordenadaX, int coordenadaY, int equipa) {
        this.id = id;
        this.tipoCriatura = tipoCriatura;
        this.nome = nome;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.equipa = equipa;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getImagePNG() {
        return null;
    }

    public int getEquipa() {
        return equipa;
    }

    public int getTipoCriatura() {
        return tipoCriatura;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
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
            return "";
        }
    }


    public String equipaString() {
        if (equipa == 20) {
            return "Os Outros";
        }
        return "Os Vivos";
    }


    abstract public String toString();


}
