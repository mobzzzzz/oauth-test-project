package sparta.nbcamp.oauthtestproject.oauth.naver

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthNaverResponse(
    @JsonProperty("resultcode")
    val resultCode: String,

    @JsonProperty("message")
    val message: String,

    @JsonProperty("response")
    val userResponse: OAuthNaverUserResponse
)