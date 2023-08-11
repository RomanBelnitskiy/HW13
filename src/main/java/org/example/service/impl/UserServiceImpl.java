package org.example.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final HttpClient CLIENT;
    private final Gson GSON;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String USERS_PATH = "users";

    public UserServiceImpl() {
        CLIENT = HttpClient.newHttpClient();
        GSON = new Gson();
    }

    @Override
    public User createUser(User user) {
        User createdUser = null;
        try {
            URI uri = URI.create(BASE_URL + USERS_PATH);
            String userJson = GSON.toJson(user);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(userJson))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            createdUser = GSON.fromJson(response.body(), User.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong. The user wasn't created.");
        }

        return createdUser;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = null;
        try {
            URI uri = URI.create(BASE_URL + USERS_PATH + "/" + user.getId());
            String userJson = GSON.toJson(user);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .PUT(HttpRequest.BodyPublishers.ofString(userJson))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            updatedUser = GSON.fromJson(response.body(), User.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong. The user wasn't updated.");
        }

        return updatedUser;
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            URI uri = URI.create(BASE_URL + USERS_PATH + "/" + user.getId());
            String userJson = GSON.toJson(user);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .DELETE()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong. The user wasn't deleted.");
        }

        return false;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            URI uri = URI.create(BASE_URL + USERS_PATH);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            return GSON.fromJson(response.body(), TypeToken.getParameterized(List.class, User.class).getType());
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong. Can't receive all users.");
        }

        return Collections.emptyList();
    }

    @Override
    public User getUserById(long id) {
        try {
            URI uri = URI.create(BASE_URL + USERS_PATH + "/" + id);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            return GSON.fromJson(response.body(), User.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong. The user not founded.");
        }

        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            URI uri = URI.create(BASE_URL + USERS_PATH + "?username=" + username);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            List<User> users = GSON.fromJson(response.body(), TypeToken.getParameterized(List.class, User.class).getType());

            return users.get(0);
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong. The user not founded.");
        }

        return null;
    }
}
