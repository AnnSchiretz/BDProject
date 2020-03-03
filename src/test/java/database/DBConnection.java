package database;

import models.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private void setupConnection(){
        try {
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/qa02?"
                            + "user=root&password=12345678");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public List<Group> selectFromTable(String tableName) throws Exception {
        try {
            setupConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement(String.format("select * from %s", tableName));
            // Result set get the result of the SQL query
            resultSet = preparedStatement
                    .executeQuery();
            return getGroupList(resultSet);
        } finally {
            close();
        }
    }
    public List<Group> selectFromTable(String tableName, int id) throws Exception {
        try {
            setupConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement(
                    String.format("SELECT * FROM %s WHERE id = ?", tableName));
            preparedStatement.setInt(1, id);
            // Result set get the result of the SQL query
            resultSet = preparedStatement
                    .executeQuery();
           return getGroupList(resultSet);
        } finally {
            close();
        }
    }
    public void insertIntoTable(String tableName, Group group) throws Exception {
        try {
            setupConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement(
                    String.format("INSERT INTO %s (name,start_date) VALUES (?,?)", tableName));
            preparedStatement.setString(1, group.getName());
            preparedStatement.setDate(2, group.getStartDate());
            // Result set get the result of the SQL query
            preparedStatement
                    .execute();
        } finally {
            close();
        }
    }
    public void updateIntoTable(String tableName,int id, Group group) throws Exception {
        try {
            setupConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement(
                    String.format("UPDATE %s SET name = ?, start_date = ? WHERE id = ?", tableName));
            preparedStatement.setString(1, group.getName());
            preparedStatement.setDate(2, group.getStartDate());
            preparedStatement.setInt(3, id);
            // Result set get the result of the SQL query
            preparedStatement
                    .execute();
        } finally {
            close();
        }
    }
    private List<Group> getGroupList(ResultSet resultSet) throws SQLException {
        List<Group> groups = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Date startDate = resultSet.getDate("start_date");
            groups.add(new Group(id, name, startDate));
        }
        return groups;
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        }catch (Exception ignored) {
        }
    }
    public void deleteFromTable(String tableName, int id) throws Exception {
        try {
            setupConnection();
            // Statements allow to issue SQL queries to the database
            preparedStatement = connect.prepareStatement(
                    String.format("DELETE FROM %s WHERE id = ?", tableName));
            preparedStatement.setInt(1, id);
            // Result set get the result of the SQL query
            preparedStatement.execute();
        } finally {
            close();
        }
    }

}
