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
    	
        //Below records would not be processed as maturity date(given in the problem statement) < current date
        
        tradeRepo.save(new Trade("T1",1,"CP-1","B1",df.parse("20/05/2020"),currentDate,"N"));
        tradeRepo.save(new Trade("T2",2,"CP-2","B1",df.parse("20/05/2021"),currentDate,"N"));
        tradeRepo.save(new Trade("T2",1,"CP-1","B1",df.parse("20/05/2021"),df.parse("14/03/2015"),"N"));
        tradeRepo.save(new Trade("T3",3,"CP-3","B2",df.parse("20/05/2014"),currentDate,"Y"));
       
        // Inserting some valid records to process, as for above records maturity date<current date and as per condition-2 they shouldn't be processed.
        
        // T4 has two versions, Version 2 came first hence version 1 will not be processed.
        tradeRepo.save(new Trade("T4",2,"CP-1","B1",df.parse("20/05/2022"),currentDate,"N"));
        tradeRepo.save(new Trade("T4",1,"CP-1","B1",df.parse("20/05/2022"),df.parse("14/03/2015"),"N"));
        
        tradeRepo.save(new Trade("T5",1,"CP-2","B1",df.parse("20/05/2024"),currentDate,"N"));
        tradeRepo.save(new Trade("T7",1,"CP-3","B2",df.parse("20/05/2012"),currentDate,"Y"));
        
        
    }
}