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

    @Test
    public void retornaLancamentosConformeContaContabil() throws Exception {
        long contaContabil = 123456L;
        LancamentoContabil lancamentoContabil1 = new LancamentoContabil(contaContabil, LocalDate.now(), new BigDecimal("100.00"));
        LancamentoContabil lancamentoContabil2 = new LancamentoContabil(contaContabil, LocalDate.now(), new BigDecimal("200.00"));
        repository.save(lancamentoContabil1);
        repository.save(lancamentoContabil2);

        mvc.perform(get("/lancamentos-contabeis/?contaContabil=" + contaContabil))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(lancamentoContabil1.getId())))
                .andExpect(jsonPath("$.[0].contaContabil", is(lancamentoContabil1.getContaContabil().intValue())))
                .andExpect(jsonPath("$.[0].data", is(lancamentoContabil1.getData().format(DateTimeFormatter.ofPattern("yyyyMMdd")))))
                .andExpect(jsonPath("$.[0].valor", is(lancamentoContabil1.getValor().doubleValue())))
                .andExpect(jsonPath("$.[1].id", is(lancamentoContabil2.getId())))
                .andExpect(jsonPath("$.[1].contaContabil", is(lancamentoContabil2.getContaContabil().intValue())))
                .andExpect(jsonPath("$.[1].data", is(lancamentoContabil2.getData().format(DateTimeFormatter.ofPattern("yyyyMMdd")))))
                .andExpect(jsonPath("$.[1].valor", is(lancamentoContabil2.getValor().doubleValue())));
    }
}