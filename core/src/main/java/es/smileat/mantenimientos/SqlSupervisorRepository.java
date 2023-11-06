package es.smileat.mantenimientos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class SqlSupervisorRepository implements ISupervisorRepository {
    private Connection conn;
    private Statement stmt;

    //Default Constructor
    public SqlSupervisorRepository() throws Exception {
        try{
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Smileat", "root", "adminSmileatJA");
        }catch(Exception e){
            throw new RuntimeException("No se ha podido conectar con la base de datos. Error: " + e.getMessage());
        }
    }

    public void save(Supervisor Supervisor) throws Exception {
        Collection<Supervisor> Supervisors = loadAll();

        for(Supervisor e : Supervisors){
            if(e.getNombre().equals(Supervisor.getNombre())){
                throw new IllegalArgumentException("Ya existe un supervisor con ese nombre");
            }
        }

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO supervisores (NOMBRE, PASSWORD) VALUES ('" + Supervisor.getNombre() + "', '" + Supervisor.getPassword() + "')");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido guardar el supervisor. Error: " + e.getMessage());
        } finally{
            if(stmt != null){
                try{
                    stmt.close();
                } catch(Exception e){}
            }
        }
    }

    public Collection<Supervisor> loadAll() throws Exception {
        Collection<Supervisor> Supervisors = new ArrayList<Supervisor>();
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM supervisores");

            while(rs.next()){
                Supervisor Supervisor = new Supervisor(rs.getString("NOMBRE"), rs.getString("PASSWORD"));
                Supervisors.add(Supervisor);
            }
        } catch(Exception e){
            throw new RuntimeException("No se han podido cargar los supervisores. Error: " + e.getMessage());
        }

        return Supervisors;
    }

    public Supervisor load(String nombre) throws Exception {
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM supervisores WHERE NOMBRE = '" + nombre + "'");

            if(rs.next()){
                String nombreSupervisor = rs.getString("NOMBRE");
                String password = rs.getString("PASSWORD");

                Supervisor supervisor = new Supervisor();
                supervisor.setNombre(nombreSupervisor);
                supervisor.setPassword(password);

                return supervisor;
            }
        } catch(Exception e){
            throw new RuntimeException("No se ha podido cargar el supervisor. Error: " + e.getMessage());
        }

        throw new IllegalArgumentException("No existe un supervisor con ese nombre");
    }

    public void update(Supervisor Supervisor) throws Exception {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE supervisores SET PASSWORD = '" + Supervisor.getPassword() + "' WHERE NOMBRE = '" + Supervisor.getNombre() + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido actualizar el supervisor. Error: " + e.getMessage());
        }
    }

    public void delete(String nombre) throws Exception {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM supervisores WHERE NOMBRE = '" + nombre + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido borrar el supervisor. Error: " + e.getMessage());
        }
    }
}
