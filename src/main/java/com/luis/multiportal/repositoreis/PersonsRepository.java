package com.luis.multiportal.repositoreis;


import com.luis.multiportal.models.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsRepository extends JpaRepository<Persons, Long> {
    long countBySexo(String sexo);
}
