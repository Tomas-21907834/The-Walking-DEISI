package pt.ulusofona.lp2.theWalkingDEISIGame;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {

    int x, y, equipaInicial, turno=0, equipaAtual, equipamentoApanhadoCont=0, equipamentoDestruidoCont=0;
    int xH, yH;
    boolean equipamentoCoordenadaD=false;
    Equipamento equipamentoTemporario;
    ArrayList<Equipamento> equipamentos=new ArrayList<>();
    List<Creature> creatures=new ArrayList<>();
    List<SafeHaven> safeHavens=new ArrayList<>();


    public TWDGameManager() {

    }

    public boolean startGame(File ficheiroInicial) {
        //Reset all.
        creatures.clear();
        equipamentos.clear();
        turno=0;
        equipamentoTemporario=null;
        equipamentoCoordenadaD=false;
        equipamentoApanhadoCont=0;
        equipamentoDestruidoCont=0;
        try {
            Scanner leitorFicheiro=new Scanner(ficheiroInicial);

            while (leitorFicheiro.hasNextLine()) {

                //Primeira Linha
                String coordenadaLinha=leitorFicheiro.nextLine();
                String[] partesCoordenadas=coordenadaLinha.split(" ");
                x=Integer.parseInt(partesCoordenadas[0]);
                y=Integer.parseInt(partesCoordenadas[1]);

                //Segunda Linha
                String equipaLinha=leitorFicheiro.nextLine();
                equipaInicial=Integer.parseInt(equipaLinha);
                equipaAtual=equipaInicial;

                //Terceira Linha
                String numCriaturasLinha=leitorFicheiro.nextLine();
                int numLinhasC=Integer.parseInt(numCriaturasLinha);

                //Número de linhas dependendo da Terceira Linha
                for (int i=0; i < numLinhasC; i++) {
                    String criaturasLinha=leitorFicheiro.nextLine();
                    String[] partesCriaturas=criaturasLinha.split(" : ");
                    int id=Integer.parseInt(partesCriaturas[0]);
                    int tipo=Integer.parseInt(partesCriaturas[1]);
                    String nome=partesCriaturas[2];
                    int criaturaCoordenadaX=Integer.parseInt(partesCriaturas[3]);
                    int criaturaCoordenadaY=Integer.parseInt(partesCriaturas[4]);

                    if (tipo == 20) {
                        Creature zombie=new Outros(id, nome, tipo, criaturaCoordenadaX, criaturaCoordenadaY);
                        creatures.add(zombie);
                    }
                    if (tipo == 10) {
                        Creature humano=new Vivos(id, nome, tipo, criaturaCoordenadaX, criaturaCoordenadaY);
                        creatures.add(humano);
                    }
                }

                //Quinta Linha
                String numEquipamentosLinha=leitorFicheiro.nextLine();
                int numLinhasE=Integer.parseInt(numEquipamentosLinha);

                //Número de linhas dependendo da Quinta Linha
                for (int i=0; i < numLinhasE; i++) {
                    String equipamentosLinha=leitorFicheiro.nextLine();
                    String[] partesEquipamentos=equipamentosLinha.split(" : ");
                    int id=Integer.parseInt(partesEquipamentos[0]);
                    int tipo=Integer.parseInt(partesEquipamentos[1]);
                    int equipamentoCoordenadaX=Integer.parseInt(partesEquipamentos[2]);
                    int equipamentoCoordenadaY=Integer.parseInt(partesEquipamentos[3]);

                    switch (tipo) {
                        case 0, 8, 9 -> {
                            Equipamento equipamento=new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 1);
                            equipamentos.add(equipamento);
                        }
                        case 2, 7 -> {
                            Equipamento equipamento1=new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY, 3);
                            equipamentos.add(equipamento1);
                        }
                        default -> {
                            Equipamento equipamento2=new Equipamento(id, tipo, equipamentoCoordenadaX, equipamentoCoordenadaY);
                            equipamentos.add(equipamento2);
                        }
                    }

                }

                //Número de safeHavens
                String numPortasLinha=leitorFicheiro.nextLine();
                int numPortasP=Integer.parseInt(numPortasLinha);

                //Coordenadas de safeHavens
                for (int i=0; i < numPortasP; i++) {
                    String portasLinha=leitorFicheiro.nextLine();
                    String[] partesPortas=portasLinha.split(" : ");
                    xH=Integer.parseInt(partesPortas[0]);
                    yH=Integer.parseInt(partesPortas[1]);
                    SafeHaven safeHaven=new SafeHaven(xH, yH);
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

    public boolean move(int xO, int yO, int xD, int yD) {
        final boolean movimentoPossivel=xD - 1 == xO && yD == yO || xD + 1 == xO && yD == yO || yD - 1 == yO && xD == xO || yD + 1 == yO && xD == xO;

        for (int i=0; i < creatures.size(); i++) {
            if (creatures.get(i).getCoordenadaX() == xD && creatures.get(i).getCoordenadaY() == yD) {
                return false;
            }
        }

        for (int i=0; i < equipamentos.size(); i++) {
            if (equipamentos.get(i).getCoordenadaX() == xD && equipamentos.get(i).getCoordenadaY() == yD) {
                equipamentoCoordenadaD=true;
                equipamentoTemporario=equipamentos.get(i);

                for (int j=0; j < humanos.size(); j++) {
                    if (equipaAtual == 1 && humanos.get(j).getCoordenadaX() == xO && humanos.get(j).getCoordenadaY() == yO && movimentoPossivel) {
                        equipamentoCoordenadaD=false;
                        equipamentoTemporario=null;
                    }
                }

                for (int j=0; j < zombies.size(); j++) {
                    if (equipaAtual == 0 && zombies.get(j).getCoordenadaX() == xO && zombies.get(j).getCoordenadaY() == yO && movimentoPossivel) {
                        equipamentoCoordenadaD=false;
                        equipamentoTemporario=null;
                    }
                }

            }
        }


        if (equipaAtual == 0) {
            if (movimentoPossivel) {
                for (int i=0; i < humanos.size(); i++) {
                    if (humanos.get(i).getCoordenadaX() == xO && humanos.get(i).getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            equipamentoApanhadoCont++;
                            humanos.get(i).setTotalEquipamentoApanhado(equipamentoApanhadoCont);
                            humanos.get(i).setEquipamento(equipamentoTemporario);
                            equipamentoTemporario=null;
                            equipamentoCoordenadaD=false;
                        }
                        if (humanos.get(i).getEquipamento() != null) {
                            humanos.get(i).getEquipamento().setCoordenadaX(xD);
                            humanos.get(i).getEquipamento().setCoordenadaY(yD);
                        }
                        humanos.get(i).setCoordenadaX(xD);
                        humanos.get(i).setCoordenadaY(yD);
                        turno++;
                        equipaAtual=1;
                        return true;
                    }
                }
            }
        }

        if (equipaAtual == 1) {
            if (movimentoPossivel) {
                for (int i=0; i < zombies.size(); i++) {
                    if (zombies.get(i).getCoordenadaX() == xO && zombies.get(i).getCoordenadaY() == yO) {
                        if (equipamentoCoordenadaD) {
                            equipamentoDestruidoCont++;
                            zombies.get(i).setTotalEquipamentoDestruido(equipamentoDestruidoCont);
                            equipamentos.remove(equipamentoTemporario);
                            equipamentoTemporario=null;
                            equipamentoCoordenadaD=false;
                        }
                        zombies.get(i).setCoordenadaX(xD);
                        zombies.get(i).setCoordenadaY(yD);
                        turno++;
                        equipaAtual=0;
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean gameIsOver() {

        int ocorrencias=0;

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
        ArrayList<String> authors=new ArrayList<>();

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
        ArrayList<String> resultados=new ArrayList<>();

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

    //Completo?
    public List<Integer> getIdsInSafeHaven() {
        ArrayList<Integer> safeHavenId=new ArrayList<>();


        for (SafeHaven safeHaven : safeHavens) {
            safeHavenId.add(safeHaven.vivos.getId());
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
        String info="";
        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getId() == equipmentId) {
                switch (equipamento.getIdTipo()) {
                    case 0 -> {
                        info+="Escudo de Madeira | " + equipamento.getMunicao();
                        return info;
                    }
                    case 1 -> {
                        info+="Espada Hattori Hanzo";
                        return info;
                    }
                    case 2 -> {
                        info+="Pistola Walther PPK | " + equipamento.getMunicao();
                        return info;
                    }
                    case 3 -> {
                        info+="Escudo Táctico";
                        return info;
                    }
                    case 4 -> {
                        info+="Revista Maria";
                        return info;
                    }
                    case 5 -> {
                        info+="Cabeça de Alho";
                        return info;
                    }
                    case 6 -> {
                        info+="Estaca de Madeira";
                        return info;
                    }
                    case 7 -> {
                        info+="Garrafa de Lixívia (1 litro) | " + equipamento.getMunicao();
                        return info;
                    }
                    case 8 -> {
                        info+="Veneno | " + equipamento.getMunicao();
                        return info;
                    }
                    case 9 -> {
                        info+="Antídoto | " + equipamento.getMunicao();
                        return info;
                    }
                    case 10 -> {
                        info+="Beskar Helmet";
                        return info;
                    }
                }
            }
        }
        return "";
    }


    public boolean saveGame(File fich) {

        try {
            fich=new File("jogo.txt");
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


    //Completo
    public int getEquipmentId(int creatureId) {
        for (Creature creature : creatures) {
            if (creature.getEquipa() == 10 && creature.getId() == creatureId) {
                return ((Vivos) creature).getEquipamento().getId();
            }
        }
        return 0;
    }

}

