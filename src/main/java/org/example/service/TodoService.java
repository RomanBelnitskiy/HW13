package org.example.service;

import org.example.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getUncompletedTasks(long userId);
}
