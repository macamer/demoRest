package com.example.demo.repository.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data 
@Entity
@Table(name="clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@Column puede omitirse porque se llama igual
	private Long id;
	
	@Column(name="nif")
	private String nif;
	
	@Column(name="nombre")
	private String nombre;
	
	private String apellidos;
	
	@Column(name="claveseguridad") //este si que hay que indicarlo porque en bbdd es todo minúsculas
	private String claveSeguridad;
	
	private String email;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
	@ToString.Exclude
	private Recomendacion recomendacion;
	
//	Mapeo de la lista de cuentas con List
//	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="cliente")
//	private List<Cuenta> listaCuentas;
	
	//Mapeo de la lista de cuentas con Set
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="cliente")
	@ToString.Exclude
	private Set<Cuenta> listaCuentas;
	
	//Atributo que almacena la fecha de nacimiento
	@Temporal(TemporalType.DATE)
	@Column(name = "fechanacimiento")
	private Date fechaNacimiento;
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public Cliente() {
		super();
		this.recomendacion = new Recomendacion();
//		this.listaCuentas = new ArrayList<Cuenta>();
//		this.listaDirecciones = new ArrayList<Direccion>();
		this.listaCuentas = new HashSet<Cuenta>();
		//this.listaDirecciones = new HashSet<Direccion>();
	}
	
}
