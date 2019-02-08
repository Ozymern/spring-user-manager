package com.ozymern.spring.user.manager.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Random;

import org.springframework.stereotype.Component;
import com.google.common.hash.Hashing;


@Component
public class Encrypt {

    public String getEncrypt(String base) {

        Instant instant = Instant.now();

        long timeStampMillis = instant.toEpochMilli();

        Random aleatorio = new Random(timeStampMillis);


        String originalString = base + timeStampMillis + aleatorio.nextLong();

        String sha256hex = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();

        sha256hex = sha256hex.substring(0, 32);

        return sha256hex;

    }
}
