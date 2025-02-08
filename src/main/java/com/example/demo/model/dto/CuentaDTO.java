package com.example.demo.model.dto;

import java.io.Serializable;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Cuenta;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
public class CuentaDTO implements Serializable {

	private Long id;

	private String banco;
	private String sucursal;
	private String dc;
	private String numeroCuenta;
	private float saldoActual;
	@ToString.Exclude
	@JsonBackReference
	private ClienteDTO clienteDTO;

	public static CuentaDTO convertToDTO(Cuenta cuenta, ClienteDTO cliente) {
		CuentaDTO cuentaDTO = new CuentaDTO();
		cuentaDTO.setId(cuenta.getId());
		cuentaDTO.setBanco(cuenta.getBanco());
		cuentaDTO.setSucursal(cuenta.getSucursal());
		cuentaDTO.setDc(cuenta.getDc());
		cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
		cuentaDTO.setSaldoActual(cuenta.getSaldoActual());
		cuentaDTO.setClienteDTO(cliente);
		return cuentaDTO;
	}

	public static Cuenta convertToEntity(CuentaDTO cuentaDTO) {
		Cuenta cuenta = new Cuenta();
		cuenta.setId(cuentaDTO.getId());
		cuenta.setBanco(cuentaDTO.getBanco());
		cuenta.setSucursal(cuentaDTO.getSucursal());
		cuenta.setDc(cuentaDTO.getDc());
		cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
		cuenta.setSaldoActual(cuentaDTO.getSaldoActual());
		
//		Con CustomCuentaRepository
//		Esta linea produce un error de referencia ciruclar, por lo que no mapeamos el cliente a entidad hasta que haga falta
//		
//		Cliente cliente = ClienteDTO.convertToEntity(cuentaDTO.getClienteDTO());
//		cuenta.setCliente(cliente);
		
		
		return cuenta;
	}
}
