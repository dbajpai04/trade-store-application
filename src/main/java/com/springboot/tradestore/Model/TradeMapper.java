package com.springboot.tradestore.Model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradeMapper {

	   TradeMapper INSTANCE = Mappers.getMapper( TradeMapper.class );   
	   TradeStore modelToEntity(Trade trade);
	

}
