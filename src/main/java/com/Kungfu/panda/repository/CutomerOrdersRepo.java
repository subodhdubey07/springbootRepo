package com.Kungfu.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kungfu.panda.entity.CutomerOrders;

@Repository
public interface CutomerOrdersRepo extends JpaRepository<CutomerOrders, Long> {

}

