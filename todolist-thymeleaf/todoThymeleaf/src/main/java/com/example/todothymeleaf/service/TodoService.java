package com.example.todothymeleaf.service;


import com.example.todothymeleaf.exception.NotFoundException;
import com.example.todothymeleaf.model.Todo;
import com.example.todothymeleaf.request.CreateTodoRequest;
import com.example.todothymeleaf.request.UpdateTodoRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private List<Todo> todos;

    // trong constructor, tao ta 1 so doi tuong todo va them vao danh sach
    public TodoService() {
        Random rd = new Random();
        todos = new ArrayList<>();

        todos.add(new Todo(rd.nextInt(100), "lam bai tap", true));
        todos.add(new Todo(rd.nextInt(100), "di da bong", false));
        todos.add(new Todo(rd.nextInt(100), "di choi voi ban be", true));

    }

    public List<Todo> getTodos(String status) {
        return switch (status) {
            case "true" -> todos.stream().filter(Todo::isStatus).collect(Collectors.toList());
            case "false" -> todos.stream().filter(todo -> !todo.isStatus()).collect(Collectors.toList());
            default -> todos;
        };
    }

    public Todo getTodoById(int id) {
        Optional<Todo> todoOptional = findById(id);
//        if (todoOptional.isPresent()){
//            return todoOptional.get();
//        }
//        throw new NotFoundException("khong tim thay cong viec co id = " + id);

        return todoOptional.orElseThrow(() -> {
            throw new NotFoundException("khong tim thay cong viec co id = " + id);
        });
    }

    public Todo updateTodo(int id, UpdateTodoRequest request) {
        //kiem tra cong viec co ton tai hay khong
//        Optional<Todo> todoOptional = findById(id);
//        if (todoOptional.isEmpty()) {
//            throw new NotFoundException("khong tim thay cong viec co id = " + id);
//        }

        // cap nhat cong viec theo thong tin tu request
//        Todo todo = todoOptional.get();
        Todo todo = getTodoById(id);
        todo.setTitle(request.getTitle());
        todo.setStatus(request.isStatus());

        // tra ve cong viec sau khi cap nhat
        return todo;
    }

    public void deleteTodo(int id) {
        Todo todo = getTodoById(id);
        todos.removeIf(t -> t.getId() == todo.getId());
    }

    // helper method : tim kiem 1 todo theo id
    public Optional<Todo> findById(int id) {
        return todos.stream().filter(todo -> todo.getId() == id).findFirst();
    }

    public Todo createTodo(CreateTodoRequest request) {
        // co the validate title neu de trong
        Random rd = new Random();
        Todo todo = new Todo(rd.nextInt(100), request.getTitle(), false);
        todos.add(todo);
        return todo;
    }

}
