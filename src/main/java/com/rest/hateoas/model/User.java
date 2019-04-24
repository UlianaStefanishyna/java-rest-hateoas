package com.rest.hateoas.model;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User extends ResourceSupport {

//    private Long id;
    private String name;
    private String email;
    private Status status;
}