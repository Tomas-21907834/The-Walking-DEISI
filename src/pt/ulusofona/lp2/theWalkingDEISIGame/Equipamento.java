package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    int id;
    int idTipo;
    int coordenadaX;
    int coordenadaY;


    public Equipamento(int id, int idTipo, int coordenadaX, int coordenadaY) {
        this.id = id;
        this.idTipo = idTipo;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }


    public String getImagePNG(){
        return "equipament.png";
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }


    public int getCoordenadaY() {
        return coordenadaY;
    }

    public int getId() {
        return id;
    }
}


