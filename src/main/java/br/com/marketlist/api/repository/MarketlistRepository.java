package br.com.marketlist.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.Marketlist;

@Component
public interface MarketlistRepository extends MongoRepository<Marketlist, String>, QueryByExampleExecutor<Marketlist>{

}
