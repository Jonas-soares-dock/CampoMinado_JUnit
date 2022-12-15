import org.jonasJava.exception.ExplosaoException;
import org.jonasJava.model.Campo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {

    private Campo campo;

    @BeforeEach
    void iniciarCampo (){
       campo = new Campo(3,3);
    }

    @Test
    void testeVizinhoDistancia1Esquerda(){
        Campo vizinho = new Campo(3,2);
        boolean resultado = campo.adicionarvizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void testeVizinhoDistancia1Direita(){
        Campo vizinho = new Campo(3,4);
        boolean resultado = campo.adicionarvizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void testeVizinhoDistancia1Cima(){
        Campo vizinho = new Campo(2,3);
        boolean resultado = campo.adicionarvizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void testeVizinhoDistancia1Baixo(){
        Campo vizinho = new Campo(4,3);
        boolean resultado = campo.adicionarvizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia2Diagonal(){
        Campo vizinho = new Campo(4,4);
        boolean resultado = campo.adicionarvizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void testeNaoVizinhoDistancia(){
        Campo vizinho = new Campo(6,9);
        boolean resultado = campo.adicionarvizinho(vizinho);
        assertFalse(resultado);
    }
    @Test
    void testeValorPadraoAtributoMarcado (){
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcado (){
        campo.alternarMarcado();
        assertTrue(campo.isMarcado());
    }
    @Test
    void testeAlternarMarcadoDuasChamadas (){
        campo.alternarMarcado();
        campo.alternarMarcado();
        assertFalse(campo.isMarcado());
    }
    @Test
    void testeAbrirNaoMinadoNaoMarcado (){
        assertTrue(campo.abrir());
    }

    @Test
    void testeAbrirNaoMinadoMarcado (){
        campo.alternarMarcado();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoMarcado (){
        campo.alternarMarcado();
        campo.minar();
        assertFalse(campo.abrir());
    }
    @Test
    void testeAbrirMinadoNaoMarcado (){
        campo.minar();
        assertThrows(ExplosaoException.class, () -> {
            campo.abrir();
        });
    }

    @Test
    void testeAbrirComVizinhos(){
        Campo campo11 = new Campo(1,1);
        Campo campo22 = new Campo(2,2);

        campo22.adicionarvizinho(campo11);
        campo.adicionarvizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    void testeAbrirComVizinhos2(){
        Campo campo11 = new Campo(1,1);
        Campo campo12 = new Campo(1,2);
        campo12.minar();
        Campo campo22 = new Campo(2,2);

        campo22.adicionarvizinho(campo11);
        campo22.adicionarvizinho(campo12);
        campo.adicionarvizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());
    }

}