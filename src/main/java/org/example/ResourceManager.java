package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ResourceManager {

    private static final Logger LOGGER = LogManager.getLogger(ResourceManager.class);

    private static final Map<String, Object> RESOURCE_CACHE = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T loadResource(String path, Function<String, T> creator) {
        return (T) RESOURCE_CACHE.computeIfAbsent(path, p -> {
            try {
                final URL url = ResourceManager.class.getResource(p);

                if (url == null) {
                    LOGGER.error("Resource not found at path: {}", p);
                    throw new IllegalArgumentException("Resource not found: " + p);
                }
                return creator.apply(url.toString());
            } catch (Exception e) {
                LOGGER.error("Failed to load resource from path: {}. Error: {}", p, e.getMessage(), e);
                throw new RuntimeException("Failed to load resource: " + p, e);
            }
        });
    }

    public static void convertOggToMp3(String pokemonName) {
        final String source = Constants.PATH_TO_POKEMON_RESOURCE + pokemonName + "/cry.ogg";
        final String target = Constants.PATH_TO_POKEMON_RESOURCE + pokemonName + "/cry.mp3";

        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", source, "-codec:a", "libmp3lame", "-b:a", "128k", target
        );

        try {
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) { LOGGER.info(line); }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                LOGGER.info("Conversion successful for: {}", pokemonName);

                File oggFile = new File(source);

                if (oggFile.delete()) LOGGER.info("Deleted original OGG file: {}", source);
            }
        } catch (Exception e) {
            LOGGER.error("Error during conversion process for: {}. Error: {}",
                    pokemonName, e.getMessage(), e);
        }
    }
}
