package pt.ulusofona.lp2.theWalkingDEISIGame;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class TestTWDGameManager {

    @Test
    public void testaMove01(){
        //Andar uma casa para cima
        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(3,3,3,2);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove02(){
        //Ir para cima da casa com uma arma
        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(3,3,2,3);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove03(){
        //Ir para cima de um zombie (False)
        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = false;
        boolean resultadoObtido = teste.move(3,4,4,4);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove04(){
        //Tentar andar na diagonal
        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = false;
        boolean resultadoObtido = teste.move(3,3,4,2);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

}