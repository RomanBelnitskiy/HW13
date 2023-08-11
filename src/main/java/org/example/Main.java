package org.example;

import org.example.model.*;
import org.example.service.PostService;
import org.example.service.TodoService;
import org.example.service.UserService;
import org.example.service.impl.PostServiceImpl;
import org.example.service.impl.TodoServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.util.List;

public class Main {
    private static UserService userService = new UserServiceImpl();
    private static PostService postService = new PostServiceImpl();
    private static TodoService todoService = new TodoServiceImpl();
    private static User testUser;

    static {
        testUser = User.builder()
                .id(1)
                .name("Leanne Graham")
                .username("Bret")
                .email("Sincere@april.biz")
                .address(new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", new GeoLocation(-37.3159, 81.1496)))
                .phone("1-770-736-8031")    // new phone number
                .website("hildegard.org")
                .company(new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"))
                .build();
    }

    public static void main(String[] args) {

        // Task 1
        createUser();
        updateUser();
        deleteUser();
        getAllUsers();
        getUserById();
        getUserByUsername();

        // Task 2
        printToFileUserLastPostComments();

        // Task 3
        getUncompletedUserTasks();
    }

    public static void createUser() {
        System.out.println("========= Task 1 create user ==========================");
        User user = User.builder()
                .name("Frodo Baggins")
                .username("Frodo")
                .email("baggins@gmail.com")
                .address(new Address("Main street", "1", "Shire", "00000", new GeoLocation(100.000, 100.00)))
                .phone("+01234-567-89-00")
                .website("baggins.net")
                .company(new Company("Company of the Ring", "We deliver any ring to Mordor", "ring delivery"))
                .build();

        User createdUser = userService.createUser(user);
        System.out.println(createdUser);
        System.out.println("=======================================================");
        System.out.println();
    }

    public static void updateUser() {
        System.out.println("========= Task 1 update user ==========================");
        User updateUser = userService.updateUser(testUser);
        System.out.println(updateUser);
        System.out.println("=======================================================");
        System.out.println();
    }

    public static void deleteUser() {
        System.out.println("========= Task 1 delete user ==========================");
        boolean userDeleted = userService.deleteUser(testUser);
        String result = userDeleted ? "successfully deleted" : "not deleted";
        System.out.println("User with id = " + testUser.getId() + " " + result);
        System.out.println("=======================================================");
        System.out.println();
    }

    public static void getAllUsers() {
        System.out.println("========= Task 1 get all users ========================");
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
        System.out.println("=======================================================");
        System.out.println();
    }

    public static void getUserById() {
        System.out.println("========= Task 1 find user by id ======================");
        User user = userService.getUserById(1);
        System.out.println(user);
        System.out.println("=======================================================");
        System.out.println();
    }

    public static void getUserByUsername() {
        System.out.println("========= Task 1 find user by username ================");
        User user = userService.getUserByUsername("Bret");
        System.out.println(user);
        System.out.println("=======================================================");
        System.out.println();
    }

    public static void printToFileUserLastPostComments() {
        System.out.println("========= Task 2 write user's last post comments to file ========");
        postService.printToFileUserLastPostComments(testUser);
        System.out.println("=======================================================");
        System.out.println();
    }

    public static void getUncompletedUserTasks() {
        System.out.println("========= Task 3 find user's uncompleted tasks ==========");
        List<Todo> tasks = todoService.getUncompletedTasks(1);
        tasks.forEach(System.out::println);
        System.out.println("=======================================================");
        System.out.println();
    }
}