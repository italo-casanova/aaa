package pe.edu.uni.pag_inicio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDTO {
    private int idproyecto;
    private String titulo;
    private String descripcion;
    private String objetivos;
    private float recaudacion;
    private Date fecha_inicio;
    private Date fecha_fin;
    private boolean estado;
    private float monto_objetivo;
    private String image_url;
    private String categoria;
    private int idcreador;
    private int idadministrador;

}
