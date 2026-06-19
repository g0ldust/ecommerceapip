package com.pcfactory.ecommerce.service;

import com.pcfactory.ecommerce.model.EstadoTransaccion;
import com.pcfactory.ecommerce.model.Transaccion;
import org.springframework.stereotype.Service;
import com.pcfactory.ecommerce.repository.TransaccionRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WebpayService {
    private final TransaccionRepository transaccionRepository;
    private final WebClient webClient;

    private final String COMMERCE_CODE = "597055555532";
    private final String API_KEY = "579B532A7440BB0C9079DED94D31EA1615B11956075CDDB4B838B6122EF26C0D";
    private final String BASE_URL = "https://webpay3gint.transbank.cl";

    public WebpayService(TransaccionRepository transaccionRepository, WebClient webClient) {
        this.transaccionRepository = transaccionRepository;
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Tbk-Api-Key-Id", COMMERCE_CODE)
                .defaultHeader("Tbk-Api-Key-Secret", API_KEY)
                .build();


    }

public Mono<Map<String, Object>> crearTransaccion(Long idVenta, Integer monto ) {
        String buyOrder = "OC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String sessionId = "SESS-" + idVenta;

        String returnUrl = "http://localhost:8080/api/pagos/vistas/retorno" ;
        Map<String, Object> body = new HashMap<>();
        body.put("buy_order", buyOrder);
        body.put("session_id", sessionId);
        body.put("amount", monto);
        body.put("return_url", returnUrl);

        return webClient.post()
                .uri("/rsenv/card_codes/v1.2/transactions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response ->{
                    String token = (String) response.get("token");
                    String url = (String) response.get("url");
                    Transaccion tx = new Transaccion();
                    tx.setBuyOrder(buyOrder);
                    tx.setIdVenta(idVenta);
                    tx.setMonto(monto);
                    tx.setTokenWebpay(token);
                    tx.setEstado(EstadoTransaccion.CREADO);
                    tx.setFechaCreacion(LocalDateTime.now());
                    transaccionRepository.save(tx);

                    Map<String, Object> result= new HashMap<>();
                    result.put("token", token);
                    result.put("url", url);
                    return result;

                });
}
public Mono<Transaccion> confirmarTransaccion(String token){
        return webClient.put()
                .uri("/rsenv/card_codes/v1.2/transactions/" + token)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Transaccion tx = transaccionRepository.findByTokenWebpay(token)
                            .orElseThrow(() -> new RuntimeException("no existe registro con ese token"));
                    String status = (String) response.get("status");

                    if ("AUTHORIZED".equals(status)){
                        tx.setEstado(EstadoTransaccion.PAGADO);
                    }else {
                        tx.setEstado(EstadoTransaccion.NOPAGADO);
                    }

                    tx.setFechaConfirmacion(LocalDateTime.now());
                    return transaccionRepository.save(tx);
                });
}



}
