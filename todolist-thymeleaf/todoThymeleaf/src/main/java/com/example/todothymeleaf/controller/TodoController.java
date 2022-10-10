package com.example.todothymeleaf.controller;


import com.example.todothymeleaf.model.Todo;
import com.example.todothymeleaf.request.CreateTodoRequest;
import com.example.todothymeleaf.request.UpdateTodoRequest;
import com.example.todothymeleaf.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TodoController {
    private final TodoService todoService;

    // lay danh sach tat ca cong viec
    @GetMapping("/todos")
    public List<Todo> getTodo(@RequestParam(required = false, defaultValue = "") String status) {
        return todoService.getTodos(status);
    }

    // lay cong viec theo id
    @GetMapping("/todos/{id}")
    public Todo getTodoById(@PathVariable int id) {
        return todoService.getTodoById(id);
    }

    // tao moi cong viec
//    @PostMapping("/todos")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Todo createTodo(@RequestBody CreateTodoRequest request){
//        return todoService.createTodo(request);
//    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody CreateTodoRequest request) {
        Todo todo = todoService.createTodo(request);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    // cap nhat cong viec
    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable int id, @RequestBody UpdateTodoRequest request) {
        return todoService.updateTodo(id, request);
    }

    // xoa cong viec
    @DeleteMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable int id) {
        todoService.deleteTodo(id);
    }
}
