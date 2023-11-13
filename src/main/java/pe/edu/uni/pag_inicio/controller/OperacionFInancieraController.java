package pe.edu.uni.pag_inicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.uni.pag_inicio.controller.dto.OperacionesFinancierasDTO;
import pe.edu.uni.pag_inicio.service.OperacionFinancieraService;

@RestController
@RequestMapping("/operacion-financiera")
public class OperacionFInancieraController {

    @Autowired
    private final OperacionFinancieraService service = null;


    @PostMapping("/donar")
    public ResponseEntity<String> donar(
            @RequestBody OperacionesFinancierasDTO dto
            ) {

        service.donar(dto);
        return ResponseEntity.ok("");
    }
}
