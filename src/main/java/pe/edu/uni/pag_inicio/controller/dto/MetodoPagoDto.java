package pe.edu.uni.pag_inicio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class MetodoPagoDto {
    private int id_metodo_pago, id_usuario;
    private float pago;
    private String tipo_tarjeta, nombre_titular, cvv;
    private Date fecha_expiracion;

}
