package com.company;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection con = null;

    /*
    * Constructor
    *
    * */
    public Database() {

        try {
            con = DriverManager.getConnection("jdbc:sqlite:todos.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * Gets all todos from the database
    *
    * */
    public ArrayList<Todo> getTodos() {

        // create a temp arraylist of todos
        ArrayList<Todo> todos = new ArrayList<>();

        // query to question the db for todos
        String query = "SELECT * FROM todos";

        // prepared statement inside try/catch block
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet res = stmt.executeQuery();

            // loop through all result from db and create a instance of the Todo class for each result
            while(res.next()) {
                int id = res.getInt("id");
                String title = res.getString("title");
                String description = res.getString("description");
                String timestamp = res.getString("timestamp");

                // for each result in loop, create a new Todo
                todos.add(new Todo(id, title, description, timestamp));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return todos;
    }

    /*
    * Gets a todo from the database based on a id
    * parameter: id
    *
    * */
    public Todo getTodoById(int id) {

        // create a temporary todo
        Todo todo = null;

        // query to question the db for a spesific todo
        String query = "SELECT * FROM todos WHERE id = ?";

        // prepared statement inside try/catch block
        try {
            PreparedStatement stmt = con.prepareStatement(query);

            // secure the query from SQL injection
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            // loop through result from db and create a instance of the Todo class
            while(res.next()) {
                int uid = res.getInt("id");
                String title = res.getString("title");
                String description = res.getString("description");
                String timestamp = res.getString("timestamp");

                // create a new Todo
                todo = new Todo(uid, title, description, timestamp);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return todo;
    }

    /*
    * Create a new todo in the database
    * parameter: Todo
    *
    * */
    public int createTodo(Todo newTodo) {
        int newUid = 0;

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO todos(title, description, timestamp) VALUES (?, ?, ?)");

            // protect SQL injection
            stmt.setString(1, newTodo.getTitle());
            stmt.setString(2, newTodo.getDescription());
            stmt.setString(3, newTodo.getTimestamp());

            // using executeUpdate when inserting or updating database
            stmt.executeUpdate();

            // get the id of the new record in db
            ResultSet res = stmt.getGeneratedKeys();

            while(res.next()) {
                newUid = res.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return newUid;
    }

    /*
    * Update a todo in database
    * parameter: Todo
    *
    * */
    public void updateTodo(Todo todo) {
        // only update with an id
        if(todo.getId() < 1) {
            System.out.println("Must have an id");
            return;
        }

        String query = "UPDATE todos SET title = ?, description = ?, timestamp = ?  WHERE id = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);

            // protect SQL injection
            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setString(3, todo.getTimestamp());
            stmt.setInt(4, todo.getId());

            stmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }

        Todo updatedTodo = getTodoById(todo.getId());
        System.out.println(updatedTodo.toString());
    }

    /*
    * Delete a todo in database
    * parameter: id
    *
    * */
    public void deleteTodo(int id) {
        // only delete todo with a id
        if(id < 1) {
            System.out.println("Must have an id");
            return;
        }

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM todos WHERE id = ?");

            // protect SQL injection
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("todo with id: " + id + " is deleted");
    }
}
