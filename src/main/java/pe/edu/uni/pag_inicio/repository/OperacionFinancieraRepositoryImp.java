package pe.edu.uni.pag_inicio.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.uni.pag_inicio.controller.dto.OperacionesFinancierasDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OperacionFinancieraRepositoryImp implements OperacionFinancieraRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Connection conexion;
    private void obtenerConexion() {
        try{
            conexion = jdbcTemplate.getDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void cerrarConexion(){
        try {
            conexion.close();
            conexion = null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int agregarDonacion(OperacionesFinancierasDTO dto) {
        int filasAfectadas;
        try{
            obtenerConexion();
            String sql = "INSERT INTO OperacaionesFineancieras VALUES(?, ?, ?, ?, ?, ?);";
            PreparedStatement st = conexion.prepareStatement(sql);
            st.setInt(1, dto.getIdOperacion());
            st.setInt(2, dto.getIdProyecto());
            st.setInt(3, dto.getIdUsuario());
            st.setFloat(4, dto.getMonto());
            st.setDate(5, dto.getFechaOperacion());
            st.setInt(6, dto.getValoracion());
            filasAfectadas = st.executeUpdate();
            st.close();
            cerrarConexion();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return filasAfectadas;
    }
}
