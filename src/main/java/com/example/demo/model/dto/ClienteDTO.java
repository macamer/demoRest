package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.ToString;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Cuenta;
import com.example.demo.repository.entity.Recomendacion;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//serialize the class, so it can be sent to web
@Data
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nif;
	private String nombre;
	private String apellidos;
	private String claveSeguridad;
	private String email;
	@ToString.Exclude
	@JsonManagedReference //mostrada la información del atributo en una sola dirección, evitando bucle infinito
	private RecomendacionDTO recomendacionDTO;
	@ToString.Exclude
	@JsonManagedReference
	private List<CuentaDTO> listaCuentasDTO;

//	@ToString.Exclude
//	private List<DireccionDTO> listaDireccionesDTO;

	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaNacimiento;

	// convert a entity to a DTO
	public static ClienteDTO convertToDTO(Cliente cliente) {
		// create new Cliente and assign basic data
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(cliente.getId());
		clienteDTO.setNif(cliente.getNif());
		clienteDTO.setNombre(cliente.getNombre());
		clienteDTO.setApellidos(cliente.getApellidos());
		clienteDTO.setClaveSeguridad(cliente.getClaveSeguridad());
		clienteDTO.setEmail(cliente.getEmail());
		clienteDTO.setFechaNacimiento(cliente.getFechaNacimiento());

		RecomendacionDTO rec = RecomendacionDTO.convertToDTO(cliente.getRecomendacion(), clienteDTO); // convert to DTO
		clienteDTO.setRecomendacionDTO(rec);

		// Cargamos la lista de cuentas, que como es un Hashset hemos de convertir a
		// ArrayList
		List<Cuenta> listaCuentas = new ArrayList<Cuenta>(cliente.getListaCuentas());
		for (int i = 0; i < cliente.getListaCuentas().size(); i++) {
			// CuentaDTO cuentadto =
			// CuentaDTO.convertToDTO(cliente.getListaCuentas().get(i), clienteDTO);
			CuentaDTO cuentadto = CuentaDTO.convertToDTO(listaCuentas.get(i), clienteDTO);
			clienteDTO.getListaCuentasDTO().add(cuentadto);
		}

		return clienteDTO;
	}

	// convert DTO to a entity
	public static Cliente convertToEntity(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		cliente.setNif(clienteDTO.getNif());
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellidos(clienteDTO.getApellidos());
		cliente.setClaveSeguridad(clienteDTO.getClaveSeguridad());
		cliente.setEmail(clienteDTO.getEmail());
		cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());

		Recomendacion rec = RecomendacionDTO.convertToEntity(clienteDTO.getRecomendacionDTO(), cliente);
		cliente.setRecomendacion(rec);

		// Cargamos la lista de cuentas
		for (int i = 0; i < clienteDTO.getListaCuentasDTO().size(); i++) {
			Cuenta cuenta = CuentaDTO.convertToEntity(clienteDTO.getListaCuentasDTO().get(i));
			cliente.getListaCuentas().add(cuenta);
		}

		return cliente;
	}

	// empty constructor
	public ClienteDTO() {
		super();
		this.recomendacionDTO = new RecomendacionDTO();
		this.listaCuentasDTO = new ArrayList<CuentaDTO>();
	}

}
