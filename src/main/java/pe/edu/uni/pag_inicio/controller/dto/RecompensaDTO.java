package pe.edu.uni.pag_inicio.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecompensaDTO {
    private int id_recompensa;
    private int id_proyecto;
    private String tipo_nivel;
    private String nombre_recompensa;
    private float valor_recompensa;
}
