package com.example.lunchlist;
/*Estos datos los hemos creado asi, para manejarlos por medio de un objeto de esta clase 
 * de lo contrario tendriamos que manejarlo con arreglos y seria algo muy complicado
 * inseguro por memoria y en  sintesis UN DESMADRE en tanto aca solo llamo a mi objeto 
 * para manejar todas estas variables con seters y getters lets see*/
public class Restaurant 
{
	private String name;
	private String address;
	private String phone;
	private String type;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String toString() {
		return(getName());
		}
	//este metodo fue el ultimo en instalarse
}
