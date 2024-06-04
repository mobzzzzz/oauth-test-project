package sparta.nbcamp.oauthtestproject.oauth.naver

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthNaverToken(
    @JsonProperty("access_token")
    val accessToken: String?,
    @JsonProperty("token_type")
    val tokenType: String?,
    @JsonProperty("refresh_token")
    val refreshToken: String?,
    @JsonProperty("expires_in")
    val expiresIn: Int,
)