package com.thiagoandrad.contabil.controller;

import com.thiagoandrad.contabil.domain.LancamentoContabil;
import com.thiagoandrad.contabil.repository.LancamentoContabilRepository;
import com.thiagoandrad.contabil.view.LancamentoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("lancamentos-contabeis")
public class LancamentoContabilController {

    @Autowired
    private LancamentoContabilRepository repository;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> salva(@RequestBody @Valid LancamentoForm lacamentoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors()
                    .stream()
                    .map(o -> o.getCode())
                    .toArray());
        }

        LancamentoContabil lancamentoContabil = lacamentoForm.toLancamentoContabil();
        repository.save(lancamentoContabil);

        return ResponseEntity.created(URI.create("/lancamentos-contabeis/" + lancamentoContabil.getId() )).build();
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLancamentoContabil(@PathVariable("id") String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
