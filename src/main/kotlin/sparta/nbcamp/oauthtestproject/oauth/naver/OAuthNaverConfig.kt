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
}