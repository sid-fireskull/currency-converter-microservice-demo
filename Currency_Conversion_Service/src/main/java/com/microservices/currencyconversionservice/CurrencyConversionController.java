package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	private final String CURRENCY_CONVERSION_BASE_URL="http://localhost:8110";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrencyExchangeFeign currencyExchangeFeign;
	
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
//		HashMap<String, String> uriVariables = new HashMap<>();
//		uriVariables.put("from",from);
//		uriVariables.put("to",to);
//		
//		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity
//		( CURRENCY_CONVERSION_BASE_URL + "/currency-exchange/from/{from}/to/{to}",
//				CurrencyConversion.class, uriVariables);
//		
//		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		CurrencyConversion currencyConversion = currencyExchangeFeign.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment()+ " " + "rest template");
		
	}
}