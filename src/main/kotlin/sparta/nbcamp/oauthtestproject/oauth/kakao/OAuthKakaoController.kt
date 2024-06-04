package sparta.nbcamp.oauthtestproject.oauth.kakao

import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.RestTemplate
import sparta.nbcamp.oauthtestproject.user.service.UserService


@Controller
class OAuthKakaoController(
    private val oauthKakaoConfig: OAuthKakaoConfig,

    private val userService: UserService
) {
    @GetMapping("/oauth2/kakao/login")
    fun getKakaoToken(): String {
        val kakaoLoginUrl = oauthKakaoConfig.authorizationCodeUrl +
                "?client_id=${oauthKakaoConfig.clientId}" +
                "&redirect_uri=${oauthKakaoConfig.redirectUri}" +
                "&response_type=code"

        return "redirect:$kakaoLoginUrl"
    }

    @GetMapping("/oauth2/kakao/callback")
    @ResponseBody
    fun getKakaoRedirect(@RequestParam code: String): String {
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
            oauthKakaoConfig.tokenUrl,
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
    ): ResponseEntity<Any> {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_FORM_URLENCODED
        header["Authorization"] = "Bearer $accessToken"

        val userRequest = HttpEntity(null, header)

        val restTemplate = RestTemplate()

        val response = restTemplate.exchange(
            oauthKakaoConfig.profileUrl,
            HttpMethod.GET,
            userRequest,
            OAuthKakaoResponse::class.java
        )

        val id = response.body?.id ?: throw IllegalStateException("User id not found with kakao")

        if (userService.existsByProviderAndProviderId("kakao", id)) {
            userService.signInWithKakao(response.body!!)

            return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully signed up with Kakao")
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                .body(userService.signUpWithKakao(response.body!!))
        }
    }
}