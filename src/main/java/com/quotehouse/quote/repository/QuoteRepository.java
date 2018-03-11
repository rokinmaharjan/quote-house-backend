package com.quotehouse.quote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.quotehouse.quote.domain.Quote;

public interface QuoteRepository extends MongoRepository<Quote, String> {

}
