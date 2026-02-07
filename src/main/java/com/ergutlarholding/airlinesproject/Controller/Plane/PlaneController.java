package com.ergutlarholding.airlinesproject.Controller.Plane;

import com.ergutlarholding.airlinesproject.Dto.Plane.PlaneRequest;
import com.ergutlarholding.airlinesproject.Dto.Plane.PlaneResponse;
import com.ergutlarholding.airlinesproject.Servis.PlaneService;
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
        return ResponseEntity.ok(planeService.DeleteAllPlanes()) ;
    }
}