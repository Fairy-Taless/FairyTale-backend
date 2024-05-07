package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.exception.FaceNotDetectException;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaceDetectApiServiceImpl implements FaceDetectApiService{

    private static final Logger LOGGER = LogManager.getLogger(FaceDetectApiServiceImpl.class);

    @Value("${face.akool.apikey}")
    private static String apiKey;


    @Override
    public FaceDetectResponseDto getOptFromFaceDetectApi(FaceDetectRequestDto faceDetectRequestDto) {


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
                LOGGER.error("외부 API 요청에 실패했습니다.");
                throw new GeneralException(ErrorStatus._REQUEST_FAIL_ERROR);
            }

            String responseData = response.body().string();

            JSONObject jsonObject = new JSONObject(responseData);

            int errCode = jsonObject.getInt("error_code");
            String errorMsg = jsonObject.getString("error_msg");

            if(errCode!=0 || !errorMsg.equals("SUCCESS")){
                throw new FaceNotDetectException(ErrorStatus._FACE_NOT_DETECT_ERROR);
            }

            landmarkStr = jsonObject.getString("landmarks_str");
        } catch (IOException e) {
            LOGGER.error("외부 API 요청에 실패했습니다.");
            e.printStackTrace();
            throw new GeneralException(ErrorStatus._REQUEST_FAIL_ERROR);

        }

        return FaceDetectResponseDto.builder()
                .photoUrl(faceDetectRequestDto.getImgURL())
                .opt(landmarkStr)
                .build();
    }
}
