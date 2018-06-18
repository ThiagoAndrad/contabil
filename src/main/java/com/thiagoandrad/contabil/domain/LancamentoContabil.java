package com.thiagoandrad.contabil.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
public class LancamentoContabil {

    @Id
    private String id;
    private Long contaContabil;
    private LocalDate data;
    private BigDecimal valor;

    public LancamentoContabil(Long contaContabil, LocalDate data, BigDecimal valor) {
        this.contaContabil = contaContabil;
        this.data = data;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public Long getContaContabil() {
        return contaContabil;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @deprecated hibernate eyes
     */
    @Deprecated
    private LancamentoContabil() {

    }
}
