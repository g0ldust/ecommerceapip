package com.pcfactory.ecommerce.controller;


import com.pcfactory.ecommerce.model.Transaccion;
import com.pcfactory.ecommerce.service.WebpayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/pagos")

public class PagoController {
    private final WebpayService webpayService;

    public PagoController(WebpayService webpayService){
        this.webpayService = webpayService;
    }

    @GetMapping("/crear")
    public Mono<ResponseEntity<Map<String, Object>>> iniciarPago(
            @RequestParam Long idVenta,
            @RequestParam Integer monto){
        return webpayService.crearTransaccion(idVenta, monto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    @PostMapping("/confirmar")
    public Mono<ResponseEntity<Transaccion>> confirmarPago(@RequestParam("token_ws") String tokenWs){
        return webpayService.confirmarTransaccion(tokenWs)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
