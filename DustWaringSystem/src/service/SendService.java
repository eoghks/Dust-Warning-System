package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.constant.ApplicationConst;
import model.constant.RateEnum;
import model.dto.UserDto;
import model.dto.WarningHistoryDto;
import model.vo.ApiErrorResultVo;
import model.vo.LoginResultVo;

public class SendService {
	private static final SendService instance = new SendService();
	private static String jwtToken;

	private SendService() {
		jwtToken = getJwtToken();
	}

	public static SendService getInstance() {
		return instance;
	}

	public static String send(String urlStr, String sendData, boolean doOutput, boolean setJwt) throws Exception {
		String result = null;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(ApplicationConst.POST);
			if(setJwt) {
				connection.setRequestProperty(ApplicationConst.Authorization, ApplicationConst.Bearer + jwtToken);
			}
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
							ObjectMapper mapper = new ObjectMapper();
							ApiErrorResultVo vo = mapper.readValue(response.toString(), ApiErrorResultVo.class);
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
			throw e;
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
		return result;
	}

	private static String getJwtToken(){
		String result = null;
		try {
			String url = ApplicationConst.Local + ApplicationConst.LoginUrl;
			UserDto user = new UserDto(ApplicationConst.LoginId, ApplicationConst.password);
			ObjectMapper mapper = new ObjectMapper();
			String sendData = mapper.writeValueAsString(user);
			String response = send(url, sendData, true, false);
			LoginResultVo vo = mapper.readValue(response, LoginResultVo.class);
			if(vo != null) {
				result = vo.getToken();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("JWT 토큰 발급 실패!! 경보 발생 시 이벤트 전송이 불가합니다.");
		}
		return result;
	}

	public void sendWarningHistory(WarningHistoryDto dto) throws Exception{
		if(jwtToken != null) {
			try {
				String url = ApplicationConst.Local + ApplicationConst.sendWarningHistoryUrl;
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(new JavaTimeModule());
				String sendData = mapper.writeValueAsString(dto);
				String response = send(url, sendData, true, true);
				WarningHistoryDto result = mapper.readValue(response, WarningHistoryDto.class);
				if(result != null) {
					System.out.printf("%-16s %-4s %s 경보 전송 성공\n", dto.getDate(), dto.getDistrictName(), RateEnum.convertKoreanName(dto.getRate()));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("경보 이력 전송 실패");
			}
		}
	}
}
