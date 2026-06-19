package com.pcfactory.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Transaccion {
    @Id
    @Column(name = "buy_order", length = 50)
    private String buyOrder;

    @Column(name = "id_venta", nullable = false)
    private Long idVenta;

    @Column( nullable = false )
    private Integer monto;

    @Column(name = "token_webpay", length = 100)
    private String tokenWebpay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTransaccion estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;


}
