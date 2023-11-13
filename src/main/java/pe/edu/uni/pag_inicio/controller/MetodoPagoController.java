package pe.edu.uni.pag_inicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.pag_inicio.controller.dto.MetodoPagoDto;
import pe.edu.uni.pag_inicio.service.MetodoPagoService;

@RestController
@RequestMapping("/metodo-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService service;

    @PostMapping("/agregar/")
    public ResponseEntity<String> agregarMetodo(
            @RequestBody MetodoPagoDto dto
            ) {
        service.agregarMetodoPago(dto);
        return ResponseEntity.ok("");
    }


}
