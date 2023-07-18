package it.interlogica.sanlorenzo.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface CsvService {

	byte[] createOutputCsv(MultipartFile file);

}
