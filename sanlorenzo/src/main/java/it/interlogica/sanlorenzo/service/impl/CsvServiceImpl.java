package it.interlogica.sanlorenzo.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import it.interlogica.sanlorenzo.service.CsvService;

@Component
public class CsvServiceImpl implements CsvService{

	private static final String ZA_CODE = "27";

	private byte[] createOutputByteArrayResource(List<CSVRecord> csvRecords) throws IOException {
		try (
	            BufferedWriter writer = Files.newBufferedWriter(Paths.get("./output.csv"));

	            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create().setHeader("id","sms_phone", "status", "notes").build());
	        ) {
			
			csvRecords.stream().forEach(csvRecord -> {
				try {
					prepareOutputRecord(csvRecord, csvPrinter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	            csvPrinter.flush();
	            
	            InputStream in = Files.newInputStream(Paths.get("./output.csv"));
	            
	            return in.readAllBytes();
	        }
	}
	
	private void prepareOutputRecord(CSVRecord csvRecord, CSVPrinter csvPrinter) throws IOException {
		if(checkZAMobileNumberCorrectness(csvRecord.get("sms_phone")))
			csvPrinter.printRecord(csvRecord.get("id"), csvRecord.get("sms_phone"), "OK", null);
		
	}

	private String checkZAMobileNumberCorrectness(String string) {
		if(string.startsWith(ZA_CODE) && string.length() == 11) {
			return string;
		} else if(!string.startsWith(ZA_CODE) && string.length() == 11){
			
			return ZA_CODE + string.substring(2);
		}
		
	}

	@Override
	public byte[] createOutputCsv(MultipartFile file) {
		try {
			if(!file.isEmpty()) {
				List<String> possibleCsvMimeTypes = List.of("text/comma-separated-values",  
					"text/csv",  
					"application/csv", 
					"application/excel", 
					"application/vnd.ms-excel", 
					"application/vnd.msexcel");
				
				if(possibleCsvMimeTypes.stream().filter(mime -> mime.equals(file.getContentType())).findAny().isPresent())
				{
					ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
				    long n = file.getSize();
				    Long fileSizeLong = Long.valueOf(n);
				    int fileSizeInt = fileSizeLong.intValue();
				    byte[] bytes = new byte[fileSizeInt];
				    inputStream.read(bytes, 0, fileSizeInt);
				    CSVFormat format = CSVFormat.Builder.create().setHeader("id","sms_phone").build();
				    CSVParser parser = CSVParser.parse(new String(bytes), format);
				    List<CSVRecord> csvRecords = parser.getRecords();
				    return createOutputByteArrayResource(csvRecords);
				    
				}
				
			}
			}
			catch(IOException e) {
				
			}
					return null;
		
	}
}
