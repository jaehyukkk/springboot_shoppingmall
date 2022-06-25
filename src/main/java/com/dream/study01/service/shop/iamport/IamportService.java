package com.dream.study01.service.shop.iamport;

import com.dream.study01.dto.shop.order.PaymentCancelResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Log4j2
@Service
public class IamportService {
    @Value("${imp_key}")
    private String impKey;

    @Value("${imp_secret}")
    private String impSecret;

    @ToString
    @Getter
    private static class Response {
        private PaymentInfo response;
    }

    @ToString
    @Getter
    private static class PaymentInfo {
        private int amount;
    }

    //토큰 받아오기
    public String getToken() throws IOException{

        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();

        json.addProperty("imp_key", impKey);
        json.addProperty("imp_secret", impSecret);

        //아임포트 서버랑 통신대기
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        //아임포트 서버에 키값과 시크릿키값을 보냄
        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

        String token = gson.fromJson(response,Map.class).get("access_token").toString();

        br.close();
        conn.disconnect();

        return token;
    }

    //상품의 정보 받아오기 ( 가격만 받아왔음 )
    public int paymentInfo(String imp_uid, String access_token) throws IOException {
        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);
        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", access_token);
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

        Gson gson = new Gson();

        Response response = gson.fromJson(br.readLine(), Response.class);
        br.close();
        conn.disconnect();

        return response.getResponse().getAmount();
    }

    //결제 취소
    public void paymentCancel(PaymentCancelResponseDto paymentCancelResponseDto) throws IOException{
        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/cancel");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", paymentCancelResponseDto.getAccess_token());

        conn.setDoOutput(true);

        JsonObject json = new JsonObject();

        json.addProperty("reason", paymentCancelResponseDto.getReason());
        json.addProperty("imp_uid", paymentCancelResponseDto.getImp_uid());
        json.addProperty("amount", paymentCancelResponseDto.getAmount());
        json.addProperty("checksum", paymentCancelResponseDto.getAmount());

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

        br.close();
        conn.disconnect();
    }



}
