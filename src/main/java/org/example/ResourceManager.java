package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ResourceManager {

    private static final Map<String, Object> RESOURCE_CACHE = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T loadResource(String path, Function<String, T> creator) {
        return (T) RESOURCE_CACHE.computeIfAbsent(path, p -> {
            try {
                final URL url = ResourceManager.class.getResource(p);

                if (url == null) throw new IllegalArgumentException("Resource not found: " + p);

                return creator.apply(url.toString());
            } catch (Exception e) {
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
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("\033[32m" + "Conversion successful!" + "\033[0m");

                File oggFile = new File(source);

                if (oggFile.delete()) System.out.println("Deleted: " + source);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
