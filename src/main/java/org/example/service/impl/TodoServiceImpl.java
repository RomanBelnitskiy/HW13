package org.example.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Todo;
import org.example.service.TodoService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class TodoServiceImpl implements TodoService {
    private final HttpClient CLIENT;
    private final Gson GSON;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public TodoServiceImpl() {
        CLIENT = HttpClient.newHttpClient();
        GSON = new Gson();
    }

    @Override
    public List<Todo> getUncompletedTasks(long userId) {
        try {
            List<Todo> userTasks = getUserTasks(userId);

            return userTasks.stream()
                    .filter(t -> !t.isCompleted())
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Todo> getUserTasks(long userId) throws IOException, InterruptedException {
        // find all user's tasks
        URI uri = URI.create(BASE_URL + "users/" + userId + "/todos");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        TypeToken<?> typeToken = TypeToken.getParameterized(List.class, Todo.class);

        return GSON.fromJson(response.body(), typeToken.getType());
    }
}
