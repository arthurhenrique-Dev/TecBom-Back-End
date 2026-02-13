package com.tecbom.e_commerce.Infra.Adapters.Output.Gateways.CepGateway;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOReturnCepService;
import com.tecbom.e_commerce.Application.Ports.Output.CepService;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProcessingErrorException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cep;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CepGateway implements CepService {


    @Override
    public DTOReturnCepService getAddressByCep(Cep cep) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://viacep.com.br/ws/" + cep.cep() + "/json/"))
                    .build();

            HttpResponse<String> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(response.body());

            return new DTOReturnCepService(
                    responseJson.path("logradouro").asText(),
                    responseJson.path("bairro").asText(),
                    responseJson.path("localidade").asText(),
                    responseJson.path("uf").asText()
            );
        } catch (Exception e) {
            throw new ProcessingErrorException();
        }
    }
}
