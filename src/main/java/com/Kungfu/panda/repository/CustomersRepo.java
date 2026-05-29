package com.Kungfu.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kungfu.panda.entity.Customers;

@Repository
public interface CustomersRepo extends JpaRepository<Customers, Long> {

}
