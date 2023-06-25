package com.bunge.pocbungeeyspringboot.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bunge.pocbungeeyspringboot.domain.dto.DocumentosDTO;
import com.bunge.pocbungeeyspringboot.domain.dto.LogUsuarioDTO;
import com.bunge.pocbungeeyspringboot.services.FileStorageServiceImpl;
import com.bunge.pocbungeeyspringboot.services.LogUsuarioService;
import com.bunge.pocbungeeyspringboot.services.UsuarioService;

@RestController
@RequestMapping(value="/documentos")
public class ListarDocumentosResource {
	
	@Autowired
	private LogUsuarioService service;
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private FileStorageServiceImpl storageService;
	
	@GetMapping("/arquivos-download/{filename:.+}")
	@ResponseBody
  	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		createLogUser(filename);
	    Resource file = storageService.load(filename);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
	
	
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
				dto.setFileSize(humanReadableByteCountSI(arquivo.getAbsoluteFile().length()));
				documentos.add(dto);
			}
		}catch( Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(documentos);
	}
	
	
	public void createLogUser(String file) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LogUsuarioDTO dto = new LogUsuarioDTO();
		dto.setUsuario(userService.findByEmail(authentication.getName()));
		dto.setFileName(file);
		dto.setData(LocalDate.now());
		service.create(dto);
	}
	
	
	public static String humanReadableByteCountSI(long bytes) {
	    if (-1000 < bytes && bytes < 1000) {
	        return bytes + " B";
	    }
	    CharacterIterator ci = new StringCharacterIterator("kMGTPE");
	    while (bytes <= -999_950 || bytes >= 999_950) {
	        bytes /= 1000;
	        ci.next();
	    }
	    return String.format("%.1f %cB", bytes / 1000.0, ci.current());
	}

}
