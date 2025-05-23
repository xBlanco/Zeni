// Archivo: com/zeni/core/di/RepositoryModule.kt
import com.zeni.core.domain.repository.HotelRepository
import com.zeni.core.data.repository.HotelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHotelRepository(
        impl: HotelRepositoryImpl
    ): HotelRepository
}