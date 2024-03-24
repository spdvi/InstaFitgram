package com.mycompany.instafitgram.dataAcess;

import com.mycompany.instafitgram.Attempt;
import com.mycompany.instafitgram.Review;
import com.mycompany.instafitgram.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manu
 */
public class DataAcess {

    private Connection getConection() {
        Connection connection = null;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("src\\main\\resources\\properties\\application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("URL");
        try {
            connection = DriverManager.getConnection(url, properties);
        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public User getUser(String email) {
        User usuario = null;
        String sql = "Select * FROM Usuaris WHERE Email=?";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                usuario = new User();
                usuario.setId(resultSet.getInt("Id"));
                usuario.setNom(resultSet.getString("Nom"));
                usuario.setEmail(resultSet.getString("Email"));
                usuario.setPasswordHash(resultSet.getString("PasswordHash"));
                //user.setFoto(resultSet.getBytes("Foto"));
                if (resultSet.getInt("Instructor") == 1) {
                    usuario.setIsIntructor(true);
                } else {
                    usuario.setIsIntructor(false);
                }
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;
    }
    
    public User getUserWithId (int id){
            
        User usuario = null;
        String sql = "Select * FROM Usuaris WHERE id=?";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                usuario = new User();
                usuario.setId(resultSet.getInt("Id"));
                usuario.setNom(resultSet.getString("Nom"));
                usuario.setEmail(resultSet.getString("Email"));
                usuario.setPasswordHash(resultSet.getString("PasswordHash"));
                //user.setFoto(resultSet.getBytes("Foto"));
                if (resultSet.getInt("Instructor") == 1) {
                    usuario.setIsIntructor(true);
                } else {
                    usuario.setIsIntructor(false);
                }
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;
    
    }

    public ArrayList<Attempt> getPendingAttempts() {
        ArrayList<Attempt> attempts = new ArrayList<Attempt>();
        String sql = "SELECT * FROM Intents LEFT JOIN Review on review.IdIntent = Intents.Id "
                + "LEFT JOIN Exercicis on Exercicis.id = intents.IdExercici "
                + "WHERE review.id IS NULL ORDER BY Timestamp_Inici";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Attempt attempt = new Attempt();
                attempt.setId(resultSet.getInt("Id"));
                attempt.setIdUser(resultSet.getInt("IdUsuari"));
                attempt.setIdExercise(resultSet.getInt("IdExercici"));               
                attempt.setTimeStampStart(resultSet.getDate("Timestamp_Inici"));
                attempt.setTimeStampEnd(resultSet.getDate("Timestamp_Fi"));
                attempt.setVideoFile(resultSet.getString("Videofile"));
                attempt.setExercise(resultSet.getString("Descripcio"));
                attempts.add(attempt);

            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return attempts;
    }

    public ArrayList<Attempt> getAllAttemptsUser(User user) {
        ArrayList<Attempt> attempts = new ArrayList<Attempt>();
        String sql = "SELECT * FROM Intents LEFT JOIN Review on review.IdIntent = Intents.Id "
                + "LEFT JOIN Exercicis on Exercicis.id = intents.IdExercici "
                + "WHERE IdUsuari =?";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1, user.getId());
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Attempt attempt = new Attempt();
                attempt.setId(resultSet.getInt("Id"));
                attempt.setIdUser(resultSet.getInt("IdUsuari"));
                attempt.setIdExercise(resultSet.getInt("IdExercici"));
                attempt.setTimeStampStart(resultSet.getDate("Timestamp_Inici"));
                attempt.setTimeStampEnd(resultSet.getDate("Timestamp_Fi"));
                attempt.setVideoFile(resultSet.getString("Videofile"));
                attempt.setExercise(resultSet.getString("Descripcio"));
                attempts.add(attempt);
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return attempts;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> user = new ArrayList<User>();
        String sql = "Select * FROM Usuaris ";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                User usuario = new User();
                if (resultSet.getInt("Instructor") == 0) {
                    usuario.setId(resultSet.getInt("Id"));
                    usuario.setNom(resultSet.getString("Nom"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setPasswordHash(resultSet.getString("PasswordHash"));
                    //user.setFoto(resultSet.getBytes("Foto"));
                    usuario.setIsIntructor(resultSet.getBoolean("Instructor"));
                    user.add(usuario);
                }
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public Review getReviewAttempt(Attempt attempt) {
        Review review = new Review();
        String sql = "Select * FROM Review WHERE idIntent =?";
        Connection connection = getConection();

        try {
            PreparedStatement selectStatement = connection.prepareCall(sql);
            selectStatement.setInt(1, attempt.getId());
            ResultSet resultset = selectStatement.executeQuery();
            while (resultset.next()) {
                review.setId(resultset.getInt("Id"));
                review.setIdAttempt(resultset.getInt("IdIntent"));
                review.setIdReviewer(resultset.getInt("idreviewer"));
                review.setReview(resultset.getInt("Valoracio"));
                review.setComent(resultset.getString("Comentari"));
            }
            selectStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return review;
    }

    public String setReview(Review review) {
        String result = "";
        String sql = "INSERT INTO Review (IdIntent, IdReviewer, Valoracio, Comentari) VALUES (?,?,?,?) ";
        Connection connection = getConection();

        try {
            PreparedStatement insertStatement = connection.prepareCall(sql);
            insertStatement.setInt(1, review.getIdAttempt());
            insertStatement.setInt(2, review.getIdReviewer());
            insertStatement.setInt(3, review.getReview());
            insertStatement.setString(4, review.getComent());
            int resultset = insertStatement.executeUpdate();
            result = "Review added  sucessful";

            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public String updateReview(Review review) {
        String result = "";
        String sql = "UPDATE Review SET IdIntent = ?, IdReviewer = ?, Valoracio = ?, Comentari = ? WHERE Id = ? ";
        Connection connection = getConection();

        try {
            PreparedStatement updateStatement = connection.prepareCall(sql);
            updateStatement.setInt(1, review.getIdAttempt());
            updateStatement.setInt(2, review.getIdReviewer());
            updateStatement.setInt(3, review.getReview());
            updateStatement.setString(4, review.getComent());
            updateStatement.setInt(5,review.getId());
            int resultset = updateStatement.executeUpdate();
            result = "Review updated  sucessful";

            updateStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    
    public String deleteReview(Review review){
                String result = "";
        String sql = "DELETE FROM Review WHERE Id = ? ";
        Connection connection = getConection();

        try {
            PreparedStatement updateStatement = connection.prepareCall(sql);
            updateStatement.setInt(1,review.getId());
            int resultset = updateStatement.executeUpdate();
            result = "Review deleted  sucessful";

            updateStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataAcess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
