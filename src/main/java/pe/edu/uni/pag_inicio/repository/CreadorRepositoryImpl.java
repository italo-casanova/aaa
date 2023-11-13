package pe.edu.uni.pag_inicio.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.uni.pag_inicio.controller.dto.*;

import java.util.List;

@Repository
public class CreadorRepositoryImpl implements CreadorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreadorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void solicitarguardarProyecto(SolicitudCreacionDTO solicitudCreacionDTO) {
        // Obtener el id del administrador asociado al proyecto
        Integer idAdmin = obtenerIDAdmin();
        Integer idcreador = obtenerIDCreador(solicitudCreacionDTO.getEmail());
        // Insertar en la tabla Contacto
        String contactoSql = "INSERT INTO Contacto (asunto, mensaje, id_administrador, id_creador, estado_aprobacion,fecha_envio) VALUES (?, ?, ?, ?, 0, GETDATE())";
        jdbcTemplate.update(contactoSql,
                solicitudCreacionDTO.getAsunto(),
                solicitudCreacionDTO.getMensaje(),
                idAdmin,
                idcreador);
    }

    private Integer obtenerIDAdmin() {
        // Lógica para obtener el id del administrador, por ejemplo, puede ser el administrador predeterminado o el último administrador creado, según tus requerimientos.
        String sql = "SELECT id_creador FROM Creador WHERE es_administrador = 1";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    private Integer obtenerIDCreador(String email) {
        // Lógica para obtener el último id_creador en base al email
        String sql = "SELECT id_creador FROM Creador JOIN Usuarios ON Creador.id_usuario = Usuarios.id_usuario WHERE Usuarios.email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }
    @Override
    @Transactional
    public void solicitarmodificarProyecto(SolicitudModificacionDTO solicitudModificacionDTO) {
        // Obtener el id del creador del proyecto
        Integer idAdmin = obtenerIDAdmin();
        Integer idCreador = obtenerCreadorDeProyecto(solicitudModificacionDTO.getIdproyectoDTO().getIdProyecto());

        // Verificar que se obtuvo el id del creador
        if (idCreador != null) {
            String contactoSql = "INSERT INTO Contacto (asunto, mensaje,id_administrador, id_proyecto, id_creador, estado_aprobacion, fecha_envio) VALUES (?, ?, ?, ?,?, 0, GETDATE())";
            jdbcTemplate.update(contactoSql,
                    solicitudModificacionDTO.getContactoDTO().getAsunto(),
                    solicitudModificacionDTO.getContactoDTO().getMensaje(),
                    solicitudModificacionDTO.getIdproyectoDTO().getIdProyecto(),
                    idAdmin,
                    idCreador);
        } else {
            // Manejar la situación en la que no se puede obtener el id del creador (puedes lanzar una excepción, loggear, etc.)
            // Aquí, simplemente imprimo un mensaje de error
            System.err.println("No se pudo obtener el id del creador del proyecto.");
        }
    }

    private Integer obtenerCreadorDeProyecto(int idProyecto) {
        String sql = "SELECT id_creador FROM Proyectos WHERE id_proyecto = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idProyecto}, Integer.class);
    }

    @Override
    public List<IdproyectoDTO> obtenerProyectosActivosPorCreador(Long idCreador) {
        String sql = "SELECT id_proyecto, titulo FROM Proyectos WHERE id_creador = ? AND estado = 1";

        return jdbcTemplate.query(sql, new Object[]{idCreador}, (rs, rowNum) -> {
            IdproyectoDTO proyectoDTO = new IdproyectoDTO();
            proyectoDTO.setIdProyecto(rs.getInt("id_proyecto"));
            proyectoDTO.setTitulo(rs.getString("titulo"));
            return proyectoDTO;
        });
    }
}


