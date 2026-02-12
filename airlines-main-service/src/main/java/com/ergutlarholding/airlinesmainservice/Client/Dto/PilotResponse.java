package com.ergutlarholding.airlinesmainservice.Client.Dto;

public record PilotResponse(
        Long id,
        String name,
        String surname,
        Double salary
) { }