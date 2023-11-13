package pe.edu.uni.pag_inicio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoPagoDTO {
    private int idMetodoPago;
    private int idUsuario;
    private String tipoTarjeta;
    private String nombreTitular;
    private float pago;
    private Date fechaExpiracion;
    private String cvv;
}
