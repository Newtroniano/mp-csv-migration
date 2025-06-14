package com.luis.multiportal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luis.multiportal.models.enuns.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    public static final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotBlank()
    @Size(min = 2, max = 100)
    private String user;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_role")
    @Column(name = "role", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<RoleEnum> getProfiles(){
        return this.profiles.stream().map(x -> RoleEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(RoleEnum profileEnum){
        this.profiles.add(profileEnum.getCode());
    }
}
