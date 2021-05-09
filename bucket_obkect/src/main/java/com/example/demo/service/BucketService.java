package com.example.demo.service;

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.util.IOUtils;
import com.example.demo.domain.entity.Documento;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Region;

@Service
public class BucketService {
	
	@Value ("${app.nomeDoBucket}")
	public String nomeDoBucket;
	
	@Value ("${app.nomeDaPastaDoc}")
	public String nomeDaPastaDoc;
	
	@Value("${app.region}")
	public String region;
	
	@Autowired
	AmazonS3 clienteAmazon;
	
	@PostConstruct
	public void teste() {System.out.println(this.nomeDoBucket + " / " + this.nomeDaPastaDoc);}
	
	

	
	//Returns the list of buckets for this amazon client.
	public List<Bucket> ListaTodosBuckets(){
		return clienteAmazon.listBuckets();
	}
	//Returns all objects within a bucket.
	public ObjectListing pegarTodosObjetos() {
		ObjectListing objetos = clienteAmazon.listObjects(this.nomeDoBucket);
		return objetos;
	
	}
	
	//Returns an object according to its Key.
	public String pegarObjetoPeloId( String Key) {
		
		java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 3600000*4;
        expiration.setTime(expTimeMillis);  
		GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(this.nomeDoBucket, Key).withMethod(HttpMethod.GET).withExpiration(expiration);
		URL url = clienteAmazon.generatePresignedUrl(urlRequest);
		return url.toString();
	}
	
	//Returns the URI of the object, giving access denied, to work it must have public access.
	public URI obj(String key) throws URISyntaxException {URL url = clienteAmazon.getUrl(this.nomeDoBucket, key); return url.toURI();}
	
	//Returns the summary of an object.
	public List<S3ObjectSummary> listarObjetos2(String key){
		
		String nomeDobjeto = this.nomeDaPastaDoc+key+"/";
		ListObjectsV2Result result = clienteAmazon.listObjectsV2(this.nomeDoBucket, nomeDobjeto);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		int num = objects.size();
		System.out.println("tamanho do sumário: "+num);
		return objects;
	}
	
	
	//Returns the type of the document within the object.
	public String metadata (String key) {
		String response = clienteAmazon.getObjectMetadata(this.nomeDoBucket, key).getContentType();
		return response;
	}
	
	//Returns the list of who has access to the object.
	public List<Grant> aCL(String key) {
		AccessControlList acl = clienteAmazon.getObjectAcl(this.nomeDoBucket, key);
        List<Grant> grants = acl.getGrantsAsList();
        return grants;
	}	

		//Returning the parameters of documents that are inside folders.
		public List<Documento> PegaObjetosDoDiretorio(String codDiretorio){
			
			//Shape of the object inside the bucket is Bucket_name/arquivos/codDiretorio/object_name
			String nomeDobjeto = this.nomeDaPastaDoc+codDiretorio+"/";
			ListObjectsV2Result result = clienteAmazon.listObjectsV2(this.nomeDoBucket, nomeDobjeto);
			List<S3ObjectSummary> objects = result.getObjectSummaries();
			
			List<Documento> objectList = new ArrayList<Documento>();
			
			String OI = null;
			int num = objects.size();
			System.out.println("tamanho do sumário: "+num);
			
			//Creating 4h of expiration for generated url.
			Date expiration = new Date();
	        long expTimeMillis = expiration.getTime();
	        expTimeMillis += 3600000*4;
	        expiration.setTime(expTimeMillis);

			if (objects.size() > 1) {
				for (int i = 1; i < objects.size(); i ++) {
			        //Generating the URL using the object's keys.
			        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(this.nomeDoBucket, objects.get(i).getKey())
			        										.withMethod(HttpMethod.GET).withExpiration(expiration);
					URL url = clienteAmazon.generatePresignedUrl(urlRequest);
					
					//Returning the type of the document within the object.
					String tipoDoDocumento = clienteAmazon.getObjectMetadata(this.nomeDoBucket, objects.get(i).getKey()).getContentType();
					
					//Cutting the object key to generate the file name.
					String[] nomeDoDocumento = objects.get(i).getKey().split("/");
					
					//Feeding the "Documento" object, with the object's data inside the S3 bucket
					Documento documento = new Documento(objects.get(i).getBucketName(),objects.get(i).getKey(), objects.get(i).getLastModified());
					documento.setUrl(url.toString());
					documento.setType(tipoDoDocumento);
					documento.setId(nomeDoDocumento[2]);
					
					//Logs.
					System.out.println("******** "+documento.getBucketName()+" ******");
					System.out.println("******** "+documento.getKey()+" ******");
					System.out.println("******** "+documento.getId()+" ******");
					System.out.println("******** "+documento.getType()+" ******\n");
					
					objectList.add(documento);
				}			
			}
			//Treatment for invalid codDiretorio.
			else if (objects.size() <= 0){	
				String erro= "Protocolo numero: "+codDiretorio+" não foi encontrado no bucket"+this.nomeDoBucket;
				System.out.println(erro);
				Documento numProtocoloInvalido = new Documento(erro);
				objectList.add(numProtocoloInvalido);
			}
			//Treatment for empty codDiretorio.
			else {
				String erro= "Não ha documentos no protocolo "+codDiretorio;
				System.out.println(erro);
				Documento semDocumento = new Documento(erro);
				objectList.add(semDocumento);
			}
			 return objectList;
		}		
}
