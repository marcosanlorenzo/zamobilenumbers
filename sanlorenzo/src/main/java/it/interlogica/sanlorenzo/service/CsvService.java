package it.interlogica.sanlorenzo.service;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import it.interlogica.sanlorenzo.exception.EmptyFileException;
import it.interlogica.sanlorenzo.exception.UnsupportedFileException;

@Component
public interface CsvService {

	byte[] createOutputCsv(MultipartFile file) throws EmptyFileException, IOException, UnsupportedFileException;

	String testSingleNumber(String numberToCheck);

}
