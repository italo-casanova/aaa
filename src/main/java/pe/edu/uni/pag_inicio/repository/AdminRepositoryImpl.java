package pe.edu.uni.pag_inicio.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.pag_inicio.controller.dto.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepositoryImpl implements AdminRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<ProyectoDTO> findAllProyectos() {
        String sql = "SELECT * FROM Proyectos";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProyectoDTO(
                        rs.getInt("id_proyecto"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("objetivos"),
                        rs.getFloat("recaudacion"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getBoolean("estado"),
                        rs.getFloat("monto_objetivo"),
                        rs.getString("image_url"),
                        rs.getString("categoria"),
                        rs.getInt("id_creador"),
                        rs.getInt("id_administrador")
                )
        );
    }

    @Override
    public List<UsuarioDTO> findAllUsuarios() {
        String sql = "SELECT * FROM Usuarios";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new UsuarioDTO(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("contrasena"),
                        rs.getString("rol")
                )
        );
    }


    @Override
    public List<CreadorDTO> findAllCreadores() {
        String sql = "SELECT * FROM Creador";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CreadorDTO(
                        rs.getInt("id_creador"),
                        rs.getInt("id_usuario"),
                        rs.getBoolean("estadoCreador"),
                        rs.getBoolean("es_administrador"),
                        rs.getDate("fecha_creacion")
                )
        );
    }

    @Override
    public List<MetodoPagoDTO> findAllMetodosPago() {
        String sql = "SELECT * FROM MetodoPago";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new MetodoPagoDTO(
                        rs.getInt("id_metodopago"),
                        rs.getInt("id_usuario"),
                        rs.getString("tipo_tarjeta"),
                        rs.getString("nombre_titular"),
                        rs.getFloat("pago"),
                        rs.getDate("fecha_expiracion"),
                        rs.getString("cvv")
                )
        );
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ProyectoDTO Crearproyecto(ProyectoDTO dto) {
        // Verificar si el creador existe
        String sqlCreador = "SELECT COUNT(1) AS filas FROM Creador WHERE id_creador = ?";
        int filasCreador = jdbcTemplate.queryForObject(sqlCreador, Integer.class, dto.getIdcreador());
        if (filasCreador == 0) {
            throw new RuntimeException("Código de creador incorrecto.");
        }

        // Obtener el ID del administrador
        Integer idAdmin = obtenerIDAdmin();

        // Registrar proyecto
        final String sqlProyecto = "INSERT INTO Proyectos (titulo, descripcion, objetivos, recaudacion, fecha_inicio, fecha_fin, estado, monto_objetivo, id_creador, image_url, categoria, id_administrador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sqlProyecto, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, dto.getTitulo());
                    ps.setString(2, dto.getDescripcion());
                    ps.setString(3, dto.getObjetivos());
                    ps.setFloat(4, dto.getRecaudacion());
                    ps.setDate(5, java.sql.Date.valueOf(dto.getFecha_inicio().toLocalDate()));
                    ps.setDate(6, java.sql.Date.valueOf(dto.getFecha_fin().toLocalDate()));
                    ps.setBoolean(7, dto.isEstado());
                    ps.setFloat(8, dto.getMonto_objetivo());
                    ps.setInt(9, dto.getIdcreador());
                    ps.setString(10, dto.getImage_url());
                    ps.setString(11, dto.getCategoria());
                    ps.setInt(12, idAdmin);
                    return ps;
                },
                keyHolder
        );

        // Obtener el ID generado y establecerlo en el objeto dto
        if (keyHolder.getKey() != null) {
            dto.setIdproyecto(keyHolder.getKey().intValue());
        }

        return dto;
    }

    private Integer obtenerIDAdmin() {
        // Lógica para obtener el id del administrador, por ejemplo, puede ser el administrador predeterminado o el último administrador creado, según tus requerimientos.
        String sql = "SELECT id_creador FROM Creador WHERE es_administrador = 1";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ProyectoDTO actualizarProyecto(int idProyecto, ProyectoDTO proyectoDTO) {
        // Obtén la información actual del proyecto
        ProyectoDTO proyectoActual = obtenerProyectoPorId(idProyecto);

        // Verifica qué campos se deben actualizar y construye la consulta SQL dinámicamente
        StringBuilder sqlBuilder = new StringBuilder("UPDATE Proyectos SET ");
        List<Object> parametros = new ArrayList<>();

        if (proyectoDTO.getDescripcion() != null) {
            sqlBuilder.append("descripcion = ?, ");
            parametros.add(proyectoDTO.getDescripcion());
        }

        if (proyectoDTO.getObjetivos() != null) {
            sqlBuilder.append("objetivos = ?, ");
            parametros.add(proyectoDTO.getObjetivos());
        }

        if (proyectoDTO.getFecha_fin() != null) {
            sqlBuilder.append("fecha_fin = ?, ");
            parametros.add(proyectoDTO.getFecha_fin());
        }

        if (proyectoDTO.getImage_url() != null) {
            sqlBuilder.append("image_url = ?, ");
            parametros.add(proyectoDTO.getImage_url());
        }

        // Elimina la última coma de la consulta SQL
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 2);

        sqlBuilder.append("WHERE id_proyecto = ?");
        parametros.add(idProyecto);

        // Convierte la lista de parámetros a un array
        Object[] parametrosArray = parametros.toArray();

        // Ejecuta la actualización en la base de datos y obtén el número de filas afectadas
        int filasAfectadas = jdbcTemplate.update(sqlBuilder.toString(), parametrosArray);

        // Si al menos una fila fue actualizada, devuelve el proyecto actualizado; de lo contrario, devuelve null
        return filasAfectadas > 0 ? proyectoActual : null;
    }

    private ProyectoDTO obtenerProyectoPorId(int idProyecto) {
        String sql = "SELECT * FROM Proyectos WHERE id_proyecto = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{idProyecto}, (rs, rowNum) ->
                    new ProyectoDTO(
                            rs.getInt("id_proyecto"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getString("objetivos"),
                            rs.getFloat("recaudacion"),
                            rs.getDate("fecha_inicio"),
                            rs.getDate("fecha_fin"),
                            rs.getBoolean("estado"),
                            rs.getFloat("monto_objetivo"),
                            rs.getString("image_url"),
                            rs.getString("categoria"),
                            rs.getInt("id_creador"),
                            rs.getInt("id_administrador")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró ningún proyecto con la id");
            return null;
        }
    }

    @Override
    public String borrarProyecto(int id) {
        String sql = "DELETE FROM Proyectos WHERE id_proyecto = ?";
        int filasBorradas = jdbcTemplate.update(sql, id);

        if (filasBorradas > 0) {
            return "Proyecto borrado exitosamente.";
        } else {
            return "No se encontró un proyecto con ID " + id;
        }
    }

    @Override
    public List<OperacionesFinancierasDTO> findAllOperacionesFinancieras() {
        String sql = "SELECT * FROM OperacionesFinancieras";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new OperacionesFinancierasDTO(
                        rs.getInt("id_operacion"),
                        rs.getInt("id_proyecto"),
                        rs.getInt("id_usuario"),
                        rs.getFloat("monto"),
                        rs.getDate("fecha_operacion"),
                        rs.getInt("valoracion")
                )
        );
    }

    @Override
    public List<RecompensaDTO> findAllRecompensas() {
        String sql = "SELECT * FROM Recompensas";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new RecompensaDTO(
                        rs.getInt("id_recompensa"),
                        rs.getInt("id_proyecto"),
                        rs.getString("tipo_nivel"),
                        rs.getString("nombre_recompensa"),
                        rs.getFloat("valor_recompensa")
                )
        );
    }

    @Override
    @Transactional
    public MensajeAdminDTO responderContacto(int idContacto, MensajeAdminDTO mensajeAdminDTO) {
        // Consulta SQL para actualizar la respuesta y el asunto de respuesta en la tabla Contacto
        String sql = "UPDATE Contacto SET estado_aprobacion = ?, respuesta = ?, asunto_respuesta = ? WHERE id_contacto = ?";

        // Ejecutar la actualización
        jdbcTemplate.update(
                sql,
                mensajeAdminDTO.isEstadoAprobacion(),
                mensajeAdminDTO.getRespuesta(),
                mensajeAdminDTO.getAsuntoRespuesta(),
                idContacto
        );
        return mensajeAdminDTO;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public RecompensaDTO crearRecompensa(RecompensaDTO recompensaDto, int idProyecto) {
        try {
            // Verificar si el proyecto existe
            String sqlProyecto = "SELECT COUNT(1) FROM Proyectos WHERE id_proyecto = ?";
            int filasProyecto = jdbcTemplate.queryForObject(sqlProyecto, Integer.class, idProyecto);
            if (filasProyecto == 0) {
                throw new RuntimeException("No se encontró un proyecto con ID " + idProyecto);
            }

            // Insertar la recompensa
            String sqlInsert = "INSERT INTO Recompensas (id_proyecto, tipo_nivel, nombre_recompensa, valor_recompensa) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sqlInsert, idProyecto, recompensaDto.getTipo_nivel(), recompensaDto.getNombre_recompensa(), recompensaDto.getValor_recompensa());

            return recompensaDto;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al crear la recompensa: " + e.getMessage());
        }
    }
    @Override
    public Mensajedto borrarRecompensa(int idRecompensa) {
        String sql = "DELETE FROM Recompensas WHERE id_recompensa = ?";
        int filasBorradas = jdbcTemplate.update(sql, idRecompensa);

        if (filasBorradas > 0) {
            return new Mensajedto(1, "Recompensa borrada exitosamente.");
        } else {
            return new Mensajedto(-1, "No se encontró una recompensa con ID " + idRecompensa);
        }
    }
}
