package sparta.nbcamp.oauthtestproject.user.dto

import sparta.nbcamp.oauthtestproject.user.model.User

data class UserDto(
    val id: Long,
    val email: String,
    val provider: String?,
    val providerId: String?,
    val nickname: String,
    val profileImageUrl: String?
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                id = user.id!!,
                email = user.email,
                provider = user.provider,
                providerId = user.providerId,
                nickname = user.nickname,
                profileImageUrl = user.profileImageUrl
            )
        }
    }
}