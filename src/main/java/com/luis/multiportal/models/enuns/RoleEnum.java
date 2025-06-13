package com.luis.multiportal.models.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum RoleEnum {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private Integer code;
    private String description;

    public static RoleEnum toEnum(Integer code){

        if (Objects.isNull(code)){
            return null;
        }
        for (RoleEnum x : RoleEnum.values()){
            if (code.equals(x.getCode())){
                return x;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
