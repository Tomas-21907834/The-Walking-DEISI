package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class SafeHaven  {
    int x;
    int y;
    List<Creature> vivos = new ArrayList<>();

    public SafeHaven(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public List<Creature> getVivos() {
        return vivos;
    }

    public int getIdSafe(int index){
        for (int i = 0; i < vivos.size(); i++) {
            if (index == i) {
                return vivos.get(index).getId();
            }
        }
        return 0;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



}
