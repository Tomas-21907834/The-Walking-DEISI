package pt.ulusofona.lp2.theWalkingDEISIGame;
import org.junit.Test;

import java.io.File;


import static org.junit.Assert.assertEquals;



public class TestTWDGameManager {

    @Test
   public void testsStatistics() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);


        boolean resultadoEsperado = false;

        boolean resultadoObtido = teste.getGameStatistics().isEmpty();

        assertEquals(resultadoEsperado,resultadoObtido);


    }





    @Test
    public void testaMove01() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,0,1,0);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove02() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,1,1,1);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove03() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,2,1,2);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove05() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,3,1,3);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove06() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,5,1,5);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove07() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,6,1,6);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove08() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,7,1,7);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove09() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(0,8,1,8);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove010() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = false;
        boolean resultadoObtido = teste.move(0,9,1,9);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove11() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(5,4,6,5);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove12() throws Exception{

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = true;
        boolean resultadoObtido = teste.move(5,4,7,6);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove13() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);


        boolean um = teste.move(0, 2, 1, 2);

        boolean dois = teste.move(4, 2, 2, 2);

        boolean tres = teste.move(1, 2, 2, 2);

        boolean quatro = teste.move(4, 4, 2, 6);

        boolean cinco = teste.move(0, 6, 1, 6);

        boolean seis = teste.move(4, 3, 3, 3);

        boolean sete = teste.move(1, 6, 2, 6);

        boolean oito = teste.move(2, 8, 2, 9);


        assertEquals(true,um);
        assertEquals(true,dois);
        assertEquals(true,tres);
        assertEquals(true,quatro);
        assertEquals(true,cinco);
        assertEquals(true,seis);
        assertEquals(true,sete);
        assertEquals(true,oito);

    }

    @Test
    public void testaMove14() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = false;
        boolean resultadoObtido = teste.move(3,4,4,4);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove15() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);

        boolean resultadoEsperado = false;
        boolean resultadoObtido = teste.move(3,3,4,2);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove16() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);


        boolean um = teste.move(5,3,3,3);
        boolean dois = teste.move(5,3,2,3);
        boolean tres = teste.move(5,3,1,3);
        boolean quatro = teste.move(5,3,3,5);
        boolean cinco = teste.move(5,3,2,6);
        boolean seis = teste.move(5,3,3,1);
        boolean sete = teste.move(5,3,3,5);
        boolean oito = teste.move(5,3,2,0);
        boolean nove = teste.move(0,2,2,0);
        boolean dez = teste.move(0,2,2,2);
        boolean onze = teste.move(0,2,2,3);
        boolean doze = teste.move(0,2,2,4);
        boolean treze = teste.move(0,2,3,5);

        assertEquals(false,um);
        assertEquals(false,dois);
        assertEquals(false,tres);
        assertEquals(false,quatro);
        assertEquals(false,cinco);
        assertEquals(false,seis);
        assertEquals(false,sete);
        assertEquals(false,oito);
        assertEquals(false,nove);
        assertEquals(false,dez);
        assertEquals(false,onze);
        assertEquals(false,doze);
        assertEquals(false,treze);
    }

    @Test
    public void testaMove17() throws Exception {

        TWDGameManager teste = new TWDGameManager();

        File ficheiro = new File("test-files/jogo.txt");
        teste.startGame(ficheiro);


        boolean um = teste.move(0,8,1,8);
        boolean dois = teste.move(4,2,5,2);
        boolean tres = teste.move(0,2,2,2);
        boolean quatro = teste.move(5,3,8,6);

        assertEquals(true,um);
        assertEquals(true,dois);
        assertEquals(false,tres);
        assertEquals(true,quatro);
    }

}

