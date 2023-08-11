package org.example.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.User;
import org.example.service.PostService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.WRITE;

public class PostServiceImpl implements PostService {
    private final HttpClient CLIENT;
    private final Gson GSON;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public PostServiceImpl() {
        CLIENT = HttpClient.newHttpClient();
        GSON = new Gson();
    }

    @Override
    public void printToFileUserLastPostComments(User user) {
        try {
            List<Post> userPosts = getUserPosts(user.getId());

            long lastPostId = findLastPostId(userPosts);

            List<Comment> comments = getPostComments(lastPostId);

            writeCommentsToFile(user, lastPostId, comments);
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong. Can't receive comments.");
            e.printStackTrace();
        }
    }



    private List<Post> getUserPosts(long userId) throws IOException, InterruptedException {
        // find all user's posts
        URI uri = URI.create(BASE_URL + "users/" + userId + "/posts");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        TypeToken<?> typeToken = TypeToken.getParameterized(List.class, Post.class);

        return GSON.fromJson(response.body(), typeToken.getType());
    }

    private long findLastPostId(List<Post> userPosts) {
        // find last user's post
        return userPosts.stream()
                .mapToLong(Post::getId)
                .max().orElseThrow();
    }

    private List<Comment> getPostComments(long postId) throws IOException, InterruptedException {
        // find all post's comments
        URI uri = URI.create(BASE_URL + "posts/" + postId + "/comments");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        TypeToken<?> typeToken = TypeToken.getParameterized(List.class, Comment.class);

        return GSON.fromJson(response.body(), typeToken.getType());
    }

    private void writeCommentsToFile(User user, long lastPostId, List<Comment> comments) throws IOException {
        String stringOfComments = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(comments);

        Path path = Path.of("./user-"+ user.getId() + "-post-"+ lastPostId + "-comments.json");
        if (!path.toFile().exists()) {
            Files.createFile(path);
        }

        Files.writeString(path, stringOfComments, WRITE);
    }
}
