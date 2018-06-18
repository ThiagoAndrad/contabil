package com.thiagoandrad.contabil.repository;

import com.thiagoandrad.contabil.domain.LancamentoContabil;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LancamentoContabilRepository extends Repository<LancamentoContabil, String> {

    void save(LancamentoContabil lancamentoContabil);

    Optional<LancamentoContabil> findById(String id);

    List<LancamentoContabil> findByContaContabil(Long contaContabil);
}
