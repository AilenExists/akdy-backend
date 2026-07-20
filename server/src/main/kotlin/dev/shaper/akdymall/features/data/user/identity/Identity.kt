package dev.shaper.akdymall.features.data.user.identity

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import dev.shaper.akdymall.features.common.database.BaseExpireDTO
import kotlinx.datetime.LocalDateTime
import java.util.*

@ExposedMapping(IdentityTable::class)
data class Identity (
    @Id val id: Long,
    @Reference val userId: UUID,
    override val name: String,
    override val age: Short,
    override val gender: UserGender,
    override val zipCode: String?,
    override val roadAddress: String?,
    override val detailAddress: String?,
    @ReadOnly override val createdAt: LocalDateTime,
    @ReadOnly override val updatedAt: LocalDateTime,
    @ReadOnly override val expiresAt: LocalDateTime,
) : BaseExpireDTO, BaseIdentity