package com.bancopichincha.credito.automotriz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

  public static final String DATE_FORMAT = "yyyy-MM-dd";

  public static Date parseToDate(String fechaString) {
    SimpleDateFormat formatoFecha = new SimpleDateFormat(DATE_FORMAT);
    Date fecha = null;

    try {
      fecha = formatoFecha.parse(fechaString);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return fecha;
  }
}
