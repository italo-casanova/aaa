package pe.edu.uni.pag_inicio.repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.uni.pag_inicio.controller.dto.Mensajedto;
import pe.edu.uni.pag_inicio.controller.dto.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuariosRepositoryImpl implements UsuariosRepository{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UsuariosRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UsuarioDTO findByEmail(String email) {
        String query = "SELECT * FROM Usuarios WHERE email = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{email}, (resultSet, rowNum) -> {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setIdUsuario(resultSet.getInt("id_usuario"));
            usuario.setNombre(resultSet.getString("nombre"));
            usuario.setApellido(resultSet.getString("apellido"));
            usuario.setTelefono(resultSet.getString("telefono"));
            usuario.setEmail(resultSet.getString("email"));
            usuario.setContrasena(resultSet.getString("contrasena"));
            return usuario;
        });
    }
    @Override
    public UsuarioDTO findById(long userId) {
        String query = "SELECT * FROM Usuarios WHERE id_usuario = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, (resultSet, rowNum) -> {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setIdUsuario(resultSet.getInt("id_usuario"));
            usuario.setNombre(resultSet.getString("nombre"));
            usuario.setApellido(resultSet.getString("apellido"));
            usuario.setTelefono(resultSet.getString("telefono"));
            usuario.setEmail(resultSet.getString("email"));
            usuario.setContrasena(resultSet.getString("contrasena"));
            return usuario;
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public UsuarioDTO save(UsuarioDTO usuario) {
        if (usuario.getIdUsuario() == 0) {
            String insertQuery = "INSERT INTO Usuarios (nombre, apellido, telefono, email, contrasena, rol) VALUES (?, ?, ?, ?, ?,'creador')";
            jdbcTemplate.update(insertQuery, usuario.getNombre(), usuario.getApellido(), usuario.getTelefono(), usuario.getEmail(), usuario.getContrasena());
        }
        return usuario;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public UsuarioDTO update(int idUsuario, UsuarioDTO usuarioDTO) {
        // Obtén la información actual del usuario
        UsuarioDTO usuarioActual = findById(idUsuario);

        // Verifica qué campos se deben actualizar y construye la consulta SQL dinámicamente
        StringBuilder updateQueryBuilder = new StringBuilder("UPDATE Usuarios SET ");
        List<Object> parametros = new ArrayList<>();

        if (usuarioDTO.getNombre() != null) {
            updateQueryBuilder.append("nombre = ?, ");
            parametros.add(usuarioDTO.getNombre());
        }

        if (usuarioDTO.getApellido() != null) {
            updateQueryBuilder.append("apellido = ?, ");
            parametros.add(usuarioDTO.getApellido());
        }

        if (usuarioDTO.getTelefono() != null) {
            updateQueryBuilder.append("telefono = ?, ");
            parametros.add(usuarioDTO.getTelefono());
        }

        if (usuarioDTO.getEmail() != null) {
            updateQueryBuilder.append("email = ?, ");
            parametros.add(usuarioDTO.getEmail());
        }

        if (usuarioDTO.getContrasena() != null) {
            updateQueryBuilder.append("contrasena = ?, ");
            parametros.add(usuarioDTO.getContrasena());
        }

        // Elimina la última coma de la consulta SQL
        updateQueryBuilder.deleteCharAt(updateQueryBuilder.length() - 2);

        updateQueryBuilder.append("WHERE id_usuario = ?");
        parametros.add(idUsuario);

        // Convierte la lista de parámetros a un array
        Object[] parametrosArray = parametros.toArray();

        // Ejecuta la actualización en la base de datos y obtén el número de filas afectadas
        int filasAfectadas = jdbcTemplate.update(updateQueryBuilder.toString(), parametrosArray);

        // Si al menos una fila fue actualizada, devuelve el usuario actualizado; de lo contrario, devuelve null
        return filasAfectadas > 0 ? usuarioActual : null;
    }

    @Override
    public Mensajedto deleteById(int userId) {
        String deleteQuery = "DELETE FROM Usuarios WHERE id_usuario = ?";
        int filasBorradas= jdbcTemplate.update(deleteQuery, userId);
        if (filasBorradas > 0) {
            return new Mensajedto(1, "Usuario borrado exitosamente.");
        } else {
            return new Mensajedto(-1, "No se encontró una usuario con ID: " + userId);
        }
    }
    @Override
    public List<UsuarioDTO> findAll() {
        String query = "SELECT * FROM Usuarios";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setIdUsuario(resultSet.getInt("id_usuario"));
            usuario.setNombre(resultSet.getString("nombre"));
            usuario.setApellido(resultSet.getString("apellido"));
            usuario.setTelefono(resultSet.getString("telefono"));
            usuario.setEmail(resultSet.getString("email"));
            usuario.setContrasena(resultSet.getString("contrasena"));
            return usuario;
        });
    }
}

