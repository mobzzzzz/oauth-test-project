package sparta.nbcamp.oauthtestproject.oauth.naver

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthNaverUserResponse(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("nickname")
    val nickname: String,

    @JsonProperty("profile_image")
    val profileImageUrl: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("name")
    val name: String
)