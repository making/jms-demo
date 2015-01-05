package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired
    JmsMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/")
    String hello() {
        Message<String> message = MessageBuilder
                .withPayload("Hello!")
                .build();
        messagingTemplate.send("hoge", message);
        return "Hello World!";
    }

    @JmsListener(destination = "hoge")
    void handleMessage(Message<String> message) {
        log.info("received! {}", message);
    }
}