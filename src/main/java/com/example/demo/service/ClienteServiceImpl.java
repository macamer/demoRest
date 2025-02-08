package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteRepository clienteRepository;
	

	@Override 
	public List<ClienteDTO> findAll() {
		
		log.info("ClienteServiceImpl - findAll: Lista de todos los clientes");
		
		List<ClienteDTO> listaClientesDTO = clienteRepository.findAll()
				.stream()
				.map(p->ClienteDTO.convertToDTO(p))
				.collect(Collectors.toList());
		
		return listaClientesDTO;		
	}

	@Override
	public ClienteDTO findById(ClienteDTO clienteDTO) {
		log.info("ClienteServiceImpl - findById: Buscar Cliente por ID: " + clienteDTO.getId());
		
		Optional<Cliente> cliente = clienteRepository.findById(clienteDTO.getId());
		//El Optional es para evitar el nullpointer. Entonces, opcionalmente devuelve un cliente.
		
		if(cliente.isPresent()) {
			clienteDTO = ClienteDTO.convertToDTO(cliente.get());
			return clienteDTO;
		} else {
			return null;
		}
	}

//	@Override
//	public void save(ClienteDTO clienteDTO) {
//		log.info("ClienteServiceImpl - save: salvamos el cliente: " + clienteDTO.toString());
//		
//		Cliente cliente = clienteDTO.convertToEntity(clienteDTO);
//		clienteRepository.save(cliente);		
//	}
	
	@Override
	public ClienteDTO save(ClienteDTO clienteDTO) {
		log.info("ClienteServiceImpl - save: salvamos el cliente: " + clienteDTO.toString());
		
		Cliente cliente = clienteDTO.convertToEntity(clienteDTO);
		clienteRepository.save(cliente);
		
		return clienteDTO;
	}

	@Override
	public void delete(ClienteDTO clienteDTO) {
		log.info("ClienteServiceImpl - delete: borramos el cliente: " + clienteDTO.getId());
		
//		Cliente cliente = new Cliente();
//		cliente.setId(clienteDTO.getId());
//		clienteRepository.delete(cliente);	
		clienteRepository.deleteById(clienteDTO.getId());
	}

	@Override
	public List<ClienteDTO> findByApellidos(String apellidos) {
log.info("ClienteServiceImpl - findAll: Lista de todos los clientes");
		
		List<ClienteDTO> listaClientesDTO = clienteRepository.findByApellidos(apellidos)
				.stream()
				.map(p->ClienteDTO.convertToDTO(p))
				.collect(Collectors.toList());
		
		return listaClientesDTO;	
	}

}
