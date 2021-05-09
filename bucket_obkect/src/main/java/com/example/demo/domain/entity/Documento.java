package com.example.demo.domain.entity;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class Documento {
	
	private String id;
	private String bucketName;
	private String key;
	private String type;
	private int size;
	private Date lastModified;
	private String storageClass;
	private String owner;
	private String etag;
	private String url;
	
	
	
	public Documento(S3ObjectSummary objects) {}
	
	public Documento(List<S3ObjectSummary> objects) {}
	
	
	
	
	public Documento(String url) {this.url=url;}
	
	public Documento(String key, Date lastModified) {
		super();
		this.key = key;
		this.lastModified = lastModified;
	}

	public Documento(String bucketName, String key, Date lastModified) {
		super();
		this.bucketName = bucketName;
		this.key = key;
		this.lastModified = lastModified;
	}


	public Documento(String bucketName, String key, int size, Date lastModified, String storageClass, String owner,
			String etag) {
		super();
		this.bucketName = bucketName;
		this.key = key;
		this.size = size;
		this.lastModified = lastModified;
		this.storageClass = storageClass;
		this.owner = owner;
		this.etag = etag;
	}
	
	



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getStorageClass() {
		return storageClass;
	}
	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	

}
