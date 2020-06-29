package com.br.gitrepos.gitrepo.model.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    
    @ApiModelProperty(required = true)
    @NotNull
    private String username;
    
    @ApiModelProperty(required = true)
    @NotNull
    @Length(min = 6)
    private String password;
}