package com.thiagoandrad.contabil.view;

import com.thiagoandrad.contabil.domain.LancamentoContabil;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoForm {

    @NotNull
    private Long contaContabil;
    @NotNull
    private LocalDate data;
    @NotNull
    private BigDecimal valor;

    public void setContaContabil(Long contaContabil) {
        this.contaContabil = contaContabil;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LancamentoContabil toLancamentoContabil() {
        return new LancamentoContabil(contaContabil, data, valor);
    }
}
