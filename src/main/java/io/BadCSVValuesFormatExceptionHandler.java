package io;

import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvException;

public class BadCSVValuesFormatExceptionHandler implements CsvExceptionHandler {
    @Override
    public CsvException handleException(CsvException e){
        String[] values = e.getLine();
        String[] valuesWithNull;
        if(values.length > 5){
            valuesWithNull = new String[5];
            System.arraycopy(values, 0, valuesWithNull, 0, 5);
        } else {
            valuesWithNull = new String[values.length];
            System.arraycopy(values, 0, valuesWithNull, 0, values.length);
        }
        e.setLine(valuesWithNull);
        e.fillInStackTrace();
        return e;
    }
}
