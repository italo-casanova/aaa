package pe.edu.uni.pag_inicio.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import pe.edu.uni.pag_inicio.controller.dto.MetodoPagoDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MetodoPagoRepositoryImp implements MetodoPagoRepository{
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
    public int save(MetodoPagoDto metodoPago) {
        int filasAfectadas;
        try{
            obtenerConexion();
            String sql = "INSERT INTO MetodoPago VALUES(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement st = conexion.prepareStatement(sql);
            st.setInt(1, metodoPago.getId_metodo_pago());
            st.setInt(2, metodoPago.getId_usuario());
            st.setString(3, metodoPago.getTipo_tarjeta());
            st.setString(4, metodoPago.getNombre_titular());
            st.setFloat(5, metodoPago.getPago());
            st.setDate(6, metodoPago.getFecha_expiracion());
            st.setString(7, metodoPago.getCvv());
            filasAfectadas = st.executeUpdate();
            st.close();
            cerrarConexion();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return filasAfectadas;
    }
}
