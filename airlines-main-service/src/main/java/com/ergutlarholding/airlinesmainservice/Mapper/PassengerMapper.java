package com.ergutlarholding.airlinesmainservice.Mapper;

import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Passenger.PassengerResponse;
import com.ergutlarholding.airlinesmainservice.Entity.Passenger;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper extends BaseMapper<Passenger, PassengerRequest, PassengerResponse> {
    // İçerisi tamamen boş! Tüm metodlar BaseMapper'dan miras alındı.
}