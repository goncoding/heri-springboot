package com.doosan.heritage.util;

import lombok.extern.java.Log;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Log
public class DoosanDateUtil {

	public static String dateToStandardDateTimeString(Date date, ZoneId timeZone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(timeZone);
		return formatter.format(date.toInstant());
	}

	public static String dateToStandardDateTimeString(Date date, Long timeZoneMinuteDiff) {
		int hours;
		int minutes;

		if (timeZoneMinuteDiff != null) {
			hours = timeZoneMinuteDiff.intValue() / 60;
			minutes = timeZoneMinuteDiff.intValue() % 60;
		} else {
			hours = 0;
			minutes = 0;
			log.warning("Passed null timeZoneMinuteDiff value while calling dateToStandardDateTimeString.");
		}

		return dateToStandardDateTimeString(date, ZoneOffset.ofHoursMinutes(hours, minutes));
	}

	public static String dateToStandardDateTimeString(Date date) {
		return dateToStandardDateTimeString(date, ZoneId.systemDefault());
	}

	public static String utcDateToStandardDateTimeString(Date date) {
		return dateToStandardDateTimeString(date, ZoneOffset.UTC);
	}

	public static String dateToStandardDateString(Date date, ZoneId timeZone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(timeZone);
		return formatter.format(date.toInstant());
	}

	public static String dateToStandardDateString(Date date, Long timeZoneMinuteDiff) {
		int hours;
		int minutes;

		if (timeZoneMinuteDiff != null) {
			hours = timeZoneMinuteDiff.intValue() / 60;
			minutes = timeZoneMinuteDiff.intValue() % 60;
		} else {
			hours = 0;
			minutes = 0;
			log.warning("Passed null timeZoneMinuteDiff value while calling dateToStandardDateString.");
		}

		return dateToStandardDateString(date, ZoneOffset.ofHoursMinutes(hours, minutes));
	}

	public static String dateToStandardDateString(Date date) {
		return dateToStandardDateString(date, ZoneId.systemDefault());
	}

	public static String utcDateToStandardDateString(Date date) {
		return dateToStandardDateString(date, ZoneOffset.UTC);
	}

	public static String dateToStandardTimeString(Date date, ZoneId timeZone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(timeZone);
		return formatter.format(date.toInstant());
	}

	public static String dateToStandardTimeString(Date date, Long timeZoneMinuteDiff) {
		int hours;
		int minutes;

		if (timeZoneMinuteDiff != null) {
			hours = timeZoneMinuteDiff.intValue() / 60;
			minutes = timeZoneMinuteDiff.intValue() % 60;
		} else {
			hours = 0;
			minutes = 0;
			log.warning("Passed null timeZoneMinuteDiff value while calling dateToStandardDateTimeString.");
		}

		return dateToStandardTimeString(date, ZoneOffset.ofHoursMinutes(hours, minutes));
	}

	public static String dateToStandardTimeString(Date date) {
		return dateToStandardTimeString(date, ZoneId.systemDefault());
	}

	public static String utcDateToStandardTimeString(Date date) {
		return dateToStandardTimeString(date, ZoneOffset.UTC);
	}

	public static String dateToStandardDateTimeWithMillisecondString(Date date, ZoneId timeZone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(timeZone);
		return formatter.format(date.toInstant());
	}

	public static String dateToStandardDateTimeWithMillisecondString(Date date, Long timeZoneMinuteDiff) {
		int hours;
		int minutes;

		if (timeZoneMinuteDiff != null) {
			hours = timeZoneMinuteDiff.intValue() / 60;
			minutes = timeZoneMinuteDiff.intValue() % 60;
		} else {
			hours = 0;
			minutes = 0;
			log.warning("Passed null timeZoneMinuteDiff value while calling dateToStandardDateTimeString.");
		}

		return dateToStandardDateTimeWithMillisecondString(date, ZoneOffset.ofHoursMinutes(hours, minutes));
	}

	public static String dateToStandardDateTimeWithMillisecondString(Date date) {
		return dateToStandardDateTimeWithMillisecondString(date, ZoneId.systemDefault());
	}

	public static String utcDateToStandardDateTimeWithMillisecondString(Date date) {
		return dateToStandardDateTimeWithMillisecondString(date, ZoneOffset.UTC);
	}

	public static String dateToStandardDateTimeStringWithoutDash(Date date, ZoneId timeZone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(timeZone);
		return formatter.format(date.toInstant());
	}

	public static String dateToStandardDateTimeStringWithoutDash(Date date, Long timeZoneMinuteDiff) {
		int hours;
		int minutes;

		if (timeZoneMinuteDiff != null) {
			hours = timeZoneMinuteDiff.intValue() / 60;
			minutes = timeZoneMinuteDiff.intValue() % 60;
		} else {
			hours = 0;
			minutes = 0;
			log.warning("Passed null timeZoneMinuteDiff value while calling dateToStandardDateTimeString.");
		}

		return dateToStandardDateTimeStringWithoutDash(date, ZoneOffset.ofHoursMinutes(hours, minutes));
	}

	public static String dateToStandardDateTimeStringWithoutDash(Date date) {
		return dateToStandardDateTimeStringWithoutDash(date, ZoneId.systemDefault());
	}

	public static String utcDateToStandardDateTimeStringWithoutDash(Date date) {
		return dateToStandardDateTimeStringWithoutDash(date, ZoneOffset.UTC);
	}

	public static String dateToStandardDateStringWithoutDash(Date date, ZoneId timeZone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(timeZone);
		return formatter.format(date.toInstant());
	}

	public static String dateToStandardDateStringWithoutDash(Date date, Long timeZoneMinuteDiff) {
		int hours;
		int minutes;

		if (timeZoneMinuteDiff != null) {
			hours = timeZoneMinuteDiff.intValue() / 60;
			minutes = timeZoneMinuteDiff.intValue() % 60;
		} else {
			hours = 0;
			minutes = 0;
			log.warning("Passed null timeZoneMinuteDiff value while calling dateToStandardDateTimeString.");
		}

		return dateToStandardDateStringWithoutDash(date, ZoneOffset.ofHoursMinutes(hours, minutes));
	}

	public static String dateToStandardDateStringWithoutDash(Date date) {
		return dateToStandardDateStringWithoutDash(date, ZoneId.systemDefault());
	}

	public static String utcDateToStandardDateStringWithoutDash(Date date) {
		return dateToStandardDateStringWithoutDash(date, ZoneOffset.UTC);
	}

	public static String dateToStandardTimeStringWithoutDash(Date date, ZoneId timeZone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss").withZone(timeZone);
		return formatter.format(date.toInstant());
	}

	public static String dateToStandardTimeStringWithoutDash(Date date, Long timeZoneMinuteDiff) {
		int hours;
		int minutes;

		if (timeZoneMinuteDiff != null) {
			hours = timeZoneMinuteDiff.intValue() / 60;
			minutes = timeZoneMinuteDiff.intValue() % 60;
		} else {
			hours = 0;
			minutes = 0;
			log.warning("Passed null timeZoneMinuteDiff value while calling dateToStandardDateTimeString.");
		}

		return dateToStandardTimeStringWithoutDash(date, ZoneOffset.ofHoursMinutes(hours, minutes));
	}

	public static String dateToStandardTimeStringWithoutDash(Date date) {
		return dateToStandardTimeStringWithoutDash(date, ZoneId.systemDefault());
	}

	public static String utcDateToStandardTimeStringWithoutDash(Date date) {
		return dateToStandardTimeStringWithoutDash(date, ZoneOffset.UTC);
	}

	private DoosanDateUtil() {
	}
}
