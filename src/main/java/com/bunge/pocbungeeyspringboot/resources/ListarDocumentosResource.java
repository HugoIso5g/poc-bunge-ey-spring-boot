package com.bunge.pocbungeeyspringboot.resources;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunge.pocbungeeyspringboot.domain.dto.DocumentosDTO;

@RestController
@RequestMapping(value="/documentos")
public class ListarDocumentosResource {
	
	@Autowired
	private ResourcePatternResolver resourcePatternResolver;
	
	@GetMapping
	public ResponseEntity<List<DocumentosDTO>> listarDocumentos(){
		
		List<DocumentosDTO> documentos = new ArrayList<DocumentosDTO>();
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("poc-documents");
		String path = url.getPath();
		
		File[] arquivos = new File(path).listFiles();
		
		try {
			for(File arquivo : arquivos) {
				DocumentosDTO dto = new DocumentosDTO();
				dto.setFileName(arquivo.getName());
				dto.setFilePath(arquivo.getAbsolutePath());
				dto.setFileSize(arquivo.getTotalSpace());
				documentos.add(dto);
			}
		}catch( Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(documentos);
	}

}
