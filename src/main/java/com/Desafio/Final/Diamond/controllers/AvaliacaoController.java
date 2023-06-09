package com.Desafio.Final.Diamond.controllers;

import com.Desafio.Final.Diamond.models.AvaliacaoModel;
import com.Desafio.Final.Diamond.models.MotoristaModel;
import com.Desafio.Final.Diamond.services.AvaliacaoService;
import com.Desafio.Final.Diamond.services.MotoristaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;
    @Autowired
    private MotoristaService motoristaService;

    @PostMapping(value = "/cadastrar")
    @Operation(summary = "Cadastra a avaliação", description = "Método da api para cadastro de avaliação na plataforma")
    @ApiResponse(responseCode = "200", description = "Operação concluida com sucesso!")
    @ApiResponse(responseCode = "404", description = "Erro na operação!")
    @ApiResponse(responseCode = "500", description = "Erro inesperado!")

    public ResponseEntity novaAvaliacao(@RequestBody AvaliacaoModel avaliacao,
                                        @RequestParam Integer codigoMotorista) {

        MotoristaModel motorista = motoristaService.buscarCodigo(codigoMotorista);
        avaliacao.setMotorista(motorista);

        try {
            avaliacaoService.adicionar(avaliacao);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Não foi possível cadastrar avaliação.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Avaliação cadastrada!", HttpStatus.CREATED);
    }

    @GetMapping(value = "/listar")
    @Operation(summary = "Listar avaliações", description = "Método da api para listagem de todas as avaliações")
    @ApiResponse(responseCode = "200", description = "Operação concluida com sucesso!")
    @ApiResponse(responseCode = "404", description = "Erro na operação!")
    @ApiResponse(responseCode = "500", description = "Erro inesperado!")

    public ResponseEntity ListarPorAvaliacao(){

        return new ResponseEntity(avaliacaoService.listar(), HttpStatus.OK);

    }
    @PutMapping(value = "/alterar")
    @Operation(summary = "Altera o avaliação", description = "Método da api para alterar os dados de uma avaliação")
    @ApiResponse(responseCode = "200", description = "Operação concluida com sucesso!")
    @ApiResponse(responseCode = "404", description = "Erro na operação!")
    @ApiResponse(responseCode = "500", description = "Erro inesperado!")

    public ResponseEntity alterar (@PathVariable Integer codigo,
                                   @RequestBody AvaliacaoModel avaliacao){

        try {
            avaliacaoService.update(codigo, avaliacao);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Não foi possível alterar. ", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Avaliação alterada com sucesso!", HttpStatus.OK);
    }


    @DeleteMapping(value = "/deletar/{codigo}")
    public ResponseEntity deletar (@PathVariable Integer codigo) {

        try {
            avaliacaoService.remover(codigo);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Código inválido!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Avaliação do código " + codigo + " foi removida! ", HttpStatus.OK);
    }
}
