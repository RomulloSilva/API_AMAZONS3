package com.example.demo.domain.entity;

public class BucketObject {
	
	public String nomeDoBucket;
	
	public String nomeDaPasta;
	
	public BucketObject() {}

	public BucketObject(String nomeDoBucket, String nomeDaPasta) {
		super();
		this.nomeDoBucket = nomeDoBucket;
		this.nomeDaPasta = nomeDaPasta;
	}

	public String getNomeDoBucket() {
		return nomeDoBucket;
	}

	public void setNomeDoBucket(String nomeDoBucket) {
		this.nomeDoBucket = nomeDoBucket;
	}

	public String getNomeDaPasta() {
		return nomeDaPasta;
	}

	public void setNomeDaPasta(String nomeDaPasta) {
		this.nomeDaPasta = nomeDaPasta;
	}
	
	
	

}
