package com.thiagoandrad.contabil.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LancamentoContabilControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void deveSalvarUmNovoLancamentoContabil() throws Exception {
        int contaContabil = 1111001;
        int data = 20170130;
        BigDecimal valor = BigDecimal.valueOf(25000.15);
        String json = new JSONObject()
                .put("contaContabil", contaContabil)
                .put("data", data)
                .put("valor", valor)
                .toString();

        mvc.perform(post("/lancamentos-contabeis/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/lancamentos-contabeis/")));
    }
}