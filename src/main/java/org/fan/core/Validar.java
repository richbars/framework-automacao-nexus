package org.fan.core;
import org.junit.jupiter.api.Assertions;

public class Validar {

    private Validar() {}

    public static void igual(String atual, String esperado, String mensagem) {
        Assertions.assertEquals(
                atual,
                esperado,
                mensagem
        );
    }

    public static void igual(Integer atual, Integer esperado, String mensagem) {
        Assertions.assertEquals(
                atual,
                esperado,
                mensagem
        );
    }

    public static void naoVazio(String texto, String mensagem) {
        Assertions.assertNotNull(
                texto,
                mensagem
        );
    }

    public static void statusCode(Integer esperado, Integer atual, String mensagem) {
        Assertions.assertEquals(
                esperado,
                atual,
                mensagem
        );
    }



}
