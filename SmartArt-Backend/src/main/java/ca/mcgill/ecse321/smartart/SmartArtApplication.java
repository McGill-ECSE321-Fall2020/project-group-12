package ca.mcgill.ecse321.smartart;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/** @author Group 12 Main class that runs our Smart Art application. */
@RestController
@SpringBootApplication
public class SmartArtApplication {

  public static void main(String[] args) {
    SpringApplication.run(SmartArtApplication.class, args);
  }

  @RequestMapping("/")
  public String greeting() {
    return "Hello world!";
  }
}
