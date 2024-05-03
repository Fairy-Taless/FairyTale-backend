package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaceDetectApiServiceImpl implements FaceDetectApiService{

    @Value("${face.akool.apikey}")
    private static String apiKey;


    @Override
    public FaceDetectResponseDto getOptFromFaceDetectApi(FaceDetectRequestDto faceDetectRequestDto) throws IOException {


        String landmarkStr = "";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        String requestBodyJson = "{\n" +
                "  \"single_face\": true, \n" +
                "  \"image_url\": \"" + faceDetectRequestDto.getImgURL() + "\"\n" +
                "}";

        RequestBody body = RequestBody.create(mediaType, requestBodyJson);

        Request request = new Request.Builder()
                .url("https://sg3.akool.com/detect")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()){

            if(!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }

            String responseData = response.body().string();

            JSONObject jsonObject = new JSONObject(responseData);

            int errCode = jsonObject.getInt("error_code");
            String errorMsg = jsonObject.getString("error_msg");

            if(errCode!=0 || !errorMsg.equals("SUCCESS")){
                throw new IOException("Error! \n" +
                        "error code : " +
                        errCode + "\n" +
                        "error massage : " +
                        errorMsg + "\n");
            }

            landmarkStr = jsonObject.getString("landmarks_str");
        } catch (IOException e) {

            e.printStackTrace();

        }

        return FaceDetectResponseDto.builder()
                .photoUrl(faceDetectRequestDto.getImgURL())
                .opt(landmarkStr)
                .build();
    }
}
