package pt.ulusofona.lp2.theWalkingDEISIGame;


public class Zombie extends Creature{

    int totalEquipamentoDestruido = 0;
    int equipa;

    public Zombie(int id, String nome, int tipoCriatura, int coordenadaX, int coordenadaY) {
        super(id, nome, tipoCriatura, coordenadaX, coordenadaY);
        this.equipa=20;
    }

    public int getId() {
        return id;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public String getImagePNG(){
        return "zombie.png";
    }

    public int nomeEquipa(){
        return equipa;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String tipoCriatura() {
        return "Zombie";
    }


    public void setTotalEquipamentoDestruido(int totalEquipamentoDestruido) {
        this.totalEquipamentoDestruido = totalEquipamentoDestruido;
    }

    public String getNome() {
        return nome;
    }

    public int equipamentosDestruidos(){
        return totalEquipamentoDestruido;
    }

    public String equipaString() {
        if (equipa == 1) {
            return "Os Outros";
        }
        return "Os Vivos";
    }

    public String toString(){
        return id + " | " + tipoCriatura() + " | "  + equipaString() + " | " + nome + " " + equipamentosDestruidos() + " @ (" + coordenadaX + ", " + coordenadaY + ")";
    }
}
