package pe.edu.uni.pag_inicio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreadorDTO {
    private int idCreador;
    private int idUsuario;
    private boolean estadoCreador;
    private boolean esAdministrador;
    private Date fechaCreacion;
}
