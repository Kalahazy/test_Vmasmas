package com.examtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examtest.Model.domicilio;

@Repository
public interface domicilioRepository extends JpaRepository<domicilio, Long> {

}
