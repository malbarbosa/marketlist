package br.com.marketlist.api.service;

import java.util.List;
import java.util.Optional;

import br.com.marketlist.api.model.Marketlist;

public interface MarketlistService {

	List<Marketlist> findAllByFilter(String name);

	Marketlist create(Marketlist marketlist);

	Optional<Marketlist> delete(Marketlist marketlist);

	Optional<Marketlist>  update(Marketlist marketlist);

}
