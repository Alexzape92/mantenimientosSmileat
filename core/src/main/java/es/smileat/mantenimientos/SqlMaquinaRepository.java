package es.smileat.mantenimientos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;

public class SqlMaquinaRepository implements IMaquinaRepository {
    private Connection conn;
    private Statement stmt;

    public SqlMaquinaRepository() throws Exception {
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Smileat", "root", "adminSmileatJA");
            this.stmt = conn.createStatement();
        }catch(Exception e){
            throw new RuntimeException("No se ha podido conectar con la base de datos. Error: " + e.getMessage());
        }
    }

    public void save(Maquina maquina) throws Exception {
        Collection<Maquina> maquinas = loadAll();

        for(Maquina e : maquinas){
            if(e.getId().equals(maquina.getId())){
                throw new IllegalArgumentException("Ya existe una maquina con ese ID");
            }
        }
        
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO maquinas (ID, MODELO, INCIDENCIAS, SALA) VALUES ('" + maquina.getId() + "', '" + maquina.getModelo() + "', "  + maquina.getIncidencias() + ", '" + maquina.getSala()  + "')");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido guardar la maquina. Error: " + e.getMessage());
        }
    }

    public Maquina load(String id) throws Exception {
        try{
            stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery("SELECT * FROM maquinas WHERE ID = '" + id + "'");

            while(rs.next()){
                return new Maquina(rs.getString("ID"), rs.getString("MODELO"), rs.getInt("INCIDENCIAS"), rs.getString("SALA"));
            }
        } catch(Exception e){
            throw new RuntimeException("No se ha podido cargar la maquina. Error: " + e.getMessage());
        }

        throw new IllegalArgumentException("No se ha encontrado la maquina");
    }

    public void update(Maquina maquina) throws Exception {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE maquinas SET MODELO = '" + maquina.getModelo() + "', INCIDENCIAS = " + maquina.getIncidencias() + ", SALA = '" + maquina.getSala() + "' WHERE ID = '" + maquina.getId() + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido actualizar la maquina. Error: " + e.getMessage());
        }
    }

    public void delete(String id) throws Exception {
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM maquinas WHERE ID = '" + id + "'");
        } catch(Exception e){
            throw new RuntimeException("No se ha podido borrar la maquina. Error: " + e.getMessage());
        }
    }

    public Collection<Maquina> loadAll() throws Exception {
        Collection<Maquina> maquinas = new java.util.ArrayList<Maquina>();
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM maquinas");

            while(rs.next()){
                maquinas.add(new Maquina(rs.getString("ID"), rs.getString("MODELO"), rs.getInt("INCIDENCIAS"), rs.getString("SALA")));
            }
        } catch(Exception e){
            throw new RuntimeException("No se han podido cargar las maquinas. Error: " + e.getMessage());
        }

        return maquinas;
    }
}
