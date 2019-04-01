package myob.technicaltest.calculator.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ServiceEntity {
	private @Id @GeneratedValue Long id;
	private String endPoint;
	private String className;
	
	public ServiceEntity(String endPoint, String className) {
		this.endPoint = endPoint;
		this.className = className;
	}

}
