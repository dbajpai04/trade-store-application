package com.springboot.tradestore.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.tradestore.Model.Trade;
import com.springboot.tradestore.Model.TradeMapper;
import com.springboot.tradestore.Model.TradeStore;
import com.springboot.tradestore.repository.TradeRepository;
import com.springboot.tradestore.repository.TradeStoreRepository;


@RestController
@RequestMapping("/api")
@EnableScheduling
public class TradeController {
	
	@Autowired
	TradeRepository tradeRepo;
	
	@Autowired
	TradeStoreRepository tradeStoreRepo;
	
	@GetMapping("/getAllTrades")
	public List<Trade> getAllTrades(){
		Iterable<Trade> allTrades=tradeRepo.findAll();
		List<Trade> tradeList = new ArrayList<>();
		allTrades.forEach(tradeList :: add);
		return tradeList;
		
	}
	
	
	@GetMapping("/getTradeStore")
	public Set<Trade> getTradeStore() throws Exception{
		
		Set<Trade> output = new HashSet<>();
		Iterable<Trade> allTrades=tradeRepo.findAll();
		List<Trade> tradeList = new ArrayList<>();
		allTrades.forEach(tradeList :: add);
		for(Trade inputTrade: tradeList)
		{
			if(isMaturityDateValid(inputTrade,getCurrentDate())){
				if(output.contains(inputTrade)){
					Optional<Trade> op = output.stream().filter(obj-> obj.getTradeId().equals(inputTrade.getTradeId())).findFirst();
					Trade outputTrade = op.isPresent() ? op.get() : new Trade();
					if(isVersionValid(outputTrade,inputTrade)) {
						output.add(inputTrade);
					}
				}
				output.add(inputTrade);
			}
		}
		
		save(output);
		return output;
	}
	
	public void save(Set<Trade> output) {
		
		Iterator<Trade> it = output.iterator();
	    while(it.hasNext()){
	    	TradeStore ts = TradeMapper.INSTANCE.modelToEntity(it.next());
	    	tradeStoreRepo.save(ts);
	     }
		
	}
	
	public boolean isMaturityDateValid(Trade trade,Date currentDate) {
		
		if(trade.getMaturityDate().compareTo(currentDate)<0) {
			System.out.println("Trade will be rejected as Maturity Date is less than today's date");
			return false;
		}
		return true;
	}
	
	public boolean isVersionValid(Trade outputTrade,Trade inputTrade) {
		if(outputTrade.getVersion()>inputTrade.getVersion()) {
			System.out.println("Version lower than previous received for tradeId :"+outputTrade.getTradeId());
			return false;
		}return true;
	}
	
	@Scheduled(cron="*/60 * * * * *" )
	@GetMapping("/cron")
	public void verifyIfTradeExpired() {
		tradeStoreRepo.updateExpireFlag(getCurrentDate());
	}
	
	
	public Date getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	Calendar cal = Calendar.getInstance();
        Date currentDate;
		try {
			currentDate = df.parse(df.format(cal.getTime()));
		} catch (ParseException e) {
			currentDate = new Date();
		}
    	return currentDate;
	}
	
}
