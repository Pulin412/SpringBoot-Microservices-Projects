package com.pressford.publisher;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pressford.publisher.entities.Message;
import com.pressford.publisher.entities.User;
import com.pressford.publisher.repositories.MessageRepository;
import com.pressford.publisher.repositories.UserRepository;

/**
 * @author Pulin
 * @version 1.0
 *
 *          Main starting point of the application. Start the application from
 *          here to load the dummy data for Messages/Posts and dummy Users
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class Application implements CommandLineRunner {

    @Value("#{${pressford.user.likes.map}}")
    private HashMap<String, String> userLikes;

    /**
     * Autowired Message and User repository to add the dummy data at the start
     * of the application
     */
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    /**
     * @param messageRepository
     * @param userRepository
     */
    @Autowired
    public Application(final MessageRepository messageRepository, final UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    /**
     * Main method
     *
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*
     * Adding the dummy data in the run method before the launch of the main
     * application
     */
    @Override
    public void run(final String... args) throws Exception {

        saveMessage(new Message("Title 1",
            "Spring Boot makes it easy to create stand-alone, production-grade Spring-based Applications that you can run.",
            new Date(), "Matt"));
        saveMessage(new Message("Title 2",
            "Spring Boot makes it easy to create stand-alone, production-grade Spring-based Applications that you can run.",
            new Date(), "Adam"));
        saveMessage(new Message("Title 3",
            "Spring Boot makes it easy to create stand-alone, production-grade Spring-based Applications that you can run.",
            new Date(), "Phil"));
        saveMessage(new Message("Title 4",
            "Spring Boot makes it easy to create stand-alone, production-grade Spring-based Applications that you can run.",
            new Date(), "Dave"));

        for (Map.Entry<String, String> entry : this.userLikes.entrySet()) {
            String val = entry.getValue();
            if (val != null) {
                String[] vals = val.split(",");
                saveUsers(new User(vals[0], Integer.valueOf(vals[1]), Integer.valueOf(vals[2]), Integer.valueOf(vals[3])));
            }
        }
    }

    /**
     * saving Message table data
     *
     * @param message
     */
    private void saveMessage(final Message message) {
        this.messageRepository.save(message);
    }

    /**
     * saving user data
     *
     * @param user
     */
    private void saveUsers(final User user) {
        this.userRepository.save(user);
    }
}