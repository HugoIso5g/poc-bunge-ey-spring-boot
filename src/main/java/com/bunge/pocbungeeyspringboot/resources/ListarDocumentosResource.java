package com.bunge.pocbungeeyspringboot.resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunge.pocbungeeyspringboot.domain.dto.DocumentosDTO;
import com.bunge.pocbungeeyspringboot.domain.dto.LogUsuarioDTO;
import com.bunge.pocbungeeyspringboot.services.LogUsuarioService;
import com.bunge.pocbungeeyspringboot.services.UsuarioService;

@RestController
@RequestMapping(value="/documentos")
public class ListarDocumentosResource {
	
	@Autowired
	private ResourcePatternResolver resourcePatternResolver;
	
	@Autowired
	private LogUsuarioService service;
	
	@Autowired
	private UsuarioService userService;
	
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
	
	@GetMapping(value="/download")
	public ResponseEntity<Resource> download(@RequestParam("file") String file) throws IOException{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LogUsuarioDTO dto = new LogUsuarioDTO();
		dto.setUsuario(userService.findByEmail(authentication.getName()));
		dto.setFileName(file);
		dto.setData(LocalDate.now());
		service.create(dto);
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("poc-documents");
		String path = url.getPath();
		
		File download = new File(path + File.separator + file);
		
		 HttpHeaders header = new HttpHeaders();
	        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
	        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        header.add("Pragma", "no-cache");
	        header.add("Expires", "0");
	        
	        Path pathAll = Paths.get(download.getAbsolutePath());
	        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(pathAll));
	        
	        return ResponseEntity.ok()
	                .headers(header)
	                .contentLength(file.length())
	                .contentType(MediaType.parseMediaType("application/octet-stream"))
	                .body(resource);

	}
	
	public static String humanReadableByteCountBin(long bytes) {
	    long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
	    if (absB < 1024) {
	        return bytes + " B";
	    }
	    long value = absB;
	    CharacterIterator ci = new StringCharacterIterator("KMGTPE");
	    for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
	        value >>= 10;
	        ci.next();
	    }
	    value *= Long.signum(bytes);
	    return String.format("%.1f %ciB", value / 1024.0, ci.current());
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
