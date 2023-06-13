package br.com.moneyflowauthorizer.repository;

import br.com.moneyflowauthorizer.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    Optional<CardEntity> findCardByNumberCard(String numberCard);

    Optional<CardEntity> findByName(String name);


}
