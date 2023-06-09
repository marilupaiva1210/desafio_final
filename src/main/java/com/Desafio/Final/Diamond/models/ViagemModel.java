package com.Desafio.Final.Diamond.models;

import com.Desafio.Final.Diamond.models.enu.ViagemEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "tb_viagem")
public class ViagemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer codigo;
    @NotNull
    @Column(name = "latitude_partida")
    private Double latitudePartida;

    @NotNull
    @Column(name = "longitude_partida")
    private Double longitudePartida;

    @NotNull
    @Column(name = "latitude_chegada")
    private Double latitudeChegada;

    @NotNull
    @Column(name = "longitude_chegada")
    private Double longitudeChegada;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_viagem")
    private ViagemEnum statusViagem;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorista_id")
    private MotoristaModel motorista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passageiro_id")
    private PassageiroModel passageiro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pagamento_id")
    private PagamentoModel pagamento;
}