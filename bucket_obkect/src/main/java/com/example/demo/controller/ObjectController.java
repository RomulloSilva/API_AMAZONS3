package com.example.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.example.demo.domain.entity.BucketObject;
import com.example.demo.service.BucketService;

@RestController
@RequestMapping("consultadocumentos/sinistros")
public class ObjectController {
	
	@Autowired
	private BucketService bucketService;
	
	@Value("${app.ambiente}")
	public String abiente;
	
	
	
	@GetMapping("/healthcheck")
	public ResponseEntity<String> HelthCheck() {
		return ResponseEntity.ok(abiente);
	}
	
	@GetMapping("/buckets")
	public List<Bucket> ListarBuckets(){
		return bucketService.ListaTodosBuckets();
	}
	
	
	@GetMapping("/objetos")
	public ObjectListing ListaObjetosDoBucket() {
		return bucketService.pegarTodosObjetos();
	}
	
	
	@GetMapping("/teste")
	public URI PegaObjeto( @RequestParam String key ) throws URISyntaxException {
		return bucketService.obj(key);
	}
	
	@GetMapping("/objeto")
	public String pegarObjetoPeloNome(@RequestParam String key) {return bucketService.pegarObjetoPeloId(key); }
	
	
	@GetMapping("/testes")
	public com.amazonaws.services.s3.model.S3Object objetoUnico(@RequestParam String Key) {return bucketService.objeetos(Key);}
	

}
