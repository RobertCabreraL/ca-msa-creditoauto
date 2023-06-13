package com.bancopichincha.credito.automotriz.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

public class CsvReaderUtil {
  public static List<String[]> loadCsvData(String csvFilePath)
      throws IOException, CsvValidationException {
    return loadCsvData(csvFilePath, false);
  }

  public static List<String[]> loadCsvData(String csvFilePath, boolean skipHeader)
      throws IOException, CsvValidationException {
    ClassPathResource resource = new ClassPathResource(csvFilePath);
    FileReader fileReader = new FileReader(resource.getFile());

    CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
    CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).build();

    List<String[]> data = new ArrayList<>();
    String[] nextLine;

    if (skipHeader) {
      reader.readNext();
    }

    while ((nextLine = reader.readNext()) != null) {
      data.add(nextLine);
    }

    reader.close();
    fileReader.close();

    return data;
  }
}
