package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.domain.entity.Documento;
import com.example.demo.service.BucketService;


@RunWith(SpringRunner.class)
@SpringBootTest
class BucketObkectApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
	@Autowired
	private BucketService bucketService;
	
	
	@Test
	public void Buscar() {
		
		List<Documento> documento = bucketService.PegaObjetosDoDiretorio("10368");
		assertThat(documento.get(1).getBucketName()).isEqualTo("Bucket_name");
	}

}
