package io.robusta.upload.api;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.robusta.upload.api.FileDTO;
import io.robusta.upload.api.Outils;

public class FileDAO {
    private Connection connection;
    public FileDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://10.31.1.32:3306/AppDB", "pox", "pox");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Chargement driver failure", e);
        } catch (SQLException e) {
            throw new RuntimeException("Impossible d'établir une connection avec le SGBD", e);
        }
    }
    
    public List<FileDTO> findAll() {
        try {
            String sql = "SELECT * FROM `files`";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<FileDTO> f = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("file_name");
                int id = resultSet.getInt("file_id");
                FileDTO file = new Outils().createFileDTOFromName(name);
                file.setId(id);
                f.add(file);
            }
            return f;
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de réaliser l(es) opération(s)", e);
        }
    }
    
    public void delete(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `files` WHERE file_id = ?");
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de réaliser l(es) opération(s)", e);
        }
    }
    
    public void add(String fileName, InputStream inputStream) {
        try {
            String sql = "INSERT INTO files (file_name,photo) values (?,?)";
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
            statement.setString(1, fileName);
            statement.setBlob(2, inputStream);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Impossible d'ajouter le disque", e);
        }
    }
    
    public void update(String id, String name) {
        try {
            String sqlQuery = "UPDATE files SET file_name = ? WHERE file_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, name);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de réaliser l(es) opération(s)", e);
        }
    }
}
