package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.counting;

public class TWDGameManager {

    int x, y, equipaInicial, turno = 0, equipaAtual, equipamentoApanhadoCont = 0, equipamentoDestruidoCont = 0, ragnarok = 1;
    int xH, yH;
    boolean equipamentoCoordenadaD = false, transformado = false;
    Equipamento equipamentoTemporario;
    ArrayList<Equipamento> equipamentos = new ArrayList<>();
    List<Creature> creatures = new ArrayList<>();
    List<SafeHaven> safeHavens = new ArrayList<>();
    List<Creature> salvos = new ArrayList<>();
    List<Creature> destruidos = new ArrayList<>();

    public TWDGameManager() {

    }

    public void startGame(File ficheiroInicial) throws InvalidTWDInitialFileException,
            FileNotFoundException {
        //Reset all.
        creatures.clear();
        equipamentos.clear();
        turno = 0;
        equipamentoTemporario = null;
        equipamentoCoordenadaD = false;
        equipamentoApanhadoCont = 0;
        equipamentoDestruidoCont = 0;
        transformado = false;
        ragnarok = 1;

        if (!new InvalidTWDInitialFileException(ficheiroInicial).validCreatureDefinition()) {
            throw new InvalidTWDInitialFileException(ficheiroInicial);
        }

        if (!new InvalidTWDInitialFileException(ficheiroInicial).validNrOfCreatures()) {
            throw new InvalidTWDInitialFileException(ficheiroInicial);
        }

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

                    if (tipo == 0 || tipo == 1 || tipo == 2 || tipo == 3 || tipo == 4) {
                        Creature zombie = new Outros(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, 20);
                        creatures.add(zombie);
                    }
                    if (tipo == 5 || tipo == 6 || tipo == 7 || tipo == 8 || tipo == 9) {
                        Creature humano = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, 10);
                        creatures.add(humano);
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

                    switch (tipo) {

                        case 0: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 1);
                            equipamentos.add(equipamento);
                            break;
                        }
                        case 8: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 1);
                            equipamentos.add(equipamento);
                            break;
                        }

                        case 9: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 1);
                            equipamentos.add(equipamento);
                            break;
                        }
                        case 2: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 3);
                            equipamentos.add(equipamento);
                            break;
                        }

                        case 7: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 3);
                            equipamentos.add(equipamento);
                            break;
                        }
                        default: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY);
                            equipamentos.add(equipamento);
                            break;
                        }
                    }

                }

                //Número de safeHavens
                String numPortasLinha = leitorFicheiro.nextLine();
                int numPortasP = Integer.parseInt(numPortasLinha);

                //Coordenadas de safeHavens
                for (int i = 0; i < numPortasP; i++) {
                    String portasLinha = leitorFicheiro.nextLine();
                    String[] partesPortas = portasLinha.split(" : ");
                    xH = Integer.parseInt(partesPortas[0]);
                    yH = Integer.parseInt(partesPortas[1]);
                    SafeHaven safeHaven = new SafeHaven(xH, yH);
                    safeHavens.add(safeHaven);

                }

            }
            leitorFicheiro.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + ficheiroInicial.toString() + " não foi encontrado.");
            throw exception;
        }
    }


    //Completo
    public int[] getWorldSize() {
        return new int[]{y, x};
    }

    //Completo?
    public boolean gameIsOver() {

        int ocorrencias = 0;

        if (transformado) {
            ragnarok += 12;
            transformado = false;
        }
        if (turno >= ragnarok && turno > 13) {
            return true;
        }

        for (Creature creature : creatures) {
            if (creature.getEquipa() == 10) {
                ocorrencias++;
            }
        }

        if (ocorrencias < 1) {
            return true;
        }
        if (ragnarok < 12) {
            ragnarok++;
        }
        return false;
    }

    //Completo
    public int getInitialTeam() {
        return equipaInicial;
    }

    //Completo
    public List<Creature> getCreatures() {
        List<Creature> allCreatures = new ArrayList<>();

        for (Creature creature : creatures) {
            allCreatures.add(creature);
        }

        for (Creature creature : salvos) {
            allCreatures.add(creature);
        }

        for (Creature creature : destruidos) {
            allCreatures.add(creature);
        }

        return allCreatures;
    }

    public boolean humanoDestino(int xD, int yD) {
        for (Creature creature : creatures) {
            if (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD) {
                if (creature.getEquipa() == 10) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean zombieDestino(int xD, int yD) {
        for (Creature creature : creatures) {
            if (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD) {
                if (creature.getEquipa() == 20) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean humanoIndefeso(int xD, int yD) {
        for (Creature creature : creatures) {
            if (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD) {
                if (((Vivos) creature).getEquipamento() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean mortoPorEstaca(int xD, int yD, Vivos creatureHumano) {
        for (Creature creatureZombie : creatures) {
            if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                zombieDestruido(creatureZombie, creatureHumano);
                return true;
            }
        }
        return false;
    }

    public boolean mortoPorBalazio(Creature creature, int xD, int yD) {
        for (Creature creatureZombie : creatures) {
            if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                if (((Vivos) creature).getEquipamento().getMunicao() > 0) {
                    if (!(creatureZombie.getTipoCriatura() == 4)) {
                        zombieDestruido(creatureZombie, (Vivos) creature);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean mortoACabecada(int xD, int yD , Vivos creatureHumano) {
        for (Creature creatureZombie : creatures) {
            if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                if (creatureZombie.getTipoCriatura() != 4) {
                    zombieDestruido(creatureZombie, creatureHumano);
                    return true;
                }
            }
        }
        return false;
    }

    public void zombieDestruido(Creature creatureZombie, Vivos creatureHumano) {
        creatureHumano.setNumZombiesDestruidos(creatureHumano.getNumZombiesDestruidos() + 1);
        creatureHumano.getEquipamento().setSalvou(creatureHumano.getEquipamento().getSalvou()+1);
        destruidos.add(creatureZombie);
        creatures.remove(creatureZombie);
        ((Outros) creatureZombie).setDestruido(true);
    }

    public void humanoRIP(Creature creatureHumano, Outros creatureZombie) {
        creatureZombie.setNrTransformacoes(creatureZombie.getNrTransformacoes() + 1);
        transformado = true;
        equipamentos.remove(((Vivos) creatureHumano).getEquipamento());
        int id = creatureHumano.getId();
        int tipo = creatureHumano.getTipoCriatura();
        String nome = creatureHumano.getNome();
        int criaturaCoordenadaX = creatureHumano.getCoordenadaX();
        int criaturaCoordenadaY = creatureHumano.getCoordenadaY();
        creatures.remove(creatureHumano);
        switch (tipo) {
            case 5:
                tipo = 0;
                break;

            case 6:
                tipo = 1;
                break;

            case 7:
                tipo = 2;
                break;

            case 8:
                tipo = 3;
                break;
        }
        Outros zombie = new Outros(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, 20);
        creatures.add(zombie);
        zombie.setTransformado(true);
    }

    public void humanoDestruido(Creature creatureHumano) {
        equipamentos.remove(((Vivos) creatureHumano).getEquipamento());
        int id = creatureHumano.getId();
        int tipo = creatureHumano.getTipoCriatura();
        String nome = creatureHumano.getNome();
        int criaturaCoordenadaX = creatureHumano.getCoordenadaX();
        int criaturaCoordenadaY = creatureHumano.getCoordenadaY();
        creatures.remove(creatureHumano);
        Vivos humanoDestruido = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, 10);
        destruidos.add(humanoDestruido);
        humanoDestruido.setMorto(true);
    }

    public void humanoSafe(Creature creatureHumano, int xD, int yD) {
        for (SafeHaven safeHaven : safeHavens) {
            if (safeHaven.getX() == xD && safeHaven.getY() == yD) {
                if (((Vivos) creatureHumano).getEquipamento() != null) {
                    equipamentos.remove(((Vivos) creatureHumano).getEquipamento());
                    salvos.add(creatureHumano);
                    ((Vivos) creatureHumano).setSalvo(true);
                    creatures.remove(creatureHumano);
                    break;
                } else {
                    salvos.add(creatureHumano);
                    ((Vivos) creatureHumano).setSalvo(true);
                    creatures.remove(creatureHumano);
                    break;
                }
            }
        }
    }

    public boolean humanoEnvenenado(int xD, int yD) {
        for (Creature creature : creatures) {
            if (creature.getEquipa() == 10 && creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD) {
                if (((Vivos) creature).isEnvenenado()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void duasRondasEnvenenado() {
        for (Creature creature : new ArrayList<>(creatures)) {
            if (creature.getEquipa() == 10) {
                if (((Vivos) creature).isEnvenenado() == true) {
                    if (((Vivos) creature).getNumRondasEnvenenado() >= 3) {
                        humanoDestruido(creature);
                    } else {
                        ((Vivos) creature).setNumRondasEnvenenado(((Vivos) creature).getNumRondasEnvenenado() + 1);
                    }
                }
            }
        }
    }

    //Funções para ver se existe equipamento,..etc no caminho

    public boolean equipCam1(Equipamento equipamento, int xD, int yD, int xO, int yO) {
        if (xD + 2 == xO && yD == yO) {
            if (equipamento.getCoordenadaX() == xD + 1 && equipamento.getCoordenadaY() == yD) {
                return true;
            }
        }

        if (xD - 2 == xO && yD == yO) {
            if (equipamento.getCoordenadaX() == xD - 1 && equipamento.getCoordenadaY() == yD) {
                return true;
            }
        }

        if (yD + 2 == yO && xD == xO) {
            if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD + 1) {
                return true;
            }
        }

        if (yD - 2 == yO && xD == xO) {
            return equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD - 1;
        }
        return false;
    }

    public boolean creatureCam1(Creature creature, int xD, int yD, int xO, int yO) {
        if (xD + 2 == xO && yD == yO) {
            if (creature.getCoordenadaX() == xD + 1 && creature.getCoordenadaY() == yD) {
                return true;
            }
        }

        if (xD - 2 == xO && yD == yO) {
            if (creature.getCoordenadaX() == xD - 1 && creature.getCoordenadaY() == yD) {
                return true;
            }
        }

        if (yD + 2 == yO && xD == xO) {
            if (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD + 1) {
                return true;
            }
        }

        if (yD - 2 == yO && xD == xO) {
            return creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD - 1;
        }
        return false;
    }

    public boolean safeHavenCam1(SafeHaven safeHaven, int xD, int yD, int xO, int yO) {
        if (xD + 2 == xO && yD == yO) {
            if (safeHaven.getX() == xD + 1 && safeHaven.getY() == yD) {
                return true;
            }
        }

        if (xD - 2 == xO && yD == yO) {
            if (safeHaven.getX() == xD - 1 && safeHaven.getY() == yD) {
                return true;
            }
        }

        if (yD + 2 == yO && xD == xO) {
            if (safeHaven.getX() == xD && safeHaven.getY() == yD + 1) {
                return true;
            }
        }

        if (yD - 2 == yO && xD == xO) {
            if (safeHaven.getX() == xD && safeHaven.getY() == yD - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean equipCam2(Equipamento equipamento, int xD, int yD, int xO, int yO) {
        if (xD + 3 == xO && yD == yO) {
            if (equipamento.getCoordenadaX() == xD + 1 && equipamento.getCoordenadaY() == yD || (equipamento.getCoordenadaX() == xD + 2 && equipamento.getCoordenadaY() == yD)) {
                return true;
            }
        }

        if (xD - 3 == xO && yD == yO) {
            if ((equipamento.getCoordenadaX() == xD - 1 && equipamento.getCoordenadaY() == yD) || (equipamento.getCoordenadaX() == xD - 2 && equipamento.getCoordenadaY() == yD)) {
                return true;
            }
        }

        if (yD + 3 == yO && xD == xO) {
            if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD + 1 || (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD + 2)) {
                return true;
            }
        }

        if (yD - 3 == yO && xD == xO) {
            if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD - 1 || (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD - 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean creatureCam2(Creature creature, int xD, int yD, int xO, int yO) {
        if (xD + 3 == xO && yD == yO) {
            if (creature.getCoordenadaX() == xD + 1 && creature.getCoordenadaY() == yD || (creature.getCoordenadaX() == xD + 2 && creature.getCoordenadaY() == yD)) {
                return true;
            }
        }

        if (xD - 3 == xO && yD == yO) {
            if ((creature.getCoordenadaX() == xD - 1 && creature.getCoordenadaY() == yD) || (creature.getCoordenadaX() == xD - 2 && creature.getCoordenadaY() == yD)) {
                return true;
            }
        }

        if (yD + 3 == yO && xD == xO) {
            if (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD + 1 || (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD + 2)) {
                return true;
            }
        }

        if (yD - 3 == yO && xD == xO) {
            if (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD - 1 || (creature.getCoordenadaX() == xD && creature.getCoordenadaY() == yD - 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean safeHavenCam2(SafeHaven safeHaven, int xD, int yD, int xO, int yO) {
        if (xD + 3 == xO && yD == yO) {
            if (safeHaven.getX() == xD + 1 && safeHaven.getY() == yD || (safeHaven.getX() == xD + 2 && safeHaven.getY() == yD)) {
                return true;
            }
        }

        if (xD - 3 == xO && yD == yO) {
            if ((safeHaven.getX() == xD - 1 && safeHaven.getY() == yD) || (safeHaven.getX() == xD - 2 && safeHaven.getY() == yD)) {
                return true;
            }
        }

        if (yD + 3 == yO && xD == xO) {
            if (safeHaven.getX() == xD && safeHaven.getY() == yD + 1 || (safeHaven.getX() == xD && safeHaven.getY() == yD + 2)) {
                return true;
            }
        }

        if (yD - 3 == yO && xD == xO) {
            if (safeHaven.getX() == xD && safeHaven.getY() == yD - 1 || (safeHaven.getX() == xD && safeHaven.getY() == yD - 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean equipCam1D(Equipamento equipamento, int xD, int yD, int xO, int yO) {

        if (xD + 2 == xO && yD + 2 == yO) {
            if (equipamento.getCoordenadaX() == xD + 1 && equipamento.getCoordenadaY() == yD + 1) {
                return true;
            }
        }

        if (xD - 2 == xO && yD - 2 == yO) {
            if (equipamento.getCoordenadaX() == xD - 1 && equipamento.getCoordenadaY() == yD - 1) {
                return true;
            }
        }

        if (yD + 2 == yO && xD - 2 == xO) {
            if (equipamento.getCoordenadaX() == xD - 1 && equipamento.getCoordenadaY() == yD + 1) {
                return true;
            }
        }

        if (yD - 2 == yO && xD + 2 == xO) {
            if (equipamento.getCoordenadaX() == xD + 1 && equipamento.getCoordenadaY() == yD - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean creatureCam1D(Creature creature, int xD, int yD, int xO, int yO) {

        if (xD + 2 == xO && yD + 2 == yO) {
            if (creature.getCoordenadaX() == xD + 1 && creature.getCoordenadaY() == yD + 1) {
                return true;
            }
        }

        if (xD - 2 == xO && yD - 2 == yO) {
            if (creature.getCoordenadaX() == xD - 1 && creature.getCoordenadaY() == yD - 1) {
                return true;
            }
        }

        if (yD + 2 == yO && xD - 2 == xO) {
            if (creature.getCoordenadaX() == xD - 1 && creature.getCoordenadaY() == yD + 1) {
                return true;
            }
        }

        if (yD - 2 == yO && xD + 2 == xO) {
            if (creature.getCoordenadaX() == xD + 1 && creature.getCoordenadaY() == yD - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean safeHavenCam1D(SafeHaven safeHaven, int xD, int yD, int xO, int yO) {

        if (xD + 2 == xO && yD + 2 == yO) {
            if (safeHaven.getX() == xD + 1 && safeHaven.getY() == yD + 1) {
                return true;
            }
        }

        if (xD - 2 == xO && yD - 2 == yO) {
            if (safeHaven.getX() == xD - 1 && safeHaven.getY() == yD - 1) {
                return true;
            }
        }

        if (yD + 2 == yO && xD - 2 == xO) {
            if (safeHaven.getX() == xD - 1 && safeHaven.getY() == yD + 1) {
                return true;
            }
        }

        if (yD - 2 == yO && xD + 2 == xO) {
            if (safeHaven.getX() == xD + 1 && safeHaven.getY() == yD - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean equipCam2D(Equipamento equipamento, int xD, int yD, int xO, int yO) {

        if (xD + 3 == xO && yD + 3 == yO) {
            if (equipamento.getCoordenadaX() == xD + 1 && equipamento.getCoordenadaY() == yD + 1 || equipamento.getCoordenadaX() == xD + 2 && equipamento.getCoordenadaY() == yD + 2) {
                return true;
            }
        }

        if (xD - 3 == xO && yD - 3 == yO) {
            if (equipamento.getCoordenadaX() == xD - 1 && equipamento.getCoordenadaY() == yD - 1 || equipamento.getCoordenadaX() == xD - 2 && equipamento.getCoordenadaY() == yD - 2) {
                return true;
            }
        }

        if (yD + 3 == yO && xD - 3 == xO) {
            if (equipamento.getCoordenadaX() == xD - 1 && equipamento.getCoordenadaY() == yD + 1 || equipamento.getCoordenadaX() == xD - 2 && equipamento.getCoordenadaY() == yD + 2) {
                return true;
            }
        }

        if (yD - 3 == yO && xD + 3 == xO) {
            if (equipamento.getCoordenadaX() == xD + 1 && equipamento.getCoordenadaY() == yD - 1 || equipamento.getCoordenadaX() == xD + 2 && equipamento.getCoordenadaY() == yD - 2) {
                return true;
            }
        }
        return false;
    }

    public boolean creatureCam2D(Creature creature, int xD, int yD, int xO, int yO) {

        if (xD + 3 == xO && yD + 3 == yO) {
            if (creature.getCoordenadaX() == xD + 1 && creature.getCoordenadaY() == yD + 1 || creature.getCoordenadaX() == xD + 2 && creature.getCoordenadaY() == yD + 2) {
                return true;
            }
        }

        if (xD - 3 == xO && yD - 3 == yO) {
            if (creature.getCoordenadaX() == xD - 1 && creature.getCoordenadaY() == yD - 1 || creature.getCoordenadaX() == xD - 2 && creature.getCoordenadaY() == yD - 2) {
                return true;
            }
        }

        if (yD + 3 == yO && xD - 3 == xO) {
            if (creature.getCoordenadaX() == xD - 1 && creature.getCoordenadaY() == yD + 1 || creature.getCoordenadaX() == xD - 2 && creature.getCoordenadaY() == yD + 2) {
                return true;
            }
        }

        if (yD - 3 == yO && xD + 3 == xO) {
            if (creature.getCoordenadaX() == xD + 1 && creature.getCoordenadaY() == yD - 1 || creature.getCoordenadaX() == xD + 2 && creature.getCoordenadaY() == yD - 2) {
                return true;
            }
        }
        return false;
    }

    public boolean safeHavenCam2D(SafeHaven safeHaven, int xD, int yD, int xO, int yO) {

        if (xD + 3 == xO && yD + 3 == yO) {
            if (safeHaven.getX() == xD + 1 && safeHaven.getY() == yD + 1 || safeHaven.getX() == xD + 2 && safeHaven.getY() == yD + 2) {
                return true;
            }
        }

        if (xD - 3 == xO && yD - 3 == yO) {
            if (safeHaven.getX() == xD - 1 && safeHaven.getY() == yD - 1 || safeHaven.getX() == xD - 2 && safeHaven.getY() == yD - 2) {
                return true;
            }
        }

        if (yD + 3 == yO && xD - 3 == xO) {
            if (safeHaven.getX() == xD - 1 && safeHaven.getY() == yD + 1 || safeHaven.getX() == xD - 2 && safeHaven.getY() == yD + 2) {
                return true;
            }
        }

        if (yD - 3 == yO && xD + 3 == xO) {
            if (safeHaven.getX() == xD + 1 && safeHaven.getY() == yD - 1 || safeHaven.getX() == xD + 2 && safeHaven.getY() == yD - 2) {
                return true;
            }
        }
        return false;
    }

    public boolean algoNoCaminhoCao(boolean movD2, int xD, int yD, int xO, int yO) {
        for (Equipamento equipamento : equipamentos) {
            if (movD2) {
                if (equipCam1D(equipamento, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }

        for (Creature creatureNoCaminho : creatures) {
            if (movD2) {
                if (creatureCam1D(creatureNoCaminho, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }

        for (SafeHaven safeHaven : safeHavens) {
            if (movD2) {
                if (safeHavenCam1D(safeHaven, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean algoNoCaminhoAdulto(boolean mov2, boolean movD2, int xD, int yD, int xO, int yO) {
        for (Equipamento equipamento : equipamentos) {
            if (mov2) {
                if (equipCam1(equipamento, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD2) {
                if (equipCam1D(equipamento, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }

        for (Creature creatureNoCaminho : creatures) {
            if (mov2) {
                if (creatureCam1(creatureNoCaminho, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD2) {
                if (creatureCam1D(creatureNoCaminho, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }

        for (SafeHaven safeHaven : safeHavens) {
            if (mov2) {
                if (safeHavenCam1(safeHaven, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD2) {
                if (safeHavenCam1D(safeHaven, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean algoNoCaminhoMilitar(boolean mov2, boolean movD2, boolean mov3, boolean movD3, int xD, int yD, int xO, int yO) {
        for (Equipamento equipamento : equipamentos) {
            if (mov2) {
                if (equipCam1(equipamento, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD2) {
                if (equipCam1D(equipamento, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (mov3) {
                if (equipCam2(equipamento, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD3) {
                if (equipCam2D(equipamento, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }

        for (Creature creatureNoCaminho : creatures) {
            if (mov2) {
                if (creatureCam1(creatureNoCaminho, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD2) {
                if (creatureCam1D(creatureNoCaminho, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (mov3) {
                if (creatureCam2(creatureNoCaminho, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD3) {
                if (creatureCam2D(creatureNoCaminho, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }

        for (SafeHaven safeHaven : safeHavens) {
            if (mov2) {
                if (safeHavenCam1(safeHaven, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD2) {
                if (safeHavenCam1D(safeHaven, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (mov3) {
                if (safeHavenCam2(safeHaven, xD, yD, xO, yO)) {
                    return true;
                }
            }

            if (movD3) {
                if (safeHavenCam2D(safeHaven, xD, yD, xO, yO)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean crianca(boolean mov1, int xO, int yO, int xD, int yD) {
        boolean matou = false;
        //Humano
        if (equipaAtual == 10) {
            if (mov1) {
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            for (Equipamento equipamento : equipamentos) {
                                if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                    if (equipamento.getIdTipo() == 9 && !(((Vivos) creature).isEnvenenado())) {
                                        return false;
                                    }
                                    if (equipamento.getIdTipo() == 9 && (((Vivos) creature).isEnvenenado())) {
                                        ((Vivos) creature).setEnvenenado(false);
                                        ((Vivos) creature).setNumRondasEnvenenado(0);
                                        equipamento.setMunicao(0);
                                    }
                                    if (equipamento.getIdTipo() == 8 && equipamento.getMunicao() >= 1) {
                                        ((Vivos) creature).setEnvenenado(true);
                                        equipamento.setMunicao(0);
                                    }
                                }
                            }
                            equipamentoApanhadoCont++;
                            ((Vivos) creature).setTotalEquipamentoApanhado(equipamentoApanhadoCont);
                            ((Vivos) creature).setEquipamento(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }
                        if (((Vivos) creature).getEquipamento() != null) {
                            ((Vivos) creature).getEquipamento().setCoordenadaX(xD);
                            ((Vivos) creature).getEquipamento().setCoordenadaY(yD);
                        }

                        //SafeHaven
                        humanoSafe(creature, xD, yD);


                        if (zombieDestino(xD, yD)) {
                            if (((Vivos) creature).getEquipamento() != null) {
                                if (((Vivos) creature).getEquipamento().ofensivo()) {

                                    switch (((Vivos) creature).getEquipamento().getIdTipo()) {

                                        case 1: {
                                            for (Creature creatureZombie : new ArrayList<>(creatures)) {
                                                if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                                                    if (creatureZombie.getTipoCriatura() == 0) {
                                                        zombieDestruido(creatureZombie, (Vivos) creature);
                                                        ((Vivos) creature).getEquipamento().setSalvou(((Vivos) creature).getEquipamento().getSalvou()+1);
                                                        matou = true;
                                                    }
                                                }
                                            }
                                            if (!matou) {
                                                return false;
                                            }
                                            break;
                                        }


                                        case 2: {
                                            if (!(mortoPorBalazio(creature, xD, yD))) {
                                                return false;
                                            }
                                            break;

                                        }
                                        case 6: {
                                            if (!mortoPorEstaca(xD, yD, (Vivos) creature)) {
                                                return false;
                                            }
                                            break;
                                        }

                                        case 10: {
                                            if (!(mortoACabecada(xD, yD, (Vivos) creature))) {
                                                return false;
                                            }
                                            break;
                                        }
                                    }
                                    creature.setCoordenadaX(xD);
                                    creature.setCoordenadaY(yD);
                                    turno++;
                                    equipaAtual = 20;

                                    return true;
                                }
                            }
                            return false;
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 20;
                        return true;
                    }
                }
            }
        }

        //Zombie
        if (equipaAtual == 20) {

            if (mov1) {
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        for (SafeHaven safeHaven : safeHavens) {
                            if (safeHaven.getX() == xD && safeHaven.getY() == yD) {
                                return false;
                            }
                        }
                        if (equipamentoCoordenadaD && !(humanoDestino(xD, yD))) {
                            for (Equipamento equipamento : equipamentos) {
                                if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                    if (equipamento.getIdTipo() == 8) {
                                        return false;
                                    }
                                }
                            }
                            equipamentoDestruidoCont++;
                            ((Outros) creature).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
                            equipamentos.remove(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }

                        if (humanoEnvenenado(xD, yD)) {
                            turno++;
                            equipaAtual = 10;

                            return true;
                        }

                        if (humanoDestino(xD, yD)) {
                            if (!(humanoIndefeso(xD, yD))) {
                                for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        switch (((Vivos) creatureHumano).getEquipamento().getIdTipo()) {
                                            case 0: {
                                                equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 1: {
                                                zombieDestruido(creature, (Vivos) creatureHumano);
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 2: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    zombieDestruido(creature, (Vivos) creatureHumano);
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 3: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 6: {
                                                zombieDestruido(creature, (Vivos) creature);
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 7: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }


                                            case 10: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            default:
                                                humanoRIP(creatureHumano, ((Outros) creature));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                        }
                                    }
                                }
                            } else {
                                for (Creature creatureHumano : creatures) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        humanoRIP(creatureHumano, ((Outros) creature));
                                        turno++;
                                        equipaAtual = 10;

                                        return true;
                                    }
                                }
                            }
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 10;

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean adulto(boolean mov1, boolean mov2, boolean movD1, boolean movD2, int xO, int yO, int xD, int yD) {


        boolean matou = false;
        //Humano
        if (equipaAtual == 10) {
            if (mov1 || mov2 || movD1 || movD2) {
                if (algoNoCaminhoAdulto(mov2, movD2, xD, yD, xO, yO)) {
                    return false;
                }
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            for (Equipamento equipamento : equipamentos) {
                                if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                    if (equipamento.getIdTipo() == 9 && !(((Vivos) creature).isEnvenenado())) {
                                        return false;
                                    }
                                    if (equipamento.getIdTipo() == 9 && (((Vivos) creature).isEnvenenado())) {
                                        ((Vivos) creature).setEnvenenado(false);
                                        ((Vivos) creature).setNumRondasEnvenenado(0);
                                        equipamento.setMunicao(0);
                                    }
                                    if (equipamento.getIdTipo() == 8 && equipamento.getMunicao() >= 1) {
                                        ((Vivos) creature).setEnvenenado(true);
                                        equipamento.setMunicao(0);
                                    }
                                }
                            }
                            equipamentoApanhadoCont++;
                            ((Vivos) creature).setTotalEquipamentoApanhado(equipamentoApanhadoCont);
                            ((Vivos) creature).setEquipamento(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }
                        if (((Vivos) creature).getEquipamento() != null) {
                            ((Vivos) creature).getEquipamento().setCoordenadaX(xD);
                            ((Vivos) creature).getEquipamento().setCoordenadaY(yD);
                        }

                        //SafeHaven
                        humanoSafe(creature, xD, yD);

                        if (zombieDestino(xD, yD)) {
                            if (((Vivos) creature).getEquipamento() != null) {
                                if (((Vivos) creature).getEquipamento().ofensivo()) {

                                    switch (((Vivos) creature).getEquipamento().getIdTipo()) {

                                        case 1: {
                                            for (Creature creatureZombie : new ArrayList<>(creatures)) {
                                                if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                                                    if (creatureZombie.getTipoCriatura() != 4) {
                                                        zombieDestruido(creatureZombie, (Vivos) creature);
                                                        matou = true;
                                                    }
                                                }
                                            }
                                            if (!matou) {
                                                return false;
                                            }
                                            break;
                                        }

                                        case 2: {
                                            if (!(mortoPorBalazio(creature, xD, yD))) {
                                                return false;
                                            }
                                            break;
                                        }

                                        case 6: {
                                            if (!mortoPorEstaca(xD, yD, (Vivos) creature)) {
                                                return false;
                                            }
                                            break;
                                        }

                                        case 10: {
                                            if (!(mortoACabecada(xD, yD, (Vivos) creature))) {
                                                return false;
                                            }
                                            break;
                                        }
                                    }
                                    creature.setCoordenadaX(xD);
                                    creature.setCoordenadaY(yD);
                                    turno++;
                                    equipaAtual = 20;

                                    return true;
                                }
                            }
                            return false;
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 20;

                        return true;
                    }
                }
            }
        }

        //Zombie
        if (equipaAtual == 20) {
            if (mov1 || mov2 || movD1 || movD2) {
                if (algoNoCaminhoAdulto(mov2, movD2, xD, yD, xO, yO)) {
                    return false;
                }
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        for (SafeHaven safeHaven : safeHavens) {
                            if (safeHaven.getX() == xD && safeHaven.getY() == yD) {
                                return false;
                            }
                        }

                        if (equipamentoCoordenadaD && !(humanoDestino(xD, yD))) {
                            for (Equipamento equipamento : equipamentos) {
                                if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                    if (equipamento.getIdTipo() == 8) {
                                        return false;
                                    }
                                }
                            }
                            equipamentoDestruidoCont++;
                            ((Outros) creature).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
                            equipamentos.remove(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }

                        if (humanoEnvenenado(xD, yD)) {
                            turno++;
                            equipaAtual = 10;

                            return true;
                        }

                        if (humanoDestino(xD, yD)) {
                            if (!(humanoIndefeso(xD, yD))) {
                                for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        switch (((Vivos) creatureHumano).getEquipamento().getIdTipo()) {
                                            case 0: {
                                                equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            //completar
                                            case 1: {
                                                if (creatureHumano.getTipoCriatura() != 5) {
                                                    zombieDestruido(creature, (Vivos) creatureHumano);
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }
                                            }

                                            case 2: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    zombieDestruido(creature, (Vivos) creatureHumano);
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 3: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 6: {
                                                zombieDestruido(creature, (Vivos) creatureHumano);
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 7: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }


                                            case 10: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            default:
                                                humanoRIP(creatureHumano, ((Outros) creature));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                        }
                                    }
                                }
                            } else {
                                for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        humanoRIP(creatureHumano, ((Outros) creature));
                                        turno++;
                                        equipaAtual = 10;

                                        return true;
                                    }
                                }
                            }
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 10;

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean militar(boolean mov1, boolean mov2, boolean mov3, boolean movD1, boolean movD2, boolean movD3,
                           int xO, int yO, int xD, int yD) {

        boolean matou = false;
        //Humano
        if (equipaAtual == 10) {
            if (mov1 || mov2 || movD1 || movD2 || mov3 || movD3) {
                if (algoNoCaminhoMilitar(mov2, movD2, mov3, movD3, xD, yD, xO, yO)) {
                    return false;
                }
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            for (Equipamento equipamento : equipamentos) {
                                if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                    if (equipamento.getIdTipo() == 9 && !(((Vivos) creature).isEnvenenado())) {
                                        return false;
                                    }
                                    if (equipamento.getIdTipo() == 9 && (((Vivos) creature).isEnvenenado())) {
                                        ((Vivos) creature).setEnvenenado(false);
                                        ((Vivos) creature).setNumRondasEnvenenado(0);
                                        equipamento.setMunicao(0);
                                    }
                                    if (equipamento.getIdTipo() == 8 && equipamento.getMunicao() >= 1) {
                                        ((Vivos) creature).setEnvenenado(true);
                                        equipamento.setMunicao(0);
                                    }
                                }
                            }
                            equipamentoApanhadoCont++;
                            ((Vivos) creature).setTotalEquipamentoApanhado(equipamentoApanhadoCont);
                            ((Vivos) creature).setEquipamento(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }
                        if (((Vivos) creature).getEquipamento() != null) {
                            ((Vivos) creature).getEquipamento().setCoordenadaX(xD);
                            ((Vivos) creature).getEquipamento().setCoordenadaY(yD);
                        }

                        //SafeHaven
                        humanoSafe(creature, xD, yD);

                        if (zombieDestino(xD, yD)) {
                            if (((Vivos) creature).getEquipamento() != null) {
                                if (((Vivos) creature).getEquipamento().ofensivo()) {

                                    switch (((Vivos) creature).getEquipamento().getIdTipo()) {

                                        case 1: {
                                            for (Creature creatureZombie : new ArrayList<>(creatures)) {
                                                if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                                                    if (creatureZombie.getTipoCriatura() != 4) {
                                                        zombieDestruido(creatureZombie, (Vivos) creature);
                                                        matou = true;
                                                    }
                                                }
                                            }
                                            if (!matou) {
                                                return false;
                                            }
                                            break;
                                        }

                                        case 2: {
                                            if (!(mortoPorBalazio(creature, xD, yD))) {
                                                return false;
                                            }
                                            break;
                                        }

                                        case 6: {
                                            if (!mortoPorEstaca(xD, yD, (Vivos) creature)) {
                                                return false;
                                            }
                                            break;
                                        }

                                        case 10: {
                                            if (!(mortoACabecada(xD, yD, (Vivos) creature))) {
                                                return false;
                                            }
                                            break;
                                        }
                                    }
                                    creature.setCoordenadaX(xD);
                                    creature.setCoordenadaY(yD);
                                    turno++;
                                    equipaAtual = 20;

                                    return true;
                                }
                            }
                            return false;
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 20;

                        return true;
                    }
                }
            }
        }

        //Zombie
        if (equipaAtual == 20) {
            if (mov1 || mov2 || mov3 || movD1 || movD2 || movD3) {
                if (algoNoCaminhoMilitar(mov2, movD2, mov3, movD3, xD, yD, xO, yO)) {
                    return false;
                }
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        for (SafeHaven safeHaven : safeHavens) {
                            if (safeHaven.getX() == xD && safeHaven.getY() == yD) {
                                return false;
                            }
                        }
                        if (equipamentoCoordenadaD && !(humanoDestino(xD, yD))) {
                            for (Equipamento equipamento : equipamentos) {
                                if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                    if (equipamento.getIdTipo() == 8) {
                                        return false;
                                    }
                                }
                            }
                            equipamentoDestruidoCont++;
                            ((Outros) creature).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
                            equipamentos.remove(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }

                        if (humanoEnvenenado(xD, yD)) {
                            turno++;
                            equipaAtual = 10;

                            return true;
                        }

                        if (humanoDestino(xD, yD)) {
                            if (!(humanoIndefeso(xD, yD))) {
                                for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        switch (((Vivos) creatureHumano).getEquipamento().getIdTipo()) {
                                            case 0: {
                                                equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            //completar
                                            case 1: {
                                                if (creatureHumano.getTipoCriatura() != 5) {
                                                    zombieDestruido(creature, (Vivos) creatureHumano);
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }
                                            }

                                            case 2: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    zombieDestruido(creature, (Vivos) creatureHumano);
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 3: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 6: {
                                                zombieDestruido(creature, (Vivos) creatureHumano);
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 7: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }


                                            case 10: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            default:
                                                humanoRIP(creatureHumano, ((Outros) creature));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                        }
                                    }
                                }
                            } else {
                                for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        humanoRIP(creatureHumano, ((Outros) creature));
                                        turno++;
                                        equipaAtual = 10;

                                        return true;
                                    }
                                }
                            }
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 10;

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean idoso(boolean mov1, int xO, int yO, int xD, int yD) {
        boolean matou = false;
        //Humano
        if (equipaAtual == 10) {
            if (mov1) {
                if (isDay()) {
                    for (Creature creature : creatures) {
                        if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                            ((Vivos) creature).setEquipamento(null);
                            if (equipamentoCoordenadaD) {
                                for (Equipamento equipamento : equipamentos) {
                                    if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                        if (equipamento.getIdTipo() == 9 && !(((Vivos) creature).isEnvenenado())) {
                                            return false;
                                        }
                                        if (equipamento.getIdTipo() == 9 && (((Vivos) creature).isEnvenenado())) {
                                            ((Vivos) creature).setEnvenenado(false);
                                            ((Vivos) creature).setNumRondasEnvenenado(0);
                                            equipamento.setMunicao(0);
                                        }
                                        if (equipamento.getIdTipo() == 8 && equipamento.getMunicao() >= 1) {
                                            ((Vivos) creature).setEnvenenado(true);
                                            equipamento.setMunicao(0);
                                        }
                                    }
                                }
                                equipamentoApanhadoCont++;
                                ((Vivos) creature).setTotalEquipamentoApanhado(equipamentoApanhadoCont);
                                ((Vivos) creature).setEquipamento(equipamentoTemporario);
                                equipamentoTemporario = null;
                                equipamentoCoordenadaD = false;
                            }

                            //SafeHaven
                            humanoSafe(creature, xD, yD);

                            if (zombieDestino(xD, yD)) {
                                if (((Vivos) creature).getEquipamento() != null) {
                                    if (((Vivos) creature).getEquipamento().ofensivo()) {

                                        switch (((Vivos) creature).getEquipamento().getIdTipo()) {

                                            case 1: {
                                                for (Creature creatureZombie : new ArrayList<>(creatures)) {
                                                    if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                                                        zombieDestruido(creatureZombie, (Vivos) creature);
                                                        matou = true;
                                                    }
                                                }
                                                if (!matou) {
                                                    return false;
                                                }
                                                break;
                                            }

                                            case 2: {
                                                if (!(mortoPorBalazio(creature, xD, yD))) {
                                                    return false;
                                                }
                                                break;
                                            }

                                            case 6: {
                                                if (!mortoPorEstaca(xD, yD, (Vivos) creature)) {
                                                    return false;
                                                }
                                                break;
                                            }

                                            case 10: {
                                                if (!(mortoACabecada(xD, yD, (Vivos) creature))) {
                                                    return false;
                                                }
                                                break;
                                            }
                                        }
                                        creature.setCoordenadaX(xD);
                                        creature.setCoordenadaY(yD);
                                        turno++;
                                        equipaAtual = 20;

                                        return true;
                                    }
                                }
                                return false;
                            }
                            creature.setCoordenadaX(xD);
                            creature.setCoordenadaY(yD);
                            turno++;
                            equipaAtual = 20;

                            return true;
                        }
                    }
                }
            }
        }

        //Zombie
        if (equipaAtual == 20) {

            if (mov1) {
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        for (SafeHaven safeHaven : safeHavens) {
                            if (safeHaven.getX() == xD && safeHaven.getY() == yD) {
                                return false;
                            }
                        }
                        if (equipamentoCoordenadaD && !(humanoDestino(xD, yD))) {
                            for (Equipamento equipamento : equipamentos) {
                                if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                    if (equipamento.getIdTipo() == 8) {
                                        return false;
                                    }
                                }
                            }
                            equipamentoDestruidoCont++;
                            ((Outros) creature).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
                            equipamentos.remove(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }

                        if (humanoEnvenenado(xD, yD)) {
                            turno++;
                            equipaAtual = 10;

                            return true;
                        }

                        if (humanoDestino(xD, yD)) {
                            if (!(humanoIndefeso(xD, yD))) {
                                for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        switch (((Vivos) creatureHumano).getEquipamento().getIdTipo()) {
                                            case 0: {
                                                equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 1: {
                                                zombieDestruido(creature, (Vivos) creatureHumano);
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 2: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    zombieDestruido(creature, (Vivos) creatureHumano);
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 3: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 4: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 6: {
                                                zombieDestruido(creature, (Vivos) creatureHumano);
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            case 7: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                } else {
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                }
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }


                                            case 10: {
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                            }

                                            default:
                                                humanoRIP(creatureHumano, ((Outros) creature));
                                                turno++;
                                                equipaAtual = 10;

                                                return true;
                                        }
                                    }
                                }
                            } else {
                                for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        if (creatureHumano.getTipoCriatura() == 9) {
                                            return false;
                                        }
                                        humanoRIP(creatureHumano, ((Outros) creature));
                                        turno++;
                                        equipaAtual = 10;

                                        return true;
                                    }
                                }
                            }
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 10;

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean cao(boolean movD1, boolean movD2, int xO, int yO, int xD, int yD) {
        boolean matou = false;
        //Humano
        if (equipaAtual == 10) {
            if (movD1 || movD2) {
                if (algoNoCaminhoCao(movD2, xD, yD, xO, yO)) {
                    return false;
                }
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            equipamentoApanhadoCont++;
                            ((Vivos) creature).setTotalEquipamentoApanhado(equipamentoApanhadoCont);
                            ((Vivos) creature).setEquipamento(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }
                        if (((Vivos) creature).getEquipamento() != null) {
                            ((Vivos) creature).getEquipamento().setCoordenadaX(xD);
                            ((Vivos) creature).getEquipamento().setCoordenadaY(yD);
                        }

                        //SafeHaven
                        humanoSafe(creature, xD, yD);

                        if (zombieDestino(xD, yD)) {
                            return false;
                        }

                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 20;

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean vampiro(boolean mov1, boolean mov2, boolean movD1, boolean movD2, int xD, int yD, int xO, int yO) {

        if (equipaAtual == 20) {
            if ((mov1 || mov2 || movD1 || movD2)) {
                if (!isDay()) {
                    if (algoNoCaminhoAdulto(mov2, movD2, xD, yD, xO, yO)) {
                        return false;
                    }
                    for (Creature creature : creatures) {
                        if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                            for (SafeHaven safeHaven : safeHavens) {
                                if (safeHaven.getX() == xD && safeHaven.getY() == yD) {
                                    return false;
                                }
                            }
                            if (equipamentoCoordenadaD && !(humanoDestino(xD, yD))) {
                                for (Equipamento equipamento : equipamentos) {
                                    if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                                        if (equipamento.getIdTipo() == 8) {
                                            return false;
                                        }
                                        if (equipamento.getIdTipo() == 5) {
                                            return false;
                                        }
                                    }
                                }
                                equipamentoDestruidoCont++;
                                ((Outros) creature).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
                                equipamentos.remove(equipamentoTemporario);
                                equipamentoTemporario = null;
                                equipamentoCoordenadaD = false;
                            }

                            if (humanoEnvenenado(xD, yD)) {
                                turno++;
                                equipaAtual = 10;

                                return true;
                            }

                            if (humanoDestino(xD, yD)) {
                                if (!(humanoIndefeso(xD, yD))) {
                                    for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                        if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                            if (creatureHumano.getTipoCriatura() == 9) {
                                                return false;
                                            }
                                            switch (((Vivos) creatureHumano).getEquipamento().getIdTipo()) {
                                                case 0: {
                                                    equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }

                                                case 3: {
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }

                                                case 5: {
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }

                                                case 6: {
                                                    zombieDestruido(creature, (Vivos) creatureHumano);
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }

                                                case 7: {
                                                    if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                        ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao()) - 1);
                                                    } else {
                                                        humanoRIP(creatureHumano, ((Outros) creature));
                                                    }
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }


                                                case 10: {
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                                }

                                                default:
                                                    humanoRIP(creatureHumano, ((Outros) creature));
                                                    turno++;
                                                    equipaAtual = 10;

                                                    return true;
                                            }
                                        }
                                    }
                                } else {
                                    for (Creature creatureHumano : new ArrayList<>(creatures)) {
                                        if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                            if (creatureHumano.getTipoCriatura() == 9) {
                                                return false;
                                            }
                                            humanoRIP(creatureHumano, ((Outros) creature));
                                            turno++;
                                            equipaAtual = 10;

                                            return true;
                                        }
                                    }
                                }
                            }
                            creature.setCoordenadaX(xD);
                            creature.setCoordenadaY(yD);
                            turno++;
                            equipaAtual = 10;

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean move(int xO, int yO, int xD, int yD) {

        equipamentoTemporario = null;
        equipamentoCoordenadaD = false;

        //Vertical e Horizontal              Esquerda                       Direita                    Cima                           Baixo
        final boolean movMax1 = xD - 1 == xO && yD == yO || xD + 1 == xO && yD == yO || yD - 1 == yO && xD == xO || yD + 1 == yO && xD == xO;
        final boolean movMax2 = xD - 2 == xO && yD == yO || xD + 2 == xO && yD == yO || yD - 2 == yO && xD == xO || yD + 2 == yO && xD == xO;
        final boolean movMax3 = xD - 3 == xO && yD == yO || xD + 3 == xO && yD == yO || yD - 3 == yO && xD == xO || yD + 3 == yO && xD == xO;
        //Diagonal                                  NO                              NE                        SO                        SE
        final boolean movDMax1 = xD - 1 == xO && yD - 1 == yO || xD + 1 == xO && yD - 1 == yO || yD + 1 == yO && xD - 1 == xO || yD + 1 == yO && xD + 1 == xO;
        final boolean movDMax2 = xD - 2 == xO && yD - 2 == yO || xD + 2 == xO && yD - 2 == yO || yD + 2 == yO && xD - 2 == xO || yD + 2 == yO && xD + 2 == xO;
        final boolean movDMax3 = xD - 3 == xO && yD - 3 == yO || xD + 3 == xO && yD - 3 == yO || yD + 3 == yO && xD - 3 == xO || yD + 3 == yO && xD + 3 == xO;

        if (equipaAtual == 20) {
            for (Creature creature : creatures) {
                if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                    if (creature.getEquipa() == 10) {
                        return false;
                    }
                }
            }
        }

        if (equipaAtual == 10) {
            for (Creature creature : creatures) {
                if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                    if (creature.getEquipa() == 20) {
                        return false;
                    }
                }
            }
        }

        //Humano não pode ir para cima de outro humano
        if (equipaAtual == 10) {
            if (humanoDestino(xD, yD)) {
                return false;
            }
        }

        //zombie não pode ir para cima de outro zombie
        if (equipaAtual == 20) {
            if (zombieDestino(xD, yD)) {
                return false;
            }
        }

        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                equipamentoCoordenadaD = true;
                equipamentoTemporario = equipamento;

            }
        }

        for (Creature creature : new ArrayList<>(creatures)) {
            if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                switch (creature.getTipoCriatura()) {

                    //Criança (Zombie)
                    case 0: {
                        if (crianca(movMax1, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Adulto (Zombie)
                    case 1: {
                        if (adulto(movMax1, movMax2, movDMax1, movDMax2, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Militar (Zombie)
                    case 2: {
                        if (militar(movMax1, movMax2, movMax3, movDMax1, movDMax2, movDMax3, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Idoso (Zombie)
                    case 3: {
                        if (idoso(movMax1, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Vampiro
                    case 4: {
                        if (vampiro(movMax1, movMax2, movDMax1, movDMax2, xD, yD, xO, yO)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Criança
                    case 5: {
                        if (crianca(movMax1, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Adulto
                    case 6: {
                        if (adulto(movMax1, movMax2, movDMax1, movDMax2, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Militar
                    case 7: {
                        if (militar(movMax1, movMax2, movMax3, movDMax1, movDMax2, movDMax3, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Idoso
                    case 8: {
                        if (idoso(movMax1, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }

                    //Cao
                    case 9: {
                        if (cao(movDMax1, movDMax2, xO, yO, xD, yD)) {
                            duasRondasEnvenenado();
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }


    //Completo
    public List<String> getAuthors() {
        ArrayList<String> authors = new ArrayList<>();

        authors.add("Tomás Martins");
        authors.add("Manuel Sousa");

        return authors;
    }

    //Completo
    public int getCurrentTeamId() {
        return equipaAtual;
    }

    //Completo
    public int getElementId(int x, int y) {
        for (Creature creature : creatures) {
            if (creature.getCoordenadaX() == x && creature.getCoordenadaY() == y) {
                return creature.getId();
            }
        }

        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getCoordenadaX() == x && equipamento.getCoordenadaY() == y) {
                return equipamento.getId();
            }
        }

        for (SafeHaven safeHaven : safeHavens) {
            if (safeHaven.getX() == x && safeHaven.getY() == y) {
                return 0;
            }
        }

        return 0;
    }

    //Quase Completo
    public List<String> getGameResults() {
        ArrayList<String> resultados = new ArrayList<>();

        resultados.add("Nr. de turnos terminados:\n");

        resultados.add(turno + "\n\n");

        resultados.add("Ainda pelo bairo:\n\n");

        resultados.add("OS VIVOS\n");

        for (Creature creature : creatures) {
            if (creature.getEquipa() == 10) {
                resultados.add(creature.getId() + " " + creature.getNome() + "\n");
            }
        }

        resultados.add("\nOS OUTROS\n");

        for (Creature creature : creatures) {
            if (creature.getEquipa() == 20) {
                resultados.add(creature.getId() + " " + creature.getNome() + "\n");
            }
        }

        resultados.add("\nNum safe haven:\n");

        resultados.add("OS VIVOS\n");


        for (Creature salvo : salvos) {
            resultados.add(salvo.getId() + " " + salvo.getNome());
        }

        resultados.add("\nEnvenenados / Destruidos\n");
        resultados.add("OS VIVOS\n");

        for (Creature creature : destruidos) {
            if (((Vivos) creature).isMorto()) {
                resultados.add(creature.getId() + " " + creature.getNome());
            }
        }

        resultados.add("\nOS OUTROS\n");


        for (Creature creature : destruidos) {
            if (((Outros) creature).isDestruido()) {
                resultados.add(creature.getId() + " " + creature.getNome() + "\n");
            }
        }

        return resultados;


    }

    //Completo
    public boolean isDay() {
        if (turno % 4 == 0) {
            return true;
        }

        if ((turno % 4) == 1) {
            return true;
        }
        return false;
    }

    //Completo
    public List<Integer> getIdsInSafeHaven() {
        ArrayList<Integer> safeHavenId = new ArrayList<>();

        for (Creature salvo : salvos) {
            safeHavenId.add(salvo.getId());
        }

        return safeHavenId;

    }


    //Completo
    public boolean isDoorToSafeHaven(int x, int y) {
        for (SafeHaven safeHaven : safeHavens) {
            if (safeHaven.getX() == x && safeHaven.getY() == y) {
                return true;
            }
        }
        return false;
    }

    //Completo
    public int getEquipmentTypeId(int equipmentId) {
        for (Equipamento equipamento : equipamentos) {
            if (equipamento != null) {
                if (equipamento.getId() == equipmentId) {
                    return equipamento.getIdTipo();
                }
            }
        }
        return -1;
    }

    //Completo
    public String getEquipmentInfo(int equipmentId) {
        String info = "";
        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getId() == equipmentId) {
                switch (equipamento.getIdTipo()) {
                    case 0: {
                        info += "Escudo de Madeira | " + equipamento.getMunicao();
                        return info;
                    }
                    case 1: {
                        info += "Espada Hattori Hanzo";
                        return info;
                    }
                    case 2: {
                        info += "Pistola Walther PPK | " + equipamento.getMunicao();
                        return info;
                    }
                    case 3: {
                        info += "Escudo Táctico";
                        return info;
                    }
                    case 4: {
                        info += "Revista Maria";
                        return info;
                    }
                    case 5: {
                        info += "Cabeça de Alho";
                        return info;
                    }
                    case 6: {
                        info += "Estaca de Madeira";
                        return info;
                    }
                    case 7: {
                        info += "Garrafa de Lixívia (1 litro) | " + equipamento.getMunicao();
                        return info;
                    }
                    case 8: {
                        info += "Veneno | " + equipamento.getMunicao();
                        return info;
                    }
                    case 9: {
                        info += "Antídoto | " + equipamento.getMunicao();
                        return info;
                    }
                    case 10: {
                        info += "Beskar Helmet";
                        return info;
                    }
                }
            }
        }
        return "";
    }

    //Completo
    public int getEquipmentId(int creatureId) {
        for (Creature creature : creatures) {
            if (creature.getEquipa() == 10 && creature.getId() == creatureId) {
                if (((Vivos) creature).getEquipamento() != null) {
                    return ((Vivos) creature).getEquipamento().getId();
                }
            }
        }
        return 0;
    }

    public boolean saveGame(File fich) {
        //Real
        int[] tamanhoMapa = getWorldSize();
        int numCreatures = 0;

        String equipaAtualString = String.valueOf(equipaAtual);
        for (Creature creature : getCreatures()) {
            numCreatures++;
        }
        String numCreaturesString = String.valueOf(numCreatures);

        int numEquipamentos = 0;
        for (Equipamento equipamento : equipamentos) {
            numEquipamentos++;
        }
        String numEquipamentosString = String.valueOf(numEquipamentos);

        int numSafeHavens = 0;
        for (SafeHaven safeHaven : safeHavens) {
            numSafeHavens++;
        }
        String numSafeHavenString = String.valueOf(numSafeHavens);

        String turnoString = String.valueOf(turno);
        String ragnarokString = String.valueOf(ragnarok);
        try {
            BufferedWriter escritor = new BufferedWriter(new
                    FileWriter(fich));

            escritor.write(tamanhoMapa[0] + " " + tamanhoMapa[1]);
            escritor.newLine();
            escritor.write(equipaAtualString);
            escritor.newLine();
            escritor.write(numCreaturesString);
            escritor.newLine();

            for (Creature creature : creatures) {
                if (creature.getEquipa() == 10) {
                    escritor.write(creature.getId() + " : " + creature.getTipoCriatura() + " : " + creature.getNome() + " : " + creature.getCoordenadaX() + " : " + creature.getCoordenadaY());
                    escritor.write(" : " + creature.getEquipa() + " : " + ((Vivos) creature).getTotalEquipamento() + " : " + ((Vivos) creature).isSalvo() + " : ");
                    escritor.write(((Vivos) creature).isEnvenenado() + " : " + ((Vivos) creature).isMorto() + " : " + ((Vivos) creature).getNumRondasEnvenenado());
                    if (((Vivos) creature).getEquipamento() != null) {
                        escritor.write(" : " + ((Vivos) creature).getEquipamento().getId() + " : " + ((Vivos) creature).getEquipamento().getIdTipo() + " : " + ((Vivos) creature).getEquipamento().getCoordenadaX());
                        escritor.write(" : " + ((Vivos) creature).getEquipamento().getCoordenadaY());
                        if (((Vivos) creature).getEquipamento().getIdTipo() == 0 || ((Vivos) creature).getEquipamento().getIdTipo() == 2 || ((Vivos) creature).getEquipamento().getIdTipo() == 7 || ((Vivos) creature).getEquipamento().getIdTipo() == 8 || ((Vivos) creature).getEquipamento().getIdTipo() == 9) {
                            escritor.write(" : " + ((Vivos) creature).getEquipamento().getMunicao());
                        }
                    }
                    if (((Vivos) creature).getEquipamento() == null) {
                        escritor.write(" : " + 0 + " : " + -1 + " : " + -1 + " : " + -1);
                    }
                }

                if (creature.getEquipa() == 20) {
                    escritor.write(creature.getId() + " : " + creature.getTipoCriatura() + " : " + creature.getNome() + " : " + creature.getCoordenadaX() + " : " + creature.getCoordenadaY());
                    escritor.write(" : " + creature.getEquipa() + " : " + ((Outros) creature).getTotalEquipamento() + " : " + ((Outros) creature).isDestruido() + " : " + ((Outros) creature).isTransformado());
                }

                escritor.newLine();

            }
            escritor.write(numEquipamentosString);
            escritor.newLine();

            for (Equipamento equipamento : equipamentos) {
                if (equipamento.getIdTipo() == 0 || equipamento.getIdTipo() == 2 || equipamento.getIdTipo() == 7 || equipamento.getIdTipo() == 8 || equipamento.getIdTipo() == 9) {
                    escritor.write(equipamento.getId() + " : " + equipamento.getIdTipo() + " : " + equipamento.getCoordenadaX() + " : " + equipamento.getCoordenadaY() + " : " + equipamento.getMunicao());
                } else {
                    escritor.write(equipamento.getId() + " : " + equipamento.getIdTipo() + " : " + equipamento.getCoordenadaX() + " : " + equipamento.getCoordenadaY() + " : " + 0);
                }
                escritor.newLine();
            }

            escritor.write(numSafeHavenString);
            escritor.newLine();

            for (SafeHaven safeHaven : safeHavens) {
                escritor.write(safeHaven.getX() + " : " + safeHaven.getY());
                escritor.newLine();
            }

            escritor.write(turnoString);
            escritor.newLine();
            escritor.write(ragnarokString);

            escritor.close();
            return true;
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita de " + fich);
        }
        return false;
    }


    public boolean loadGame(File fich) {
        creatures.clear();
        equipamentos.clear();
        turno = 0;
        equipamentoTemporario = null;
        equipamentoCoordenadaD = false;
        equipamentoApanhadoCont = 0;
        equipamentoDestruidoCont = 0;
        transformado = false;
        ragnarok = 1;

        try {

            Scanner leitorFicheiro = new Scanner(fich);

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
                    int equipa = Integer.parseInt(partesCriaturas[5]);
                    if (equipa == 10) {
                        int totalEquipApanhado = Integer.parseInt(partesCriaturas[6]);
                        boolean salvo = Boolean.parseBoolean(partesCriaturas[7]);
                        boolean envenenado = Boolean.parseBoolean(partesCriaturas[8]);
                        boolean morto = Boolean.parseBoolean(partesCriaturas[9]);
                        int rondasEnvenenado = Integer.parseInt(partesCriaturas[10]);
                        int idEquip = Integer.parseInt(partesCriaturas[11]);
                        int idTipoEquip = Integer.parseInt(partesCriaturas[12]);
                        int xEquip = Integer.parseInt(partesCriaturas[13]);
                        int yEquip = Integer.parseInt(partesCriaturas[14]);
                        //Munição
                        if (idEquip != 0) {
                            if (idTipoEquip == 0 || idTipoEquip == 2 || idTipoEquip == 7 || idTipoEquip == 8 || idTipoEquip == 9) {
                                int municao = Integer.parseInt(partesCriaturas[15]);
                                Equipamento equipamento = new Equipamento(idEquip, idTipoEquip, xEquip, yEquip, municao);
                                equipamentos.add(equipamento);
                                Creature humano = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa, totalEquipApanhado, equipamento, salvo, envenenado, morto, rondasEnvenenado);
                                creatures.add(humano);
                                //Sem Munição
                            } else {
                                Equipamento equipamento = new Equipamento(idEquip, idTipoEquip, xEquip, yEquip);
                                equipamentos.add(equipamento);
                                Creature humano = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa, totalEquipApanhado, equipamento, salvo, envenenado, morto, rondasEnvenenado);
                                creatures.add(humano);
                            }
                        }
                        //Sem Equipamento
                        if (salvo == true) {
                            Creature humano = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa, totalEquipApanhado, salvo, envenenado, morto, rondasEnvenenado);
                            salvos.add(humano);
                        } else if (morto == true) {
                            Creature humano = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa, totalEquipApanhado, salvo, envenenado, morto, rondasEnvenenado);
                            destruidos.add(humano);
                        } else {
                            Creature humano = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa, totalEquipApanhado, salvo, envenenado, morto, rondasEnvenenado);
                            creatures.add(humano);
                        }

                    }

                    if (equipa == 20) {
                        int totalEquipDestruido = Integer.parseInt(partesCriaturas[6]);
                        boolean destruido = Boolean.parseBoolean(partesCriaturas[7]);
                        boolean transformado = Boolean.parseBoolean(partesCriaturas[8]);
                        if (destruido == false) {
                            Creature zombie = new Outros(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa, totalEquipDestruido, destruido, transformado);
                            creatures.add(zombie);
                        } else {
                            Creature zombie = new Outros(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa, totalEquipDestruido, destruido, transformado);
                            destruidos.add(zombie);
                        }
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
                    int municao = Integer.parseInt(partesEquipamentos[4]);
                    switch (tipo) {

                        case 0: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, municao);
                            equipamentos.add(equipamento);
                            break;
                        }
                        case 8: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, municao);
                            equipamentos.add(equipamento);
                            break;
                        }

                        case 9: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, municao);
                            equipamentos.add(equipamento);
                            break;
                        }
                        case 2: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, municao);
                            equipamentos.add(equipamento);
                            break;
                        }

                        case 7: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, municao);
                            equipamentos.add(equipamento);
                            break;
                        }
                    }
                    Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY);
                    equipamentos.add(equipamento);

                }


                //Número de safeHavens
                String numPortasLinha = leitorFicheiro.nextLine();
                int numPortasP = Integer.parseInt(numPortasLinha);

                //Coordenadas de safeHavens
                for (int i = 0; i < numPortasP; i++) {
                    String portasLinha = leitorFicheiro.nextLine();
                    String[] partesPortas = portasLinha.split(" : ");
                    xH = Integer.parseInt(partesPortas[0]);
                    yH = Integer.parseInt(partesPortas[1]);
                    SafeHaven safeHaven = new SafeHaven(xH, yH);
                    safeHavens.add(safeHaven);

                }

                String turnoLinha = leitorFicheiro.nextLine();
                turno = Integer.parseInt(numPortasLinha);

                String ragnarokLinha = leitorFicheiro.nextLine();
                ragnarok = Integer.parseInt(ragnarokLinha);
            }
            leitorFicheiro.close();
            return true;

        } catch (
                FileNotFoundException exception) {
            System.out.println("Erro: " + fich.toString() + " não foi encontrado.");
            return false;
        }

    }

    //Opcional
    public String[] popCultureExtravaganza() {
        String[] respostas = new String[14];

        respostas[0] = "Resident Evil";
        respostas[1] = "Evil Dead";
        respostas[2] = "I Am Legend";
        respostas[3] = "I Am Legend";
        respostas[4] = "Dragon Ball";
        respostas[5] = "World War Z";
        respostas[6] = "Mandalorians";
        respostas[7] = "1972";
        respostas[8] = "Kill Bill";
        respostas[9] = "1978";
        respostas[10] = "Bond, James Bond.";
        respostas[11] = "Lost";
        respostas[12] = "Chocho";
        respostas[13] = "Farrokh Bulsara";

        return respostas;

    }

    public Map<String, List<String>> getGameStatistics() {


        Map<String, List<String>> statiktoks = new HashMap<>();

        String chave0 = "os3ZombiesMaisTramados";

        String chave1 = "os3VivosMaisDuros";

        String chave2 = "tiposDeEquipamentoMaisUteis";

        String chave3 = "tiposDeZombieESeusEquipamentosDestruidos";

        String chave4 = "criaturasMaisEquipadas";

        // ------------------ Primeiro ------------------
        List<Outros> outros = new ArrayList<>();
                for (Creature creature : new ArrayList<>(creatures)) {
                    if (creature.getEquipa() == 20) {
                        outros.add((Outros) creature);
                    }
                }
                long numeroZombies = outros.stream()
                        .filter(creature -> ((Outros) creature).getNrTransformacoes() >= 1)
                        .collect(counting());

                if (numeroZombies < 3) {
                    List<String> valor0 = outros.stream()

                            .filter(creature -> ((Outros) creature).getNrTransformacoes() >= 1)
                            .map(creature -> creature.getId() + ":" + creature.getNome() + ":" + ((Outros) creature).getNrTransformacoes())
                            .collect(Collectors.toList());
                    statiktoks.put(chave0, valor0);
                } else {
                    List<String> valor0 = outros.stream()

                            .filter(creature -> ((Outros) creature).getNrTransformacoes() >= 1)
                            .sorted((c1, c2) -> ((Outros) c1).getNrTransformacoes() - ((Outros) c2).getNrTransformacoes())
                            .limit(3)
                            .map(creature -> creature.getId() + ":" + creature.getNome() + ":" + ((Outros) creature).getNrTransformacoes())
                            .collect(Collectors.toList());
                    statiktoks.put(chave0, valor0);
                }

        // ------------------ Segundo ------------------
        List<Vivos> vivos = new ArrayList<>();
        for (Creature creature : new ArrayList<>(creatures)) {
            if (creature.getEquipa() == 10) {
                vivos.add((Vivos) creature);
            }
        }
        long numeroHumanos = vivos.stream()
                .filter(creature -> ((Vivos) creature).getNumZombiesDestruidos() >= 1)
                .collect(counting());


        if (numeroHumanos < 3) {
            List<String> valor1 = vivos.stream()

                    .filter(creature -> ((Vivos) creature).getNumZombiesDestruidos() >= 1)
                    .map(creature -> creature.getId() + ":" + creature.getNome() + ":" + ((Vivos) creature).getNumZombiesDestruidos())
                    .collect(Collectors.toList());
            statiktoks.put(chave1, valor1);
        } else {
            List<String> valor1 = vivos.stream()

                    .filter(creature -> ((Vivos) creature).getNumZombiesDestruidos() >= 1)
                    .sorted((c1, c2) -> ((Vivos) c1).getNumZombiesDestruidos() - ((Vivos) c2).getNumZombiesDestruidos())
                    .limit(3)
                    .map(creature -> creature.getId() + ":" + creature.getNome() + ":" + ((Vivos) creature).getNumZombiesDestruidos())
                    .collect(Collectors.toList());
            statiktoks.put(chave1, valor1);
        }


        // ------------------ Terceiro ------------------
        List<String> valor2 = salvos.stream()

                .sorted((s1, s2) -> ((Vivos) s1).equipamento.getSalvou() - ((Vivos) s2).equipamento.getSalvou())
                .map(creature -> ((Vivos) creature).equipamento.getIdTipo() + ":" + ((Vivos) creature).equipamento.getSalvou())
                .collect(Collectors.toList());
                statiktoks.put(chave2, valor2);

        statiktoks.put(chave3, new ArrayList<String>());
        statiktoks.put(chave4, new ArrayList<String>());

        // ------------------ Quinto ------------------
        List<String> valor4 = creatures.stream()

                .sorted((s1, s2) -> (s2).getTotalEquipamento() - (s1).getTotalEquipamento())
                .limit(5)
                .map(creature -> creature.getId() + ":" + creature.getNome() + ":" + creature.getTotalEquipamento())
                .collect(Collectors.toList());
        statiktoks.put(chave4, valor4);

        statiktoks.put(chave3, new ArrayList<String>());


        return statiktoks;

    }

}

