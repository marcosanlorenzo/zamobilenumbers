package it.interlogica.sanlorenzo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.interlogica.sanlorenzo.dto.NumberToCheck;
import it.interlogica.sanlorenzo.exception.EmptyFileException;
import it.interlogica.sanlorenzo.exception.UnsupportedFileException;
import it.interlogica.sanlorenzo.service.CsvService;

@RestController
public class CsvController {

	@Autowired
	CsvService csvService;

	@PostMapping(value = "/api/v1/check-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody ResponseEntity<byte[]> checkCsv(@RequestParam("file") MultipartFile file) {
		try {
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=output.csv")
					.contentType(MediaType.parseMediaType("text/csv")).body(csvService.createOutputCsv(file));
		} catch (IOException e) {
			return ResponseEntity.internalServerError().body("IOException".getBytes());
		} catch (EmptyFileException e) {
			return ResponseEntity.internalServerError().body("EmptyFileException".getBytes());
		} catch (UnsupportedFileException e) {
			return ResponseEntity.internalServerError().body("UnsupportedFileException".getBytes());
		}
	}

	@PostMapping(value = "/api/v1/test-single-number")
	public String test(@RequestBody NumberToCheck numberToCheck) {
		return csvService.testSingleNumber(numberToCheck.getNumberToCheck());

	}

}
