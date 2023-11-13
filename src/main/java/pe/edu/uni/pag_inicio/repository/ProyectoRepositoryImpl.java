package pe.edu.uni.pag_inicio.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.uni.pag_inicio.controller.dto.ProyectoDTO;

import java.util.List;
import java.util.Optional;

@Repository
public class ProyectoRepositoryImpl implements ProyectoRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProyectoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<ProyectoDTO> obtenerProyectosPorCategoria(String categoria) {
        // Lógica para obtener proyectos por categoría desde la base de datos
        String sql = "SELECT * FROM Proyectos WHERE categoria = ?";
        return jdbcTemplate.query(sql, new Object[]{categoria}, (rs, rowNum) ->
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
    public List<ProyectoDTO> findAllProyecto() {
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
    public void deleteById(int idProyecto) {
        String query = "DELETE FROM Proyectos WHERE id_proyecto = ?";
        jdbcTemplate.update(query, idProyecto);
    }

    @Override
    public Optional<ProyectoDTO> findById(int id_proyecto){
        String query = "SELECT * FROM Proyectos WHERE id_proyecto = ?";
        return jdbcTemplate.query(query, new Object[]{id_proyecto}, resultSet -> {
            if (resultSet.next()) {
                ProyectoDTO proyecto = new ProyectoDTO();
                proyecto.setIdproyecto(resultSet.getInt("id_proyecto"));
                proyecto.setTitulo(resultSet.getString("titulo"));
                // Establecer otros campos de Proyectos según la estructura de tu tabla en la base de datos
                return Optional.of(proyecto);
            } else {
                return Optional.empty();
            }
        });
    }
}
