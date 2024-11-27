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

	public String send(String urlStr, String sendData, boolean doOutput) throws Exception {
		String result = null;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(ApplicationConst.POST);
			connection.setRequestProperty(ApplicationConst.ContentType, ApplicationConst.ApplicationJSON);
			connection.setConnectTimeout(ApplicationConst.Timeout);
			connection.setReadTimeout(ApplicationConst.Timeout);
			connection.setDoOutput(doOutput);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = sendData.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			if(doOutput) {
				int responseCode = connection.getResponseCode();
				StringBuilder response = new StringBuilder();

				if(responseCode == HttpURLConnection.HTTP_OK) {
					try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
						String responseLine;
						while ((responseLine = br.readLine()) != null) {
							response.append(responseLine);
						}
						result = response.toString();
					}
				} else {
					try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
							String responseLine;
							while ((responseLine = br.readLine()) != null) {
								response.append(responseLine);
							}
							ObjectMapper objectMapper = new ObjectMapper();
							ApiErrorResultVo vo = objectMapper.readValue(response.toString(), ApiErrorResultVo.class);
							if(vo != null && vo.getMsgs()!= null && vo.getMsgs().size() > 0) {
								throw new Exception(vo.getMsgs().get(0));
							} else {
								throw new Exception("Unknown Error");
							}
						}
				}
			}
		} catch (Exception e) {
			System.out.println("통신 오류");
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
		return result;
	}

	public String getJwtToken() throws Exception{
		String result = null;
		try {
			String url = ApplicationConst.Local + ApplicationConst.LoginUrl;
			UserDto user = new UserDto(ApplicationConst.LoginId, ApplicationConst.password);
			ObjectMapper objectMapper = new ObjectMapper();
			String sendData = objectMapper.writeValueAsString(user);
			String response = send(url, sendData, true);
			LoginResultVo vo = objectMapper.readValue(response, LoginResultVo.class);
			result = vo.getToken();
		} catch (Exception e) {
			System.out.println("JWT 토큰 발급 실패!! 경보 발생 시 이벤트 전송이 불가합니다.");
		}
		return result;
	}
}
