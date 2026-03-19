package com.ergutlarholding.airlinesmainservice.Mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface BaseMapper<E, Req, Res> {

    // Entity -> Response DTO
    Res toResponse(E entity);

    // Request DTO -> Entity
    E toEntity(Req request);

    // Liste Dönüşümü
    List<Res> toResponseList(List<E> entities);

    // Güncelleme/Patch: Null değerleri ignore ederek güncelleme
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(Req request, @MappingTarget E entity);
}