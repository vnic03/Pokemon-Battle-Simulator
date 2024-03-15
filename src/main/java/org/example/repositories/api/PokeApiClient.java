package org.example.repositories.api;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PokeApiClient {

    public static String fetchData(String pokemonName) {
        final String baseUrl = "https://pokeapi.co/api/v2/pokemon/";
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(baseUrl + pokemonName.toLowerCase());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP Response Code: " + responseCode);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
            return result.toString();
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    public static void createSprites(String data, String pokemonName) {
        JSONObject obj = new JSONObject(data);
        JSONObject sprites = obj.getJSONObject("sprites");

        String back = sprites.getString("back_default");
        String front = sprites.getString("front_default");

        String icon = sprites.getJSONObject("versions")
                .getJSONObject("generation-viii")
                .getJSONObject("icons")
                .getString("front_default");

        saveImage(back, "back", pokemonName);
        saveImage(front, "front", pokemonName);
        saveImage(icon, "icon", pokemonName);
    }

    private static void saveImage(String imageUrl, String imageName, String pokemonName) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            System.out.println("Image URL for " + imageName + " does not exist. . .");
            return;
        }
        try {
            URL url = new URL(imageUrl);
            BufferedImage img = ImageIO.read(url);

            final String directoryPath = "src/main/resources/pokemon/" + pokemonName;
            File directory = new File(directoryPath);

            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    System.out.println("Could not create directory: " + directoryPath);
                    return;
                }
            }
            final String path = directoryPath + "/" + imageName + ".png";
            File file = new File(path);

            ImageIO.write(img, "png", file);

            System.out.println("Saved: " + path);
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
}
