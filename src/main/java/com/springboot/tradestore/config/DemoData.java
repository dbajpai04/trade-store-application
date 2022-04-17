package com.springboot.tradestore.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.springboot.tradestore.Model.Trade;
import com.springboot.tradestore.repository.TradeRepository;


@Component
public class DemoData implements CommandLineRunner {

    @Autowired
    private TradeRepository tradeRepo;
    
   
    @Override
    public void run(String...args) throws Exception {
    	
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	Calendar cal = Calendar.getInstance();
        Date currentDate = df.parse(df.format(cal.getTime()));
    	tradeRepo.save(new Trade(1L,"T1",1,"CP-1","B1",df.parse("20/05/2022"),currentDate,"N"));
        tradeRepo.save(new Trade(2L,"T2",2,"CP-2","B1",df.parse("20/05/2022"),currentDate,"N"));
        tradeRepo.save(new Trade(3L,"T2",1,"CP-1","B1",df.parse("20/05/2022"),df.parse("14/03/2015"),"N"));
        tradeRepo.save(new Trade(4L,"T3",3,"CP-3","B2",df.parse("20/05/2014"),currentDate,"Y"));
        tradeRepo.save(new Trade(5L,"T4",1,"CP-1","B1",df.parse("20/05/2022"),currentDate,"N"));
        tradeRepo.save(new Trade(6L,"T5",1,"CP-2","B1",df.parse("20/05/2024"),currentDate,"N"));
        tradeRepo.save(new Trade(7L,"T6",1,"CP-1","B1",df.parse("20/05/2027"),df.parse("14/03/2015"),"N"));
        tradeRepo.save(new Trade(8L,"T7",1,"CP-3","B2",df.parse("20/05/2012"),currentDate,"Y"));
        
        
    }
}