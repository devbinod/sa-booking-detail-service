package edu.miu590.bookingservice.client;



import edu.miu590.bookingservice.model.VehicleDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${vehicle.service.feign.name}", url = "${vehicle.service.feign.url}")
public interface VehicleServiceClient {


    @GetMapping("/vehicles/{id}")
    public VehicleDto findById(@PathVariable String id);
}
