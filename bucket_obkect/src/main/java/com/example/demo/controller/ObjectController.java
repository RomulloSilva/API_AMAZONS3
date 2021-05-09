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
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.demo.domain.entity.BucketObject;
import com.example.demo.domain.entity.Documento;
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
	@GetMapping("/uri")
	public URI PegaObjeto( @RequestParam String key ) throws URISyntaxException {
		return bucketService.obj(key);
	}
	@GetMapping("/objeto")
	public String pegarObjetoPeloNome(@RequestParam String key) {return bucketService.pegarObjetoPeloId(key); }
	@GetMapping("/sumario")
	public List<S3ObjectSummary> sumario(@RequestParam String key){return bucketService.listarObjetos2(key);}
	@GetMapping("/protocolo")
	public List<Documento> objetoUnicoo(@RequestParam String codDiretorio) {return bucketService.PegaObjetosDoDiretorio(codDiretorio);}
	@GetMapping("/type")
	public String tipoDoObjeto(@RequestParam String key) {return bucketService.metadata(key);}
	@GetMapping("/users")
	public List<Grant> usuariosDoObjeto(@RequestParam String key){return bucketService.aCL(key);}
}
