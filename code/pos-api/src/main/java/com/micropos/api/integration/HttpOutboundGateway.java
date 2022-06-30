package com.micropos.api.integration;

import com.micropos.api.dto.DeliveryDto;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Component;

@Component
public class HttpOutboundGateway {
    @Bean
    public IntegrationFlow outGate() {
        return IntegrationFlows.from("sampleChannel")
                .handle(Http.outboundGateway("http://localhost:8080/delivery")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(DeliveryDto.class))
                .get();
    }
}
