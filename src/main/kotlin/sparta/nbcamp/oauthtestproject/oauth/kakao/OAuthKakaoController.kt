package sparta.nbcamp.oauthtestproject.oauth.kakao

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@Controller
class OAuthKakaoController(
    private val oauthKakaoConfig: OAuthKakaoConfig
) {
    @GetMapping("/oauth2/kakao/login")
    fun getKakaoToken(): String {
        val kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=${oauthKakaoConfig.clientId}" +
                "&redirect_uri=${oauthKakaoConfig.redirectUri}" +
                "&response_type=code"

        return "redirect:$kakaoLoginUrl"
    }

    @GetMapping("/oauth2/kakao/callback")
    @ResponseBody
    fun getKakaoRedirect(code: String): String {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params["grant_type"] = "authorization_code"
        params["client_id"] = oauthKakaoConfig.clientId
        params["redirect_uri"] = oauthKakaoConfig.redirectUri
        params["code"] = code

        val headersForAccessToken = HttpHeaders()
        headersForAccessToken.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val kakaoTokenRequest = HttpEntity(params, headersForAccessToken)

        val restTemplate = RestTemplate()

        val accessTokenResponse = restTemplate.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            OAuthKakaoToken::class.java
        )

        return accessTokenResponse.toString()
    }

    @GetMapping("/oauth2/kakao/user")
    @ResponseBody
    fun getKakaoUser(
        @RequestParam accessToken: String,
    ): ResponseEntity<String> {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_FORM_URLENCODED
        header["Authorization"] = "Bearer $accessToken"

        val userRequest = HttpEntity(null, header)

        val restTemplate = RestTemplate()

        val userResponse = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            userRequest,
            String::class.java
        )

        val jsonNode = ObjectMapper().readTree(userResponse.body ?: "")

        return ResponseEntity.ok(jsonNode.toString())
    }
}