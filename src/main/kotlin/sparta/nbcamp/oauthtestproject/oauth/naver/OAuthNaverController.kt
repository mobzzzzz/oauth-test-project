package sparta.nbcamp.oauthtestproject.oauth.naver

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpSession
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.RestTemplate
import java.math.BigInteger
import java.security.SecureRandom

@Controller
class OAuthNaverController(
    val oauthNaverConfig: OAuthNaverConfig
) {

    @GetMapping("/oauth2/naver/login")
    fun getNaverToken(session: HttpSession): String {
        val state = generateState()
        session.setAttribute("state", state)

        val naverLoginUrl = "https://nid.naver.com/oauth2.0/authorize" +
                "?client_id=${oauthNaverConfig.clientId}" +
                "&redirect_uri=${oauthNaverConfig.redirectUri}" +
                // CSRF 공격 방지를 위한 교차 체크용 상태 토큰, https://developers.naver.com/docs/login/web/web.md
                "&state=$state" +
                "&response_type=code"

        return "redirect:$naverLoginUrl"
    }

    @GetMapping("/oauth2/naver/callback")
    @ResponseBody
    fun getNaverRedirect(@RequestParam code: String, session: HttpSession): String {
        val state = session.getAttribute("state") as String

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params["grant_type"] = "authorization_code"
        params["client_id"] = oauthNaverConfig.clientId
        params["client_secret"] = oauthNaverConfig.secret
        params["code"] = code
        params["state"] = state

        val headersForAccessToken = HttpHeaders()
        headersForAccessToken.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val naverTokenRequest = HttpEntity(params, headersForAccessToken)

        val restTemplate = RestTemplate()

        val accessTokenResponse = restTemplate.exchange(
            "https://nid.naver.com/oauth2.0/token",
            HttpMethod.POST,
            naverTokenRequest,
            OAuthNaverToken::class.java
        )

        return accessTokenResponse.toString()
    }

    @GetMapping("/oauth2/naver/user")
    @ResponseBody
    fun getNaverUser(
        @RequestParam accessToken: String,
    ): ResponseEntity<String> {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_FORM_URLENCODED
        header["Authorization"] = "Bearer $accessToken"

        val userRequest = HttpEntity(null, header)

        val restTemplate = RestTemplate()

        val userResponse = restTemplate.exchange(
            "https://openapi.naver.com/v1/nid/me",
            HttpMethod.GET,
            userRequest,
            String::class.java
        )

        val jsonNode = ObjectMapper().readTree(userResponse.body ?: "")

        return ResponseEntity.ok(jsonNode.toString())
    }

    private fun generateState(): String {
        val state = BigInteger(128, SecureRandom()).toString(32)

        return state
    }
}