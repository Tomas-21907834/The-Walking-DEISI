package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class InvalidTWDInitialFileException extends Exception {
    File ficheiroInicial;


    public InvalidTWDInitialFileException(File ficheiroInicial) {
        this.ficheiroInicial = ficheiroInicial;
    }

    public boolean validNrOfCreatures() throws FileNotFoundException {
        try {
            Scanner leitorFicheiro = new Scanner(ficheiroInicial);
            while (leitorFicheiro.hasNextLine()) {
                leitorFicheiro.nextLine();
                leitorFicheiro.nextLine();
                String numCriaturasLinha = leitorFicheiro.nextLine();
                int numLinhasC = Integer.parseInt(numCriaturasLinha);
                if (numLinhasC < 2) {
                    return false;
                }
                for (int i = 0; i < numLinhasC; i++) {
                    leitorFicheiro.nextLine();
                }

                String numEquipamentosLinha = leitorFicheiro.nextLine();
                int numLinhasE = Integer.parseInt(numEquipamentosLinha);
                for (int i = 0; i < numLinhasE; i++) {
                    leitorFicheiro.nextLine();
                }
                String numSafeHavensLinha = leitorFicheiro.nextLine();
                int numLinhasS = Integer.parseInt(numSafeHavensLinha);
                for (int i = 0; i < numLinhasS; i++) {
                    leitorFicheiro.nextLine();
                }
            }
            leitorFicheiro.close();
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + ficheiroInicial.toString() + " não foi encontrado.");
            throw exception;
        }
    }

    public boolean validCreatureDefinition() throws FileNotFoundException {
        int count = 0;
        try {

            Scanner leitorFicheiro = new Scanner(ficheiroInicial);
            while (leitorFicheiro.hasNextLine()) {
                leitorFicheiro.nextLine();
                leitorFicheiro.nextLine();
                String numCriaturasLinha = leitorFicheiro.nextLine();
                int numLinhasC = Integer.parseInt(numCriaturasLinha);
                for (int i = 0; i < numLinhasC; i++) {
                    String criaturasLinha = leitorFicheiro.nextLine();
                    String[] partesCriaturas = criaturasLinha.split(" : ");
                    if (partesCriaturas.length != 5) {
                        return false;
                    }
                }
                String numEquipamentosLinha = leitorFicheiro.nextLine();
                int numLinhasE = Integer.parseInt(numEquipamentosLinha);
                for (int i = 0; i < numLinhasE; i++) {
                    leitorFicheiro.nextLine();
                }
                String numSafeHavensLinha = leitorFicheiro.nextLine();
                int numLinhasS = Integer.parseInt(numSafeHavensLinha);
                for (int i = 0; i < numLinhasS; i++) {
                    leitorFicheiro.nextLine();
                }
            }
            leitorFicheiro.close();
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + ficheiroInicial.toString() + " não foi encontrado.");
            throw exception;
        }
    }

    public String getErroneousLine() throws FileNotFoundException {
        try {
            Scanner leitorFicheiro = new Scanner(ficheiroInicial);
            while (leitorFicheiro.hasNextLine()) {
                leitorFicheiro.nextLine();
                leitorFicheiro.nextLine();
                String numCriaturasLinha = leitorFicheiro.nextLine();
                int numLinhasC = Integer.parseInt(numCriaturasLinha);
                for (int i = 0; i < numLinhasC; i++) {

                    String criaturasLinha = leitorFicheiro.nextLine();
                    String[] partesCriaturas = criaturasLinha.split(" : ");
                    if (partesCriaturas.length != 5) {
                        return criaturasLinha;
                    }
                }

                String numEquipamentosLinha = leitorFicheiro.nextLine();
                int numLinhasE = Integer.parseInt(numEquipamentosLinha);
                for (int i = 0; i < numLinhasE; i++) {
                    leitorFicheiro.nextLine();
                }
                String numSafeHavensLinha = leitorFicheiro.nextLine();
                int numLinhasS = Integer.parseInt(numSafeHavensLinha);
                for (int i = 0; i < numLinhasS; i++) {
                    leitorFicheiro.nextLine();
                }
            }
            leitorFicheiro.close();

        } catch (FileNotFoundException exception) {
            System.out.println("Erro: " + ficheiroInicial.toString() + " não foi encontrado.");
            throw exception;
        }
        return "";
    }
}

