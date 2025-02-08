package com.example.demo.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.service.ClienteService;

@Controller
public class ClienteController {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	//List the Clients
	@GetMapping("/clientes")
	public ModelAndView findAll() {
		
		log.info("ClienteController- findAll : Mostramos todos los clientes");
		
		ModelAndView mav = new ModelAndView("clientes");
		List<ClienteDTO> listaClientesDTO = clienteService.findAll();
		mav.addObject("listaClientesDTO", listaClientesDTO);
		
		return mav;
		

	}
	
	//Display customer information
	@GetMapping("/clientes/{idCliente}")
	public ModelAndView findById(@PathVariable("idCliente") Long idCliente) {
		
		log.info("ClienteController- findById : Mostramos la información del cliente: " +idCliente);
		
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		//the service layer has to search for the client
		clienteDTO = clienteService.findById(clienteDTO);
		
		ModelAndView mav = new ModelAndView("clienteshow");
		mav.addObject("clienteDTO", clienteDTO);
		
		return mav;
	}
	
	//Customer registration
	@GetMapping("/clientes/add")
	public ModelAndView add() {
		log.info("ClienteController- add : añadimos un nuevo cliente");
		
		ModelAndView mav = new ModelAndView ("clienteform");
		mav.addObject("clienteDTO", new ClienteDTO());
		mav.addObject("add", true);
		
		return mav;
	}
	
	//Saving clients
	@PostMapping("/clientes/save")
	public ModelAndView save(@ModelAttribute("clienteDTO") ClienteDTO clienteDTO) {
		//log.info(ClienteController.class.getSimpleName())
		log.info("ClienteController- save : salvamos los datos del cliente: "+ clienteDTO.toString());
		
		//call service layer in order to save the client
		clienteService.save(clienteDTO);
		
		ModelAndView mav = new ModelAndView ("redirect:/clientes"); //call back the view findAll()
		
		return mav;
	}
	
	//Update customer information
	@GetMapping("/clientes/update/{idCliente}")
	public ModelAndView update(@PathVariable("idCliente") Long idCliente) {
		log.info("ClienteController- update : modificamos cliente:" + idCliente);
		
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		//the service layer has to search for the client
		clienteDTO = clienteService.findById(clienteDTO);
		
		ModelAndView mav = new ModelAndView ("clienteform");
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("add", false);
		
		return mav;
	}
	
	//Delete customer
	@GetMapping("/clientes/delete/{idCliente}")
	public ModelAndView delete(@PathVariable("idCliente") Long idCliente) {
		log.info("ClienteController- delete : borramos cliente:" + idCliente);
		
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		//the service layer has to delete for the client
		clienteService.delete(clienteDTO);

		ModelAndView mav = new ModelAndView ("redirect:/clientes");
		
		return mav;
	}

}
