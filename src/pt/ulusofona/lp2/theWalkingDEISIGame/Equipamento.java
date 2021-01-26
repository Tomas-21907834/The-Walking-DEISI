package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    int id;
    int idTipo;
    int coordenadaX;
    int coordenadaY;
    int municao;
    int salvou;


    public Equipamento(int id, int idTipo, int coordenadaX, int coordenadaY) {
        this.id = id;
        this.idTipo = idTipo;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public Equipamento(int id, int idTipo, int coordenadaX, int coordenadaY, int municao) {
        this.id = id;
        this.idTipo = idTipo;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.municao = municao;

    }

    public String getImagePNG(){
        return "equipament.png";
    }

    public boolean ofensivo() {
        return idTipo == 1 || idTipo == 2 || idTipo == 6 || idTipo == 10;
    }

    public void setMunicao(int municao) {
        this.municao = municao;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public int getId() {
        return id;
    }

    public int getMunicao() {
        return municao;
    }

    public int getSalvou() {
        return salvou;
    }

    public void setSalvou(int salvou) {
        this.salvou = salvou;
    }
}
