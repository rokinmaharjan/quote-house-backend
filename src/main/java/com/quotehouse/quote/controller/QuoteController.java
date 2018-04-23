package com.quotehouse.quote.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quotehouse.quote.domain.Quote;
import com.quotehouse.quote.service.QuoteService;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
	
	@Autowired
	private QuoteService quoteService;
	
	@PostMapping("/quote")
	public Map<String, Quote> addQuote(@RequestBody @Valid Quote quote) {
		Map<String, Quote> quoteMap = new HashMap<>();
		quoteMap.put("quote", quoteService.addQuote(quote));
		
		return quoteMap; 
	}
	
	@GetMapping()
	public Map<String, List<Quote>> getAllQuotesWithPaging(@RequestParam(value = "page", defaultValue ="0") Integer page, 
			@RequestParam(value = "size", defaultValue = "25") Integer size) {
		Map<String, List<Quote>> quotesMap = new HashMap<>();
		quotesMap.put("quotes", quoteService.getAllQuotesWithPaging(page, size));
		
		return quotesMap;
	}
	
	@PostMapping("/import/goodreads")
	public List<Quote> importQuotesFromGoodreads(@RequestParam(defaultValue = "1") Integer startPage, @RequestParam(defaultValue = "2") Integer endPage) {
		try {
			return quoteService.importQuotesFromGoodreads(startPage, endPage);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
