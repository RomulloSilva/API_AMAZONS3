package com.example.demo.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.util.IOUtils;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;

@Service
public class BucketService {
	
	@Value ("${app.nomeDoBucket}")
	public String nomeDoBucket;
	
	@Value ("${app.nomeDaPastaDoc}")
	public String nomeDaPastaDoc;
	
	@Autowired
	AmazonS3 clienteAmazon;
	
	
	
	
	
	/*
	 * BucketObject bucket = new BucketObject(nomeDoBucket, nomeDaPastaDoc);
	@PostConstruct
	public void teste() {System.out.println(bucket.getNomeDoBucket() + " / " + bucket.getNomeDaPasta());}
	*/
	
	
	
	
	//Retorna a lista de buckets desse cliente amazon.
	public List<Bucket> ListaTodosBuckets(){
		return clienteAmazon.listBuckets();
	}
	
	
	//Retorna 
	public byte[] pegarArquivo(String Key) {
		S3Object obj = clienteAmazon.getObject(this.nomeDoBucket, "/" + Key);
		S3ObjectInputStream stream = obj.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(stream);
			obj.close();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//Retorna todos objetos dentro um bucket
	public ObjectListing pegarTodosObjetos() {
		ObjectListing objetos = clienteAmazon.listObjects(this.nomeDoBucket);
		return objetos;
	}
	
	
	//Retorna um objeto de acordo com o Key do mesmo
	public String pegarObjetoPeloId( String Key) {
		
		java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        
        
		GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(this.nomeDoBucket, Key).withMethod(HttpMethod.GET).withExpiration(expiration);
		
		
		URL url = clienteAmazon.generatePresignedUrl(urlRequest);
		
		return url.toString();
		
	}
	
	
	//Retorna a URI do objeto, dando acesso negado.
	public URI obj(String key) throws URISyntaxException {
		 URL url = clienteAmazon.getUrl(this.nomeDoBucket, key);
		 return url.toURI();
	}
	
	
	//testar
	public S3Object objeetos(String key) {
		S3Object s3 = clienteAmazon.getObject(this.nomeDoBucket, key);
		return s3;
		
	}
}
