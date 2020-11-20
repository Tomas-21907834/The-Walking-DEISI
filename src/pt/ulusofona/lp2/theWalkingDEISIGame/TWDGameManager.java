package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {

    int x, y, equipaInicial, turno = 0, equipaAtual;
    boolean equipamentoCoordenadaD = false;
    Equipamento equipamentoTemporario;
    ArrayList<Humano> humanos = new ArrayList<>();
    ArrayList<Zombie> zombies = new ArrayList<>();
    ArrayList<Equipamento> equipamentos = new ArrayList<>();

    public TWDGameManager() {

    }

    public boolean startGame(File ficheiroInicial) {
        //Reset all.
        humanos.clear();
        zombies.clear();
        equipamentos.clear();
        turno = 0;
        equipamentoTemporario = null;
        equipamentoCoordenadaD = false;

        try {
            Scanner leitorFicheiro = new Scanner(ficheiroInicial);

            while (leitorFicheiro.hasNextLine()) {

                //Primeira Linha
                String coordenadaLinha = leitorFicheiro.nextLine();
                String[] partesCoordenadas = coordenadaLinha.split(" ");
                x = Integer.parseInt(partesCoordenadas[0]);
                y = Integer.parseInt(partesCoordenadas[1]);

                //Segunda Linha
                String equipaLinha = leitorFicheiro.nextLine();
                equipaInicial = Integer.parseInt(equipaLinha);
                equipaAtual = equipaInicial;

                //Terceira Linha
                String numCriaturasLinha = leitorFicheiro.nextLine();
                int numLinhasC = Integer.parseInt(numCriaturasLinha);

                //Número de linhas dependendo da Terceira Linha
                for (int i = 0; i < numLinhasC; i++) {
                    String criaturasLinha = leitorFicheiro.nextLine();
                    String[] partesCriaturas = criaturasLinha.split(" : ");
                    int id = Integer.parseInt(partesCriaturas[0]);
                    int tipo = Integer.parseInt(partesCriaturas[1]);
                    String nome = partesCriaturas[2];
                    int criaturaCoordenadaX = Integer.parseInt(partesCriaturas[3]);
                    int criaturaCoordenadaY = Integer.parseInt(partesCriaturas[4]);

                    if (tipo == 0) {
                        Zombie zombie = new Zombie(id, nome, tipo, criaturaCoordenadaX, criaturaCoordenadaY);
                        zombies.add(zombie);
                    }
                    if (tipo == 1) {
                        Humano humano = new Humano(id, nome, tipo, criaturaCoordenadaX, criaturaCoordenadaY);
                        humanos.add(humano);
                    }
                }

                //Quinta Linha
                String numEquipamentosLinha = leitorFicheiro.nextLine();
                int numLinhasE = Integer.parseInt(numEquipamentosLinha);

                //Número de linhas dependendo da Quinta Linha
                for (int i = 0; i < numLinhasE; i++) {
                    String equipamentosLinha = leitorFicheiro.nextLine();
                    String[] partesEquipamentos = equipamentosLinha.split(" : ");
                    int id = Integer.parseInt(partesEquipamentos[0]);
                    int tipo = Integer.parseInt(partesEquipamentos[1]);
                    int equipamentoCoordenadaX = Integer.parseInt(partesEquipamentos[2]);
                    int equipamentoCoordenadaY = Integer.parseInt(partesEquipamentos[3]);

                    Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY);
                    equipamentos.add(equipamento);
                }


            }
            leitorFicheiro.close();
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + ficheiroInicial.toString() + " não foi encontrado.");
            return false;
        }
    }

    public int[] getWorldSize() {
        int[] coordendas = {y, x};
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
        final boolean movimentoPossivel = xD - 1 == xO && yD == yO || xD + 1 == xO && yD == yO || yD - 1 == yO && xD == xO || yD + 1 == yO && xD == xO;

        if (xO < 0 || xO > x - 1 || yO < 0 || yO > y - 1 || xD < 0 || xD > x - 1 || yD < 0 || yD > y - 1) {
            return false;
        }

        for (int i = 0; i < humanos.size(); i++) {
            if (humanos.get(i).getCoordenadaX() == xD && humanos.get(i).getCoordenadaY() == yD) {
                return false;
            }
        }

        for (int i = 0; i < zombies.size(); i++) {
            if (zombies.get(i).getCoordenadaX() == xD && zombies.get(i).getCoordenadaY() == yD) {
                return false;
            }
        }


        for (int i = 0; i < equipamentos.size(); i++) {
            if (equipamentos.get(i).getCoordenadaX() == xD && equipamentos.get(i).getCoordenadaY() == yD) {
                equipamentoCoordenadaD = true;
                equipamentoTemporario = equipamentos.get(i);

                for (int j = 0; j < humanos.size(); j++) {
                    if (equipaAtual == 1 && humanos.get(j).getCoordenadaX() == xO && humanos.get(j).getCoordenadaY() == yO && movimentoPossivel) {
                        equipamentoCoordenadaD = false;
                        equipamentoTemporario = null;
                    }
                }

                for (int j = 0; j < zombies.size(); j++) {
                    if (equipaAtual == 0 && zombies.get(j).getCoordenadaX() == xO && zombies.get(j).getCoordenadaY() == yO && movimentoPossivel) {
                        equipamentoCoordenadaD = false;
                        equipamentoTemporario = null;
                    }
                }

            }
        }


        if (equipaAtual == 0) {
            if (movimentoPossivel) {
                for (int i = 0; i < humanos.size(); i++) {
                        if (humanos.get(i).getCoordenadaX() == xO && humanos.get(i).getCoordenadaY() == yO) {
                            if (equipamentoCoordenadaD) {
                                humanos.get(i).setTotalEquipamentoApanhado(+1);
                                humanos.get(i).setEquipamento(equipamentoTemporario);
                                equipamentoTemporario = null;
                                equipamentoCoordenadaD = false;
                            }
                            if (humanos.get(i).getEquipamento() != null) {
                                humanos.get(i).getEquipamento().setCoordenadaX(xD);
                                humanos.get(i).getEquipamento().setCoordenadaY(yD);
                            }
                            humanos.get(i).setCoordenadaX(xD);
                            humanos.get(i).setCoordenadaY(yD);
                            turno++;
                            equipaAtual = 1;
                            return true;
                        }
                    }
                }
            }

        if (equipaAtual == 1) {
            if (movimentoPossivel) {
                for (int i = 0; i < zombies.size(); i++) {
                    if (zombies.get(i).getCoordenadaX() == xO && zombies.get(i).getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            zombies.get(i).setTotalEquipamentoDestruido(+1);
                            equipamentos.remove(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }
                        zombies.get(i).setCoordenadaX(xD);
                        zombies.get(i).setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 0;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean gameIsOver() {
        if (turno == 12) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getAuthors() {
        ArrayList<String> authors = new ArrayList<>();

        authors.add("Tomás Martins");
        authors.add("Manuel Sousa");

        return authors;
    }

    public int getCurrentTeamId() {
        return equipaAtual;
    }

    public int getElementId(int x, int y) {
        for (int i = 0; i < humanos.size(); i++) {
            if (humanos.get(i).getCoordenadaX() == x && humanos.get(i).getCoordenadaY() == y) {
                return humanos.get(i).getId();
            }
        }

        for (int i = 0; i < zombies.size(); i++) {
            if (zombies.get(i).getCoordenadaX() == x && zombies.get(i).getCoordenadaY() == y) {
                return zombies.get(i).getId();
            }
        }

        for (int i = 0; i < equipamentos.size(); i++) {
            if (equipamentos.get(i).getCoordenadaX() == x && equipamentos.get(i).getCoordenadaY() == y) {
                return equipamentos.get(i).getId();
            }
        }

        return 0;
    }


    public List<String> getSurvivors() {
        ArrayList<String> survivors = new ArrayList<>();
        return survivors;
    }

    public boolean isDay() {
        if (turno == 0 || turno == 1 || turno == 4 || turno == 5 || turno == 8 || turno == 9) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        for (int i = 0; i < humanos.size(); i++) {
            for (int j = 0; j < equipamentos.size(); j++) {
                if (humanos.get(i).getId() == creatureId && equipamentos.get(j).getId() == equipmentTypeId) {
                    if (humanos.get(i).getEquipamento().getId() == equipmentTypeId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

