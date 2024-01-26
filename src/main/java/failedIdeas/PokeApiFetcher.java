package failedIdeas;

/*

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PokeApiFetcher {

    private static final String Base_URL =  "https://pokeapi.co/api/v2/pokemon/";

    public static String fetchPokemonData(String PokemonName){

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(Base_URL + PokemonName.toLowerCase());
            HttpResponse response = httpClient.execute(request);

            return EntityUtils.toString(response.getEntity());
        }
        catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

 */

