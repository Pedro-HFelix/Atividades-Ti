package App;

import java.io.IOException;
import com.google.gson.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TranslatorText {

    private static String key = "4fbd02e538b840d8a36026815466c3cf";
    private static String location = "eastus";

    // Instancia o OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // Esta função realiza uma requisição POST.
    public String Post() throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        @SuppressWarnings("deprecation")
		RequestBody body = RequestBody.create(mediaType,
                "[{\"Text\": \"Driving a Ferrari on the racetrack is an exhilarating experience. The roar of the engine, the precision of the handling, and the sheer luxury of the car make it a dream for any racing enthusiast!\"}]");
        Request request = new Request.Builder()
        		 .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0"
                         + "&to=fr&to=es&to=pt&to=it&to=ro&to=ca")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // Esta função formata o JSON de resposta.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static void main(String[] args) {
        try {
            TranslatorText translateRequest = new TranslatorText();
            String response = translateRequest.Post();
            System.out.println(prettify(response));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
