package com.luis.multiportal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = Persons.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Persons {

    public Persons(String nome, String sobreNome, String email, String sexo, String ipAcesso, int idade, String nascimento) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.email = email;
        this.sexo = sexo;
        this.ipAcesso = ipAcesso;
        this.idade = idade;
        this.nascimento = nascimento;
    }

    public static final String TABLE_NAME = "persons";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome", length = 100)
    @JsonProperty("Nome")
    private String nome;

    @Column(name = "sobreNome", length = 100)
    @JsonProperty("SobreNome")
    private String sobreNome;

    @Column(name = "email", length = 100)
    @JsonProperty("Email")
    private String email;

    @Column(name = "sexo", length = 30)
    @JsonProperty("Sexo")
    private String sexo;

    @Column(name = "ipAcesso", length = 100)
    @JsonProperty("IpAcesso")
    private String ipAcesso;

    @Column(name = "idade")
    @JsonProperty("Idade")
    private int idade;

    @Column(name = "nascimento")
    @JsonProperty("Nascimento")
    private String nascimento;

    public void corrigirDataNascimento() {

        LocalDate dataAtual = LocalDate.now();
        LocalDate dataNascimento = dataAtual.minusYears(this.idade);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.nascimento = dataNascimento.format(formatter);

    }


}
