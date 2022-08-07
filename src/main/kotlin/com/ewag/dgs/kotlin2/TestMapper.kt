package com.ewag.dgs.kotlin2

import com.ewag.dgs.kotlin2.dgs.generated.types.ExampleType
import com.ewag.dgs.kotlin2.dgs.generated.types.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Qualifier
import org.mapstruct.factory.Mappers

@Mapper(uses = [ExternalIdMapper::class])
abstract class TestMapper {

    @Mapping(target = "isNice", ignore = true)
    @Mapping(source = "userId", target = "user", qualifiedBy = [UserIdToUser::class])
    abstract fun mapToDto(source: TestEntity): ExampleType
}

@Qualifier
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class UserIdToUser

@Mapper
abstract class ExternalIdMapper {
    @UserIdToUser fun userIdToUser(uuid: String): User = User.Builder().withUuid(uuid).build()

    companion object {
        val MAPPER: ExternalIdMapper = Mappers.getMapper(ExternalIdMapper::class.java)
    }
}

data class TestEntity(
    val isCool: Boolean,
    val userId: String,
)
