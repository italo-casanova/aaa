package pe.edu.uni.pag_inicio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperacionesFinancierasDTO {
    private int idOperacion;
    private int idProyecto;
    private int idUsuario;
    private float monto;
    private Date fechaOperacion;
    private int valoracion;
}
