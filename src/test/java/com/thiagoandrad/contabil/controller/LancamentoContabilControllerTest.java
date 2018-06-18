package com.thiagoandrad.contabil.controller;

import com.thiagoandrad.contabil.domain.LancamentoContabil;
import com.thiagoandrad.contabil.repository.LancamentoContabilRepository;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LancamentoContabilControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LancamentoContabilRepository repository;

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

    @Test
    public void deveLancarErroQuandoDadosForemInvalidos() throws Exception {
        String json = new JSONObject()
                .put("contaContabil", null)
                .put("data", null)
                .put("valor", null)
                .toString();

        mvc.perform(post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarUmLancamento() throws Exception {
        LancamentoContabil lancamentoContabil = new LancamentoContabil(123456L, LocalDate.now(), new BigDecimal("100.00"));
        repository.save(lancamentoContabil);

        mvc.perform(get("/lancamentos-contabeis/" + lancamentoContabil.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(lancamentoContabil.getId())))
                .andExpect(jsonPath("$.contaContabil", is(lancamentoContabil.getContaContabil().intValue())))
                .andExpect(jsonPath("$.data", is(lancamentoContabil.getData().format(DateTimeFormatter.ofPattern("yyyyMMdd")))))
                .andExpect(jsonPath("$.valor", is(lancamentoContabil.getValor().doubleValue())));
    }

    @Test
    public void retorna404CasoNaoEncontreLancamento() throws Exception {
        mvc.perform(get("/lancamentos-contabeis/blablabla"))
                .andExpect(status().isNotFound());
    }
}