package com.thiagoandrad.contabil.repository;

import com.thiagoandrad.contabil.domain.LancamentoContabil;
import org.springframework.data.repository.Repository;

public interface LancamentoContabilRepository extends Repository<LancamentoContabil, String> {

    void save(LancamentoContabil lancamentoContabil);

}
