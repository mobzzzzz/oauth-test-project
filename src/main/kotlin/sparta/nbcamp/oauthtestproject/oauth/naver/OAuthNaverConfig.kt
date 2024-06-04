package sparta.nbcamp.oauthtestproject.oauth.naver

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OAuthNaverConfig {

    @Value("\${naver.client.id}")
    lateinit var clientId: String

    @Value("\${naver.client.secret}")
    lateinit var secret: String

    @Value("\${naver.redirect.uri}")
    lateinit var redirectUri: String
    
    val authorizationCodeUrl = "https://nid.naver.com/oauth2.0/authorize"
    val tokenUrl = "https://nid.naver.com/oauth2.0/token"
    val profileUrl = "https://openapi.naver.com/v1/nid/me"
}