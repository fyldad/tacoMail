package com.example.tacoemaillistener.data.repository;

import com.example.tacoemaillistener.data.model.EmailIngredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<EmailIngredient, String> {
}
