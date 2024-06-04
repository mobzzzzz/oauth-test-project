package sparta.nbcamp.oauthtestproject.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import sparta.nbcamp.oauthtestproject.user.model.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByProviderAndProviderId(provider: String, providerId: String): User?
    fun existsByProviderAndProviderId(provider: String, providerId: String): Boolean
}