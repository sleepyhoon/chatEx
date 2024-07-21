package hello.chatex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class ChatExApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatExApplication.class, args);
    }

}
