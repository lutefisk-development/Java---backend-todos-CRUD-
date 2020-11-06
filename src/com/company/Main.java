package com.company;

import java.util.ArrayList;

public class Main {

    static Database db = new Database();

    public static void main(String[] args) {

        // get all todos
        //getTodos();

        // get todo by id
        //getTodoById(2);

        // create new todo
        //createTodo("dentist", "yearly dentist appointment", "2020-11-09 13:30:00");

        // update todo
        //updateTodo(2);

        // delete todo
        //deleteTodo(4);

    }

    /*
    * READ
    *
    * */
    public static void getTodos() {
        ArrayList<Todo> todos = db.getTodos();

        todos.forEach(todo -> {
            System.out.println(todo.toString());
        });
    }

    /*
    * READ
    *
    * */
    public static void getTodoById(int id) {
        Todo todo = db.getTodoById(id);

        if(todo == null) {
            System.out.println("No todo by the id");
        } else {
            System.out.println(todo.toString());
        }
    }

    /*
    * CREATE
    *
    * */
    public static void createTodo(String title, String description, String timestamp) {
        Todo newTodo = new Todo(title, description, timestamp);
        int id = db.createTodo(newTodo);

        if(id != 0) {

            // sets the id based on the new record in db
            newTodo.setId(id);

            Todo todo = db.getTodoById(id);

            if(todo == null) {
                System.out.println("No todo by that id");
            } else {
                System.out.println(todo.toString());
            }
        }
    }

    /*
    * UPDATE
    *
    * */
    public static void updateTodo(int id) {
        // only update if there is a todo in the database by that id
        Todo todo = db.getTodoById(id);
        if(todo == null) {
            System.out.println("No todo by the id");
        } else {

            // update the todo
            todo.setTitle("my first workout");
            //todo.setDescription("something");
            //todo.setTimestamp("0000-00-00 00:00:00");

            db.updateTodo(todo);
        }
    }


    /*
    * DELETE
    *
    * */
    public static void deleteTodo(int id) {
        // only delete if there is a todo in the database by that id
        Todo todo = db.getTodoById(id);
        if(todo == null) {
            System.out.println("No todo by the id");
        } else {
            db.deleteTodo(id);
        }
    }
}
