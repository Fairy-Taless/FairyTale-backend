package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaceSwapApiServiceImpl implements FaceSwapApiService{

    @Value("${face.akool.apikey}")
    private String apikey;


    @Value("${face.akool.clientId}")
    private String clientId;

    private static final Logger LOGGER = LogManager.getLogger(FaceSwapApiServiceImpl.class);

    @Override
    @Transactional
    public Map<String, String> getFaceSwapImg(FaceSwapRequestDto.FaceSwapRequest faceSwapRequest) throws IOException{

        Map<String, String> apiResponse = new HashMap<>();
        String customCharacterUrl = "";
        String customCharacterId = "";

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");

        String requestString =  "{\n" +
                "    \"sourceImage\": [\n" +
                "        {\n" +
                "            \"path\": \"" + faceSwapRequest.getSourceImage() + "\",\n" +
                "            \"opts\": \"" + faceSwapRequest.getSourceImage() + "\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"targetImage\": [\n" +
                "        {\n" +
                "            \"path\": \"" + faceSwapRequest.getTargetImage() + "\",\n" +
                "            \"opts\": \"" + faceSwapRequest.getTargetImage() + "\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"face_enhance\": 1" +  ",\n" +
                "    \"modifyImage\": \"" + faceSwapRequest.getModifyImage() + "\",\n" +
                "    \"webhookUrl\": \"" + faceSwapRequest.getWebhookUrl() + "\"\n" +
                "}";

        RequestBody body = RequestBody.create(mediaType, requestString);

        Request request = new Request.Builder()
                .url("https://openapi.akool.com/api/open/v3/faceswap/highquality/specifyimage")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + getToken())
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }

            String responseData = response.body().string();

            JSONObject jsonObject = new JSONObject(responseData);

            int errCode = jsonObject.getInt("code");
            String errorMsg = jsonObject.getString("msg");

            if(errCode!=1000){
                throw new IOException("Error! \n" +
                        "error code : " +
                        errCode + "\n" +
                        "error massage : " +
                        errorMsg + "\n");
            }

            customCharacterId = jsonObject.getJSONObject("data").getString("_id");
            customCharacterUrl = jsonObject.getJSONObject("data").getString("url");

        } catch (IOException e) {
            e.printStackTrace();
        }

        apiResponse.put("customCharacterId", customCharacterId);
        apiResponse.put("customCharacterUrl", customCharacterUrl);

        return apiResponse;
    }

    private String getToken() {
        String accessToken = "";

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("clientId", clientId);
        jsonBody.put("clientSecret", apikey);

        RequestBody body = RequestBody.create(mediaType, jsonBody.toString());

        Request request = new Request.Builder()
                .url("https://openapi.akool.com/api/open/v3/getToken")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseData = response.body().string();

            LOGGER.info("response : {}", responseData);

            JSONObject jsonObject = new JSONObject(responseData);

            int errCode = jsonObject.getInt("code");
            if (errCode != 1000) {
                throw new IOException("Error! \n" +
                        "error code : " +
                        errCode + "\n");
            }

            accessToken = jsonObject.getString("token");
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("access Token from AKOOL : {}", accessToken);
        return accessToken;
    }

}

