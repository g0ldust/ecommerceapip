package com.pcfactory.ecommerce.service;

import com.pcfactory.ecommerce.model.EstadoTansaccion;
import com.pcfactory.ecommerce.model.Transaccion;
import org.springframework.stereotype.Service;
import com.pcfactory.ecommerce.repository.TansaccionRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

@Service
public class WebpayService {
    private final TansaccionRepository tansaccionRepository;
    private final WebClient webClient;

    private final String COMMERCE_CODE = "597055555532";
    private final String API_KEY = "579B532A7440BB0C9079DED94D31EA1615B11956075CDDB4B838B6122EF26C0D";
    private final String BASE_URL = "https://webpay3gint.transbank.cl";

    public WebpayService(TansaccionRepository tansaccionRepository, WebClient webClient) {
        this.tansaccionRepository = tansaccionRepository;
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Tbk-Api-Key-Id", COMMERCE_CODE)
                .defaultHeader("Tbk-Api-Key-Secret", API_KEY)
                .build();


    }


    
}
