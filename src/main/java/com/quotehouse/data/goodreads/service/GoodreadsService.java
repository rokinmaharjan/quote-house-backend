package com.quotehouse.data.goodreads.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quotehouse.quote.domain.Quote;

public class GoodreadsService {
	
	private static final Logger logger = LoggerFactory.getLogger(GoodreadsService.class);

	public static List<Quote> scrapePopularQuotes(List<String> urls) throws IOException {
		long startTime = System.currentTimeMillis();

		List<Quote> quotes = new ArrayList<>();
		
		for (String url : urls) {
			logger.info("Scraping quotes from '{}'", url);
			
			Document documet;
			try {
				documet = Jsoup.connect(url).timeout(0).get();
			} catch (IOException e) {
				logger.info("Exception occured while connecting to '{}'. Exception: ", url, e);
				throw e;
			}

			Elements quotesElement = documet.select(".quotes .quote");
			
			for (Element quoteElement : quotesElement) {
				String quoteText = quoteElement.select(".quoteText").text();
				String author = quoteElement.selectFirst(".authorOrTitle").text();
				List<String> tags = quoteElement.select(".quoteFooter a[href*=/quotes/tag/]").eachText();

				String[] quoteArray = quoteText.split("―");

				quoteText = quoteArray[0].replaceAll("”", "").replaceAll("“", "").trim();

				Quote quote = new Quote.QuoteBuilder()
						.quote(quoteText)
						.author(author)
						.tags(new HashSet<>(tags))
						.build();

				quotes.add(quote);
			}
		}

		long endTime = System.currentTimeMillis();
		logger.info("Time taken to scrape quotes from Goodreads : {} ms", endTime - startTime);
		
		return quotes;
	}

	public static void main(String[] args) throws IOException {
		scrapePopularQuotes(Arrays.asList("https://www.goodreads.com/quotes", "https://www.goodreads.com/quotes?page=2"));
	}
}
