package org.example.model;

import lombok.Data;

@Data
public class Post {
    private long id;
    private long userId;
    private String title;
    private String body;
}
