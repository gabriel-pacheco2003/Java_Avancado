package br.com.trier.springvespertino.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter dtfBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static ZonedDateTime stringToDate(String dateString) {
		return LocalDate.parse(dateString, dtfBR).atStartOfDay(ZoneId.systemDefault());
	}
	
	public static String dateToString(ZonedDateTime date) {
		return dtfBR.format(date);
	}

}
