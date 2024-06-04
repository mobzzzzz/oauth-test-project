package sparta.nbcamp.oauthtestproject.oauth.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthKakaoResponse(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("properties")
    val userResponse: OAuthKakaoUserResponse
)