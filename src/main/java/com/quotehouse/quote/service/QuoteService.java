package com.quotehouse.quote.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.quotehouse.quote.domain.Quote;
import com.quotehouse.quote.repository.QuoteRepository;

@Service
public class QuoteService {
	
	@Autowired
	private QuoteRepository quoteRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
	
	public Quote addQuote(Quote quote) {
		long startTime = System.currentTimeMillis();
		
		quote = quoteRepository.save(quote);
		
		long endTime = System.currentTimeMillis();
		logger.info("Time taken to add quote: {} ms", (endTime - startTime));
		
		return quote;
	}
	
	public List<Quote> getAllQuotesWithPaging(Integer page, Integer size) {
		long startTime = System.currentTimeMillis();
		
		List<Quote> quotes = quoteRepository.findAll(PageRequest.of(page, size)).getContent();
		
		long endTime = System.currentTimeMillis();
		logger.info("Time taken to fetch all quotes: {} ms", (endTime - startTime));
		
		return quotes;
		
	}
	

}
