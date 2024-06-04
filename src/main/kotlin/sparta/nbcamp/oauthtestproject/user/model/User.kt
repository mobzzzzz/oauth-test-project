package sparta.nbcamp.oauthtestproject.user.model

import jakarta.persistence.*
import sparta.nbcamp.oauthtestproject.oauth.kakao.OAuthKakaoResponse
import sparta.nbcamp.oauthtestproject.oauth.naver.OAuthNaverResponse

@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val providerId: String?,

    @Column(unique = true)
    val email: String?,

    @Column
    val password: String,

    @Column
    val nickname: String,

    @Column
    val profileImageUrl: String? = null,

    @Column
    val provider: String?
) {
    companion object {
        fun from(
            oauthNaverResponse: OAuthNaverResponse,
            password: String,
        ): User {
            return User(
                providerId = oauthNaverResponse.userResponse.id,
                email = oauthNaverResponse.userResponse.email,
                password = password,
                nickname = oauthNaverResponse.userResponse.nickname,
                profileImageUrl = oauthNaverResponse.userResponse.profileImageUrl,
                provider = "naver"
            )
        }

        fun from(
            oauthKakaoResponse: OAuthKakaoResponse,
            password: String,
        ): User {
            return User(
                providerId = oauthKakaoResponse.id,
                email = null,
                password = password,
                nickname = oauthKakaoResponse.userResponse.nickname,
                profileImageUrl = oauthKakaoResponse.userResponse.profileImageUrl,
                provider = "kakao"
            )
        }
    }
}