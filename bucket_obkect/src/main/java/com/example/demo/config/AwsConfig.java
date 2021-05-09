package com.example.demo.config;




import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;





@Configuration
public class AwsConfig {
	/*
	 * Use this code snippet if your credentials are in the application properties.
	@Value ("${app.idDaChave}")
	public String idDaChave;
	
	@Value ("${app.chaveSecreta}")
	public String chaveSecreta;
	
	@Value("${app.region}")
	public String region;
	
	@PostConstruct
	public void teste() {System.out.println("ID da Chave: "+this.idDaChave+" Chave Secreta: "+ this.chaveSecreta+" Região: "+this.region);}
	*/
	
	@Bean
	public AmazonS3 clienteAmazon() {
		
		
		/*
		 * This is the configuration of credentials that are in the application properties.
		 * AWSCredentials credenciais = new BasicAWSCredentials(this.idDaChave, this.chaveSecreta);
		 * 	AmazonS3 clienteS3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider((AWSCredentials) credenciais)).withRegion(this.region).build();
		 */
		
		
		//Use this if your credentials are in the AWS CLI installed on your computer.  
		AmazonS3 clienteS3 = AmazonS3ClientBuilder.defaultClient();
		
		return clienteS3;
	}

	
	
	
}
