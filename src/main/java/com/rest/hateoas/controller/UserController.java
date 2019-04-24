package com.rest.hateoas.controller;

import com.rest.hateoas.model.Status;
import com.rest.hateoas.model.User;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping(produces = {"application/hal+json"})
    public Resources<User> getAll() {

        User user1 = User.builder()
                .email("TEST_EMAIL")
                .name("ACTIVE_NAME")
                .status(Status.ACTIVE).build();

        User user2 = User.builder()
                .email("TEST_EMAIL")
                .name("DISABLED_NAME")
                .status(Status.CLOSED).build();
        List<User> response = asList(user1, user2);

        response.forEach(res -> {
            Link selfLink = linkTo(methodOn(UserController.class).getAll()).withSelfRel();

            if (res.getStatus().equals(Status.ACTIVE)) {
                Link link = linkTo(methodOn(UserController.class)
                        .changeStatus(res.getEmail(), res.getStatus().toString())).withRel("changeStatus");
                res.add(link);
            }
            res.add(selfLink);
        });

        Link link = linkTo(methodOn(UserController.class).getAll()).withSelfRel();
        return new Resources<>(response, link);
    }

    @GetMapping("/{email}")
    public User changeStatus(@PathVariable String email, @RequestParam String status) {
        User user = User.builder().email(email).build();

        user.setStatus(Status.valueOf(status));
        Link selfLink = linkTo(methodOn(UserController.class).changeStatus(email, status)).withSelfRel();
        user.add(selfLink);

        return user;
    }

}
