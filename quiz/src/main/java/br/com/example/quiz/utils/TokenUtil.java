package br.com.example.quiz.utils;

import br.com.example.quiz.config.TokenHolder;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenUtil {

  private static JSONObject getAuthenticatedToken() {
    String token = TokenHolder.getToken();

    if(token == null || token.isEmpty()) {
      throw new IllegalStateException("No token found in TokenHolder");
    }

    String[] parts = token.split("\\.");

    if(parts.length != 3) {
      throw new IllegalStateException("Invalid JWT token");
    }

    String payload = parts[1];
    String decodedPayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);

    return new JSONObject(decodedPayload);
  }

  public static String getAttributeFromToken(String attribute) {
    String tokenAttribute = "";
    try {
      JSONObject tokenJson = getAuthenticatedToken();

      switch (attribute) {
        case "id":
          tokenAttribute = tokenJson.getString(attribute);
          break;
      }
    } catch (Exception ex) {
      throw ex;
    }
    return tokenAttribute;
  }
}
