package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {

    int x, y, equipaInicial, turno = 0, equipaAtual, equipamentoApanhadoCont = 0, equipamentoDestruidoCont = 0;
    int xH, yH;
    boolean equipamentoCoordenadaD = false;
    Equipamento equipamentoTemporario;
    ArrayList<Equipamento> equipamentos = new ArrayList<>();
    List<Creature> creatures = new ArrayList<>();
    List<SafeHaven> safeHavens = new ArrayList<>();


    public TWDGameManager() {

    }

    public boolean startGame(File ficheiroInicial) {
        //Reset all.
        creatures.clear();
        equipamentos.clear();
        turno = 0;
        equipamentoTemporario = null;
        equipamentoCoordenadaD = false;
        equipamentoApanhadoCont = 0;
        equipamentoDestruidoCont = 0;
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
                        int equipa = 20;
                        Creature zombie = new Outros(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa);
                        creatures.add(zombie);
                    }
                    if (tipo == 5 || tipo == 6 || tipo == 7 || tipo == 8 || tipo == 9) {
                        int equipa = 10;
                        Creature humano = new Vivos(id, tipo, nome, criaturaCoordenadaX, criaturaCoordenadaY, equipa);
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
                        }
                        case 8: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 1);
                            equipamentos.add(equipamento);
                        }

                        case 9: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 1);
                            equipamentos.add(equipamento);
                        }
                        case 2: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 3);
                            equipamentos.add(equipamento);
                        }

                        case 7: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 3);
                            equipamentos.add(equipamento);
                        }
                        default: {
                            Equipamento equipamento = new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY);
                            equipamentos.add(equipamento);
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
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + ficheiroInicial.toString() + " não foi encontrado.");
            return false;
        }
    }

    //Completo
    public int[] getWorldSize() {
        return new int[]{y, x};
    }

    //Completo
    public int getInitialTeam() {
        return equipaInicial;
    }

    //Completo
    public List<Creature> getCreatures() {
        return creatures;
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

    public boolean humanoIndefeso() {
        for (Creature creature : creatures) {
            if (((Vivos) creature).getEquipamento() == null ) {
                return true;
            }
        }
        return false;
    }

    public boolean mortoPorEstaca(int xD, int yD) {
        for (Creature creatureZombie : creatures) {
            if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                creatures.remove(creatureZombie);
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
                        creatures.remove(creatureZombie);
                        ((Vivos) creature).getEquipamento().setMunicao(((Vivos) creature).getEquipamento().getMunicao() - 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean mortoACabecada(int xD, int yD) {
        for (Creature creatureZombie : creatures) {
            if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                if (creatureZombie.getTipoCriatura() != 4) {
                    creatures.remove(creatureZombie);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean crianca(boolean mov1, int xO, int yO, int xD, int yD) {
        boolean defendeu = false;
        boolean matou = false;
        //Humano
        if (equipaAtual == 0) {
            if (mov1) {
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
                        if (zombieDestino(xD, yD)) {
                            if (((Vivos) creature).getEquipamento().ofensivo()) {

                                switch (((Vivos) creature).getEquipamento().idTipo) {

                                    case 1: {
                                        for (Creature creatureZombie : creatures) {
                                            if (creatureZombie.getCoordenadaX() == xD && creatureZombie.getCoordenadaY() == yD) {
                                                if (creatureZombie.getTipoCriatura() == 0) {
                                                    creatures.remove(creatureZombie);
                                                    matou = true;
                                                }
                                            }
                                        }
                                        if (!matou) {
                                            return false;
                                        }
                                    }

                                    case 2:  if (!(mortoPorBalazio(creature, xD, yD))) { return false; }

                                    case 6: if (!mortoPorEstaca(xD, yD)) { return false; }

                                    case 10: if (!(mortoACabecada(xD, yD))) { return false; }
                                }
                            }
                        }

                        return true;
                    }
                }
            }
        }

        //Zombie
        if (equipaAtual == 1) {

            if (mov1) {
                for (Creature creature : creatures) {
                    if (creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            equipamentoDestruidoCont++;
                            ((Outros) creature).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
                            equipamentos.remove(equipamentoTemporario);
                            equipamentoTemporario = null;
                            equipamentoCoordenadaD = false;
                        }
                        if (humanoDestino(xD,yD)) {
                            if (!(humanoIndefeso())) {
                                for (Creature creatureHumano : creatures) {
                                    if (creatureHumano.getCoordenadaX() == xD && creatureHumano.getCoordenadaY() == yD) {
                                        switch (((Vivos) creatureHumano).getEquipamento().getIdTipo()) {
                                            case 0:{
                                                equipamentos.remove((((Vivos) creatureHumano).getEquipamento()));
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 1: {
                                                creatures.remove(creature);
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 2: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    creatures.remove(creature);
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao())-1);
                                                } else {
                                                    creatures.remove(creatureHumano);
                                                    creature.setCoordenadaX(xD);
                                                    creature.setCoordenadaY(yD);
                                                }
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 3: {
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 4: {
                                                creatures.remove(creatureHumano);
                                                creature.setCoordenadaX(xD);
                                                creature.setCoordenadaY(yD);
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 5: {
                                                creatures.remove(creatureHumano);
                                                creature.setCoordenadaX(xD);
                                                creature.setCoordenadaY(yD);
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 6: {
                                                creatures.remove(creature);
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 7: {
                                                if ((((Vivos) creatureHumano).getEquipamento().getMunicao()) > 0) {
                                                    ((Vivos) creatureHumano).getEquipamento().setMunicao((((Vivos) creatureHumano).getEquipamento().getMunicao())-1);
                                                } else {
                                                    creatures.remove(creatureHumano);
                                                    creature.setCoordenadaX(xD);
                                                    creature.setCoordenadaY(yD);
                                                }
                                                turno++;
                                                equipaAtual = 0;
                                                return true;
                                            }

                                            case 8: {
                                                turno++;
                                                equipaAtual = 0;
                                            }

                                            case 9: {
                                                turno++;
                                                equipaAtual = 0;
                                            }

                                            case 10: {
                                                turno++;
                                                equipaAtual = 0;
                                            }

                                            default: return false;
                                        }
                                    }
                                }
                            }
                        }
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 0;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean adulto(boolean mov1, boolean mov2, boolean movD1, boolean movD2, int xO, int yO, int xD, int yD) {
        if (mov1 || mov2 || movD1 || movD2) {
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
                        creature.setCoordenadaX(xD);
                        creature.setCoordenadaY(yD);
                        turno++;
                        equipaAtual = 1;
                        return true;
                    }
                }
            }
        return false;
    }

    public boolean militar(boolean mov1, boolean mov2, boolean mov3, boolean movD1, boolean movD2, boolean movD3,
                           int xO, int yO, int xD, int yD) {
        if (mov1 || mov2 || mov3 || movD1 || movD2 || movD3) {
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
                    creature.setCoordenadaX(xD);
                    creature.setCoordenadaY(yD);
                    turno++;
                    equipaAtual = 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean idoso(boolean movimento1) {
        return true;
    }

    public boolean cao(boolean movimento1) {
        return true;
    }

    public boolean vampiro(boolean movimento1) {
        return true;
    }

    public boolean move(int xO, int yO, int xD, int yD) {

        //Vertical e Horizontal              Esquerda                       Direita                    Cima                           Baixo
        final boolean movMax1 = xD - 1 == xO && yD == yO || xD + 1 == xO && yD == yO || yD - 1 == yO && xD == xO || yD + 1 == yO && xD == xO;
        final boolean movMax2 = xD - 2 == xO && yD == yO || xD + 2 == xO && yD == yO || yD - 2 == yO && xD == xO || yD + 2 == yO && xD == xO;
        final boolean movMax3 = xD - 3 == xO && yD == yO || xD + 3 == xO && yD == yO || yD - 3 == yO && xD == xO || yD + 3 == yO && xD == xO;
        //Diagonal                                  NO                              NE                        SO                        SE
        final boolean movDMax1 = xD - 1 == xO && yD - 1 == yO || xD + 1 == xO && yD - 1 == yO || yD + 1 == yO && xD - 1 == xO || yD + 1 == yO && xD + 1 == xO;
        final boolean movDMax2 = xD - 2 == xO && yD - 2 == yO || xD + 2 == xO && yD - 2 == yO || yD + 2 == yO && xD - 2 == xO || yD + 2 == yO && xD + 2 == xO;
        final boolean movDMax3 = xD - 3 == xO && yD - 3 == yO || xD + 3 == xO && yD - 3 == yO || yD + 3 == yO && xD - 3 == xO || yD + 3 == yO && xD + 3 == xO;

        //Humano não pode ir para cima de outro humano
        if (equipaAtual == 0) {
            if (humanoDestino(xD, yD)) {
                return false;
            }
        }

        //zombie não pode ir para cima de outro zombie
        if (equipaAtual == 1) {
            if (zombieDestino(xD, yD)) {
                return false;
            }
        }

        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getCoordenadaX() == xD && equipamento.getCoordenadaY() == yD) {
                equipamentoCoordenadaD = true;
                equipamentoTemporario = equipamento;

                for (Creature creature : creatures) {
                    if (equipaAtual == 1 && creature.getCoordenadaX() == xO && creature.getCoordenadaY() == yO) {
                        equipamentoCoordenadaD = false;
                        equipamentoTemporario = null;
                    }
                }

            }
        }

        if (equipaAtual == 0) {
            for (Creature creature : creatures) {
                if (creature.getEquipa() == 10) {
                    switch (creature.getTipoCriatura()) {

                        //Criança
                        case 5: {
                            crianca(movMax1, xO, yO, xD, yD);
                        }

                        //Adulto
                        case 6: {
                            adulto(movMax1, movDMax2, movMax2, movMax2, xO, yO, xD, yD);
                        }

                        //Militar
                        case 7: {
                            militar(movMax1, movMax2, movMax3, movDMax1, movDMax2, movDMax3, xO, yO, xD, yD);
                        }

                        //Idoso
                        case 8: {

                        }

                        //Cao
                        case 9: {
                        }
                    }
                }
            }
        }
/*

        if (equipaAtual == 1) {
            if (movimentoPossivel) {
                for (int i = 0; i < zombies.size(); i++) {
                    if (zombies.get(i).getCoordenadaX() == xO && zombies.get(i).getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            equipamentoDestruidoCont++;
                            zombies.get(i).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
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
 */
        return false;
    }


    public boolean gameIsOver() {

        int ocorrencias = 0;

        if (turno >= 12) {
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


        for (SafeHaven safeHaven : safeHavens) {
            resultados.add(safeHaven.vivos.getId() + " " + (safeHaven.vivos.getNome()) + "\n");
        }

        resultados.add("\nEnvenenados / Destruidos\n");
        resultados.add("OS VIVOS\n");

        for (Creature creature : creatures) {
            resultados.add(creature.getId() + " " + creature.getNome());
        }

        resultados.add("\nOS OUTROS\n");


        for (Creature creature : creatures) {
            if (creature.destruido) {
                resultados.add(creature.getId() + " " + creature.getNome() + "\n");
            }
        }

        return resultados;


    }


    //Completo
    public boolean isDay() {
        if (turno == 0 || turno == 1 || turno == 4 || turno == 5 || turno == 8 || turno == 9 || turno == 12) {
            return true;
        } else {
            return false;
        }
    }

    //Completo
    public List<Integer> getIdsInSafeHaven() {
        ArrayList<Integer> safeHavenId = new ArrayList<>();

        for (SafeHaven safeHaven : safeHavens) {
            if (safeHaven.vivos != null) {
                safeHavenId.add(safeHaven.vivos.getId());
            }
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
            if (equipamento.getId() == equipmentId) {
                return equipamento.getIdTipo();
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

        try {
            fich = new File("jogo.txt");
        } catch (Exception e) {
            System.out.println("");
        }

        return true;
    }


    public boolean loadGame(File fich) {
        return true;
    }

    //Opcional
    public String[] popCultureExtravaganza() {
        return null;
    }

}

