package com.example.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private String email;
}
