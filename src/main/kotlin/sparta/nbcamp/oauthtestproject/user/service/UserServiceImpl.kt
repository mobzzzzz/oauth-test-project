package sparta.nbcamp.oauthtestproject.user.service

import org.springframework.stereotype.Service
import sparta.nbcamp.oauthtestproject.oauth.naver.OAuthNaverResponse
import sparta.nbcamp.oauthtestproject.user.dto.UserDto
import sparta.nbcamp.oauthtestproject.user.model.User
import sparta.nbcamp.oauthtestproject.user.repository.UserRepository

@Service
class UserServiceImpl(
    val userRepository: UserRepository
) : UserService {
    override fun signUpWithNaver(oauthNaverResponse: OAuthNaverResponse) {
        userRepository.save(
            User.from(
                oauthNaverResponse = oauthNaverResponse,
                password = generateRandomPassword(),
            )
        )
    }

    override fun signInWithNaver(oauthNaverResponse: OAuthNaverResponse): UserDto {
        return UserDto.from(
            userRepository.findByProviderAndProviderId("naver", oauthNaverResponse.userResponse.id)
                ?: throw IllegalArgumentException("User not found")
        )
    }

    override fun existsByProviderAndProviderId(provider: String, providerId: String): Boolean {
        return userRepository.existsByProviderAndProviderId(provider, providerId)
    }

    private fun generateRandomPassword(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_+=<>?"
        return (1..16)
            .map { chars.random() }
            .joinToString("")
    }
}