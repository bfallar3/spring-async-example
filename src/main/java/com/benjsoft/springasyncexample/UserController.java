package com.benjsoft.springasyncexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserServiceImpl userService;

  @GetMapping("/users")
  public ResponseEntity<NameModel> getUsers() throws InterruptedException, ExecutionException {
    LOGGER.info("getUsers api started at thread: {}", Thread.currentThread().getName());

    CompletableFuture<String> userBenj = userService.getUsers("Benj");
    CompletableFuture<String> userYciel = userService.getUsers("Yciel");
    CompletableFuture<String> userXp = userService.getUsers("XP");

    CompletableFuture.allOf(userXp, userBenj, userYciel).join();

    NameModel nameModel = new NameModel();
    nameModel.setName1(userBenj.get());
    nameModel.setName2(userYciel.get());
    nameModel.setName3(userXp.get());

    return ResponseEntity.ok(nameModel);
  }
}
