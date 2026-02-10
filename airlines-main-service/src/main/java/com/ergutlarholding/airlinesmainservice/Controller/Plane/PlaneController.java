package com.ergutlarholding.airlinesmainservice.Controller.Plane;

import com.ergutlarholding.airlinesmainservice.Dto.Plane.PlaneRequest;
import com.ergutlarholding.airlinesmainservice.Dto.Plane.PlaneResponse;
import com.ergutlarholding.airlinesmainservice.Services.PlaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlaneController {

    private final PlaneService planeService;

    @PostMapping("/save")
    public ResponseEntity<PlaneResponse> save(@RequestBody PlaneRequest request) {
        return ResponseEntity.ok(planeService.savePlane(request));
    }

    @GetMapping
    public ResponseEntity<List<PlaneResponse>> getAll() {
        return ResponseEntity.ok(planeService.getAllPlanes());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {  // Repsınse entity ne döcneegi vermen önemli
        return ResponseEntity.ok(planeService.deleteAllPlanes()) ;
    }
}