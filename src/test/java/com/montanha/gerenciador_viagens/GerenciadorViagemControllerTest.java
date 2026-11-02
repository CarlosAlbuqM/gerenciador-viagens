package com.montanha.gerenciador_viagens;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class GerenciadorViagemControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void deveRetornar400QuandoLocalDeDestinoTemMenosDe3Caracteres() throws Exception {
        String json = """
                {
                    "localDeDestino": "Ca",
                    "dataPartida": "2018-01-10",
                    "dataRetorno": "2018-02-20",
                    "acompanhante": "Camila"
                }
                """;

        mockMvc.perform(post("/api/viagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Local de Destino deve estar entre 3 e 40 caracteres"));
    }

    @Test
void deveCadastrarViagemComDadosValidos() throws Exception {
    String json = """
            {
                "localDeDestino": "Itapema - SC",
                "dataPartida": "2018-01-10",
                "dataRetorno": "2018-02-20",
                "acompanhante": "Camila"
            }
            """;

    mockMvc.perform(post("/api/viagens")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.id").exists())
            .andExpect(jsonPath("$.data.localDeDestino").value("Itapema - SC"))
            .andExpect(jsonPath("$.errors").isEmpty());
}

@Test
void deveRetornar400QuandoDataDeRetornoEhAnteriorAPartida() throws Exception {
    String json = """
            {
                "localDeDestino": "Maceió - AL",
                "dataPartida": "2026-12-20",
                "dataRetorno": "2026-12-10",
                "acompanhante": "Carlos"
            }
            """;

    mockMvc.perform(post("/api/viagens")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors[0]")
                    .value("Data de retorno não pode ser anterior à data de partida"));
}

@Test
void deveRetornar404QuandoViagemNaoExiste() throws Exception {
    mockMvc.perform(get("/api/viagens/99999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.errors[0]")
                    .value("Não existe esta viagem cadastrada"));
}
}