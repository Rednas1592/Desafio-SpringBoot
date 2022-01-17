package br.com.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.desafio.model.CustomerOrder;
import br.com.desafio.model.StatusOrder;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long>{

	List<CustomerOrder> findByStatus(StatusOrder status);
	List<CustomerOrder> findByTotal(double total);
	
	@Query(value = "SELECT MAX(TOTAL) FROM CUSTOMER_ORDER", nativeQuery = true)
	double findMaxTotal();
	@Query(value = "SELECT MIN(TOTAL) FROM CUSTOMER_ORDER", nativeQuery = true)
	double findMinTotal();
	
	@Query(value = "SELECT * FROM CUSTOMER_ORDER WHERE LOWER(NAME) LIKE %?1% OR LOWER(DESCRIPTION) like %?1%", nativeQuery = true)
	List<CustomerOrder> findValue(String q);
	
	@Query(value = "SELECT * FROM CUSTOMER_ORDER WHERE (?1 IS NULL OR (LOWER(NAME) LIKE %?1% "
			+ "OR LOWER(DESCRIPTION) LIKE %?1%))"
			+ " AND (?2 IS NULL OR TOTAL >= ?2)"
			+ " AND (?3 IS NULL OR TOTAL <= ?3)"
			+ " AND (?4 IS NULL OR LOWER(STATUS) = ?4)", nativeQuery = true)
	List<CustomerOrder> findByFilter(String q, Double minTotal, Double maxTotal, String status);
}
