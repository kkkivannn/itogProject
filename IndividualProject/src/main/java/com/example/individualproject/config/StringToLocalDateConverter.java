package com.example.individualproject.config;

import java.time.LocalDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class StringToLocalDateConverter implements Converter<String, LocalDate>{
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }
}
