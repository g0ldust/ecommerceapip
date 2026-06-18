package com.pcfactory.ecommerce.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    


}
