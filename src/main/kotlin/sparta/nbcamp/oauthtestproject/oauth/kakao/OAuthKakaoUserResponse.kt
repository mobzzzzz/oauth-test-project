package sparta.nbcamp.oauthtestproject.oauth.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthKakaoUserResponse(
    @JsonProperty("nickname")
    val nickname: String,

    @JsonProperty("profile_image")
    val profileImageUrl: String
)