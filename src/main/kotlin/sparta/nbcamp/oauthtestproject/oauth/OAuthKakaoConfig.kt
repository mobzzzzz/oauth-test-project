package sparta.nbcamp.oauthtestproject.oauth

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OAuthKakaoConfig {

    @Value("\${kakao.client.id}")
    lateinit var clientId: String

    @Value("\${kakao.redirect.uri}")
    lateinit var redirectUri: String
}