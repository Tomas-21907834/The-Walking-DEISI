package pt.ulusofona.lp2.theWalkingDEISIGame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestTWDGameManager {

    @Test
    public void testaMove01(){

        TWDGameManager teste = new TWDGameManager();



        boolean resultadoEsperado = true;

        boolean resultadoObtido = teste.move(0,0,0,1);

        assertEquals(resultadoEsperado,resultadoObtido);


    }
    /*
    @Test
    public void testaMove02(){
        TWDGameManager teste = new TWDGameManager();

        boolean resultadoEsperado = false;

        boolean resultadoObtido = teste.move(-1,0,-1,-1);

        assertEquals(resultadoEsperado,resultadoObtido);
    }
    @Test
    public void testaMove03(){
        TWDGameManager teste = new TWDGameManager();

        boolean resultadoEsperado = false;

        boolean resultadoObtido = teste.move(2,0,2,2);

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void testaMove04(){
        TWDGameManager teste = new TWDGameManager();

        boolean resultadoEsperado = true;

        boolean resultadoObtido = teste.move(3,1,2,1);

        assertEquals(resultadoEsperado,resultadoObtido);
    }


     */
}