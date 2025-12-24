package org.framework.backend;

import org.framework.core.Request;
import org.framework.core.Validar;
import org.junit.jupiter.api.Test;

public class RegressivoApiCorreiosTest {

    @Test
    void deveRetornarStatus200QuandoBuscarCepValido(){

        var response = Request
                .get("https://viacep.com.br/ws/07178540/json/")
                .header("Accept", "application/json")
                .send();

        Validar.statusCode(response.statusCode(), 200, "Validando StatusCode igual a 200 OK");
        Validar.naoVazio(response.body(), "Validando o response da requisição é não vazio");
    }

    @Test
    void deveRetornarStatus400QuandoBuscarCepInvalido(){

        var response = Request
                .get("https://viacep.com.br/ws/2/json/")
                .header("Accept", "application/json")
                .send();

        Validar.statusCode(response.statusCode(), 400, "Validando StatusCode igual a 400 Bad Request");
        Validar.naoVazio(response.body(), "Validando o response da requisição é não vazio");
    }

    @Test
    void deveRetornarStatus400QuandoBuscarComLetra(){

        var response = Request
                .get("https://viacep.com.br/ws/teste/json/")
                .header("Accept", "application/json")
                .send();

        Validar.statusCode(response.statusCode(), 400, "Validando StatusCode igual a 400 Bad Request");
        Validar.naoVazio(response.body(), "Validando o response da requisição é não vazio");
    }

}
