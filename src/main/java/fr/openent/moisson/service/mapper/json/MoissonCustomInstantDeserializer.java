package fr.openent.moisson.service.mapper.json;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class MoissonCustomInstantDeserializer extends InstantDeserializer<Instant> {

    public MoissonCustomInstantDeserializer() {
        super(  Instant.class, DateTimeFormatter.ISO_INSTANT,
            Instant::from,
            a -> Instant.ofEpochMilli(a.value),
            a -> Instant.ofEpochSecond(a.integer, a.fraction),
            null,
            true); // yes, replace zero offset with Z
    }
}

