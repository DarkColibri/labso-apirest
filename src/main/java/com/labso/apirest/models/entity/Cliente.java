package com.labso.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// NOMBRE
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12")
	@Column(nullable = false)
	private String nombre;

	// APELLIDO
	@NotEmpty(message = "no puede estar vacio")
	private String apellido;

	// EMAIL
	@NotEmpty(message = "no puede estar vacio")
	@Email(message = "no es una dirección de correo bien formada")
	@Column(nullable = false, unique = true)
	private String email;

	// CREATE AT
	@NotNull(message = "no puede estar vacio")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	// FOTO
	private String foto;

	// REGION
	@NotNull(message = "la región no puede ser vacia")
	// Muchos clientes Para una región.
	// Se hace la carga cuando ser llama a getRegion
	@ManyToOne(fetch = FetchType.LAZY)
	// Nombre columna foreign Key. Si se omite, coge el nomnbre del campo" _ "id"
	@JoinColumn(name = "region_id")
	// Ignora campos en el JSON
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Region region;

	// FACTURAS
	@JsonIgnoreProperties(value = { "cliente", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	// MappedBy cliente, pilla el id de cliente.
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Factura> facturas;

	// Inicializamos
	public Cliente() {
		this.facturas = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	private static final long serialVersionUID = 1L;
}
