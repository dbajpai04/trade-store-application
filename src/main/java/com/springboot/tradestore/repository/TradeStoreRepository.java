package com.springboot.tradestore.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.tradestore.Model.TradeStore;

@Repository
public interface TradeStoreRepository extends CrudRepository<TradeStore, Long>{

	
	@Modifying
	@Transactional
	@Query("update TradeStore t set t.expired='Y' where t.maturityDate < :currentDate")
	public void updateExpireFlag(Date currentDate);

}
