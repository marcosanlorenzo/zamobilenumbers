package it.interlogica.sanlorenzo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.interlogica.sanlorenzo.service.CsvService;

@RestController
public class CsvController {
	
	@Autowired
	CsvService csvService;

	@PostMapping(value="/check-csv", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	  public @ResponseBody ResponseEntity<byte[]> checkCsv(
			  @RequestParam("file") MultipartFile file ){
				try {
					return ResponseEntity.ok()
							.header("Content-Disposition", "attachment; filename=output.csv")
				            .contentType(MediaType.parseMediaType("text/csv"))
				            .body(csvService.createOutputCsv(file));
				} catch(Exception e) {
					return null;
				}
				
		
	}
	
	@GetMapping(value="/test")
	  public @ResponseBody ResponseEntity<Resource> test() {
		return null;
			  
					
				
		
	}
		
	
}
