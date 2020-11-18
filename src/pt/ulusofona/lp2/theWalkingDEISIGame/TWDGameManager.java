package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {
    //.
    static int x, y, equipaInicial;
    ArrayList<Humano> humanos = new ArrayList<>();
    ArrayList<Zombie> zombies = new ArrayList<>();
    ArrayList<Equipamento> equipamentos = new ArrayList<>();

    public TWDGameManager() {
    }



    public boolean startGame(File ficheiroInicial) {
        String nomeFicheiro = "jogo.txt";
        try {
            File ficheiro = new File(nomeFicheiro);
            Scanner leitorFicheiro = new Scanner(ficheiro);

            while (leitorFicheiro.hasNextLine()) {

                //Primeira Linha
                String coordenadaLinha = leitorFicheiro.nextLine();
                String[] partesCoordenadas = coordenadaLinha.split(" ");
                x = Integer.parseInt(partesCoordenadas[0]);
                y = Integer.parseInt(partesCoordenadas[1]);

                //Segunda Linha
                String equipaLinha = leitorFicheiro.nextLine();
                equipaInicial = Integer.parseInt(equipaLinha);

                //Terceira Linha
                String numCriaturasLinha = leitorFicheiro.nextLine();
                int numLinhasC = Integer.parseInt(numCriaturasLinha);

                //Número de linhas dependendo da Terceira Linha
                for (int i = 0; i <= numLinhasC; i++) {
                    String criaturasLinha = leitorFicheiro.nextLine();
                    String[] partesCriaturas = coordenadaLinha.split(" : ");
                    int id = Integer.parseInt(partesCriaturas[0]);
                    int tipo = Integer.parseInt(partesCriaturas[1]);
                    String nome = partesCriaturas[2];
                    int criaturaCoordenadaX = Integer.parseInt(partesCriaturas[3]);
                    int criaturaCoordenadaY = Integer.parseInt(partesCriaturas[4]);

                    if (tipo == 0) {
                        Zombie zombie = new Zombie(id,nome,tipo,criaturaCoordenadaX,criaturaCoordenadaY,null);
                        zombies.add(zombie);
                    }
                    if (tipo == 1) {
                        Humano humano = new Humano(id,nome,tipo,criaturaCoordenadaX, criaturaCoordenadaY, null);
                        humanos.add(humano);
                    }
                }

                //Quinta Linha
                String numEquipamentosLinha = leitorFicheiro.nextLine();
                int numLinhasE = Integer.parseInt(numCriaturasLinha);

                //Número de linhas dependendo da Quinta Linha
                for (int i = 0; i <= numLinhasE; i++) {
                    String equipamentosLinha = leitorFicheiro.nextLine();
                    String[] partesEquipamentos = coordenadaLinha.split(" : ");
                    int id = Integer.parseInt(partesEquipamentos[0]);
                    int tipo = Integer.parseInt(partesEquipamentos[1]);
                    int equipamentoCoordenadaX = Integer.parseInt(partesEquipamentos[2]);
                    int equipamentoCoordenadaY = Integer.parseInt(partesEquipamentos[3]);

                                                                 //espada fortissima
                    Equipamento equipamento = new Equipamento(0,1);
                    equipamentos.add(equipamento);
                }


            }
            leitorFicheiro.close();
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + nomeFicheiro + " não foi encontrado.");
            return false;
        }
    }

    public int[] getWorldSize() {
        int[] coordendas = {y,x};

        return coordendas;
    }

    public int getInitialTeam() {
        return equipaInicial;
    }

    public List<Humano> getHumans() {
        return humanos;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        if (xO < 0 || xO > x-1 || yO < 0 || yO > y-1 || xD < 0 || xD > x-1 || yD < 0 || yD > y-1) {
            return false;
        }
//
        if (xD-1 == xO && yD == yO || xD+1 == xO && yD == yO || yD-1 == yO && xD == xO || yD+1 == yO && xD == xO) {
            for (int i = 0; i < humanos.size(); i++) {
                if (humanos.get(i).getCoordenadaX() == xO && humanos.get(i).getCoordenadaY() == yO) {
                    humanos.get(i).setCoordenadaX(xD);
                    humanos.get(i).setCoordenadaY(yD);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameIsOver() {
        return false;
    }

    public List<String> getAuthors() {
        ArrayList<String> authors = new ArrayList<>();

        authors.add("Tomás Martins");
        authors.add("Manuel Sousa");

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

