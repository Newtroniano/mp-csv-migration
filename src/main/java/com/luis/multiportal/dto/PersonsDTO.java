package com.luis.multiportal.dto;

import com.luis.multiportal.models.Persons;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonsDTO {
    private String nome;
    private String sobreNome;
    private String email;
    private String sexo;
    private String ipAcesso;
    private int idade;
    private String nascimento;


    public PersonsDTO(Persons p) {
        this.nome = p.getNome();
        this.sobreNome = p.getSobreNome();
        this.email = p.getEmail();
        this.sexo = p.getSexo();
        this.ipAcesso = p.getIpAcesso();
        this.idade = p.getIdade();
        this.nascimento = p.getNascimento();
    }
}
