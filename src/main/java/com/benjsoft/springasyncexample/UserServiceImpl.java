package com.benjsoft.springasyncexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl {

   private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

   @Async("asyncExecutor")
   public CompletableFuture<String> getUsers(String name) throws InterruptedException {

      LOGGER.info("getUsers service started at thread: {}", Thread.currentThread().getName());
      // simulate long running execution

      Thread.sleep(3000);

      return CompletableFuture.supplyAsync(() -> "Hello " + name);
      //return CompletableFuture.completedFuture("getUsers completed " + name + "!");
   }
}
