package com.example.demo.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Cliente;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query(value= "SELECT * FROM clientes WHERE apellidos = ?1", nativeQuery=true)
	public List<Cliente> findByApellidos(String apellidos);

}
