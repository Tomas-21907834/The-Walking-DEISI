package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TWDGameManager {
    
    public boolean startGame(File ficheiroInicial) {
        return false;
    }

    public int[] getWorldSize() {
        int[] coordendas = {1,1};
        return coordendas;
    }

    public int getInitialTeam() {
        return 0;
    }

    public List<Humano> getHumans() {
        ArrayList<Humano> humanos = new ArrayList<>();
        return humanos;
    }

    public List<Zombie> getZombies() {
        ArrayList<Zombie> zombies = new ArrayList<>();
        return zombies;
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        return false;
    }

    public boolean gameIsOver() {
        return false;
    }

    public List<String> getAuthors() {
        ArrayList<String> authors = new ArrayList<>();
        return authors;
    }

    public int getCurrentTeamId() {
        return 0;
    }

    public int getElementId(int x, int y) {
        return 0;
    }

    public List<String> getSurvivors() {
        ArrayList<String> survivors = new ArrayList<>();
        return survivors;
    }

    public boolean isDay() {
        return false;
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        return false;
    }
}

