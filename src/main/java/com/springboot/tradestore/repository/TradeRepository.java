package com.springboot.tradestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.tradestore.Model.Trade;


@Repository
public interface TradeRepository extends CrudRepository<Trade, Long>{

}

