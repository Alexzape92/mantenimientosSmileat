package es.smileat.mantenimientos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class SqlEmpleadoRepository implements IEmpleadoRepository {
    private Connection conn;
    private Statement stmt;

    //Default Constructor
    public SqlEmpleadoRepository() throws Exception {
        try{
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Smileat", "root", "adminSmileatJA");
        }catch(Exception e){
            throw new RuntimeException("No se ha podido conectar con la base de datos. Error: " + e.getMessage());
        }
    }

    public void save(Empleado empleado) throws Exception {
        Collection<Empleado> empleados = loadAll();

        for(Empleado e : empleados){
            if(e.getNombre().equals(empleado.getNombre())){
                throw new IllegalArgumentException("Ya existe un empleado con ese nombre");
            }
        }

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Empleados (NOMBRE, PASSWORD) VALUES ('" + empleado.getNombre() + "', '" + empleado.getPassword() + "')");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido guardar el empleado. Error: " + e.getMessage());
        } finally{
            if(stmt != null){
                try{
                    stmt.close();
                } catch(Exception e){}
            }
        }
    }

    public Collection<Empleado> loadAll() throws Exception {
        Collection<Empleado> empleados = new ArrayList<Empleado>();
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Empleados");

            while(rs.next()){
                Empleado empleado = new Empleado(rs.getString("NOMBRE"), rs.getString("PASSWORD"));
                empleados.add(empleado);
            }
        } catch(Exception e){
            throw new RuntimeException("No se han podido cargar los empleados. Error: " + e.getMessage());
        }

        return empleados;
    }

    public Empleado load(String nombre) throws Exception {
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Empleados WHERE NOMBRE = '" + nombre + "'");

            if(rs.next()){
                String name = rs.getString("NOMBRE");
                String password = rs.getString("PASSWORD");

                Empleado empleado = new Empleado();
                empleado.setNombre(name);
                empleado.setPassword(password);

                return empleado;
            }
        } catch(Exception e){
            throw new RuntimeException("No se ha podido cargar el empleado. Error: " + e.getMessage());
        }

        throw new IllegalArgumentException("No existe un empleado con ese nombre");
    }

    public void update(Empleado empleado) {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE Empleados SET PASSWORD = '" + empleado.getPassword() + "' WHERE NOMBRE = '" + empleado.getNombre() + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido actualizar el empleado. Error: " + e.getMessage());
        }
    }

    public void delete(String nombre) {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Empleados WHERE NOMBRE = '" + nombre + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido borrar el empleado. Error: " + e.getMessage());
        }
    }
}
