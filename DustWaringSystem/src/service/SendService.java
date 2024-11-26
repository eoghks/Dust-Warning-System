package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.constant.ApplicationConst;
import model.dto.UserDto;
import model.vo.ApiErrorResultVo;
import model.vo.LoginResultVo;

public class SendService {
	public String getJwtToken() throws Exception{
		HttpURLConnection connection = null;
		UserDto user = new UserDto(ApplicationConst.LoginId, ApplicationConst.password);
		try {
			URL url = new URL(ApplicationConst.Local+ApplicationConst.LoginUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(ApplicationConst.POST);
			connection.setRequestProperty(ApplicationConst.ContentType, ApplicationConst.ApplicationJSON);
			connection.setConnectTimeout(ApplicationConst.Timeout);
			connection.setReadTimeout(ApplicationConst.Timeout);
			connection.setDoOutput(true);

			ObjectMapper objectMapper = new ObjectMapper();
			String sendData = objectMapper.writeValueAsString(user);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = sendData.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			int responseCode = connection.getResponseCode();
			StringBuilder response = new StringBuilder();

			if(responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
						String responseLine;
						while ((responseLine = br.readLine()) != null) {
							response.append(responseLine);
						}
				}
				LoginResultVo result = objectMapper.readValue(response.toString(), LoginResultVo.class);
				return result.getToken();
			} else {
				try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
						String responseLine;
						while ((responseLine = br.readLine()) != null) {
							response.append(responseLine);
						}
				}
				ApiErrorResultVo result = objectMapper.readValue(response.toString(), ApiErrorResultVo.class);
				if(result != null && result.getMsgs()!= null && result.getMsgs().size() > 0) {
					throw new Exception(result.getMsgs().get(0));
				} else {
					throw new Exception("Unknown Error");
				}
			}
		} catch (Exception e) {
			System.out.println("JWT 토큰 발급 실패!! 경보 발생 시 이벤트 전송이 불가합니다.");
			e.printStackTrace();
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
}
