package work.racka.thinkrchive.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import work.racka.thinkrchive.billing.BillingManager
import work.racka.thinkrchive.repository.BillingRepository

@Module
@InstallIn(ViewModelComponent::class)
object BillingModule {

    @ViewModelScoped
    @Provides
    fun providesBillingRepository(
        billingManager: BillingManager
    ) = BillingRepository(billingManager)


    @ViewModelScoped
    @Provides
    fun providesBillingManager(
        @ApplicationContext context: Context
    ): BillingManager = BillingManager(context)
}