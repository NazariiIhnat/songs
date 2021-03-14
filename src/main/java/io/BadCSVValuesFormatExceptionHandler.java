package io;

import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvException;

public class BadCSVValuesFormatExceptionHandler implements CsvExceptionHandler {
    @Override
    public CsvException handleException(CsvException e) throws CsvException {
        String[] values = e.getLine();
        String[] valuesWithNull = new String[5];
        System.arraycopy(values, 0, valuesWithNull, 0, values.length);
        e.setLine(valuesWithNull);
        e.fillInStackTrace();
        return e;
    }
}
