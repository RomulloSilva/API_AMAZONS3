package com.example.demo.config;




import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;





@Configuration
public class AwsConfig {
	
	@Value ("${app.idDaChave}")
	public String idDaChave;
	
	@Value ("${app.chaveSecreta}")
	public String chaveSecreta;
	
	public String region = "SA_EAST_1";
	
	
	
	@PostConstruct
	public void teste() {System.out.println("ID da Chave: "+this.idDaChave+" Chave Secreta: "+ this.chaveSecreta);}
	
	@Bean
	public AmazonS3 clienteAmazon() {
		
		AWSCredentials credenciais = new BasicAWSCredentials(this.idDaChave, this.chaveSecreta);
		
		AmazonS3 clienteS3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credenciais)).withRegion(Regions.SA_EAST_1).build();
		
		return clienteS3;
	}

	
	
	
}
