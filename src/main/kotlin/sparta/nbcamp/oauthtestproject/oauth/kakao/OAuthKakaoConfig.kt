package sparta.nbcamp.oauthtestproject.oauth.kakao

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OAuthKakaoConfig {

    @Value("\${kakao.client.id}")
    lateinit var clientId: String

    @Value("\${kakao.redirect.uri}")
    lateinit var redirectUri: String

    val authorizationCodeUrl = "https://kauth.kakao.com/oauth/authorize"
    val tokenUrl = "https://kauth.kakao.com/oauth/token"
    val profileUrl = "https://kapi.kakao.com/v2/user/me"
}