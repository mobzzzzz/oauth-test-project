package sparta.nbcamp.oauthtestproject.user.service

import sparta.nbcamp.oauthtestproject.oauth.naver.OAuthNaverResponse
import sparta.nbcamp.oauthtestproject.user.dto.UserDto

interface UserService {
    fun signUpWithNaver(oauthNaverResponse: OAuthNaverResponse)
    fun signInWithNaver(oauthNaverResponse: OAuthNaverResponse): UserDto
    fun existsByProviderAndProviderId(provider: String, providerId: String): Boolean
}