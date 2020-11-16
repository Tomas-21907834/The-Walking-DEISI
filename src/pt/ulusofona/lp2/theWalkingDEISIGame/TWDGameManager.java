package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {

    public boolean startGame(File ficheiroInicial) {
        String nomeFicheiro = "jogo.txt";
        try {
            File ficheiro = new File(nomeFicheiro);
            Scanner leitorFicheiro = new Scanner(ficheiro);

            while (leitorFicheiro.hasNextLine()) {

                String linha = leitorFicheiro.nextLine();

                String parts[] = linha.split(":");
                // converter para os tipos esperados
                int part1 = Integer.parseInt(parts[0]);
                String part2 = parts[1];
                // ...
            }
            leitorFicheiro.close();
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + nomeFicheiro + " não foi encontrado.");
            return false;
        }
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

