package org.example.model;

import lombok.Data;

@Data
public class Todo {
    private long id;
    private long userId;
    private String title;
    private boolean completed;
}
