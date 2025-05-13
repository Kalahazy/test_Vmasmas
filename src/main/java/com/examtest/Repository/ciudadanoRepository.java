package com.examtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examtest.Model.ciudadano;

@Repository
public interface ciudadanoRepository extends JpaRepository<ciudadano, Long>{
		
}
