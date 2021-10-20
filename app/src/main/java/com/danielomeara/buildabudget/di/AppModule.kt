package com.danielomeara.buildabudget.di

import android.app.Application
import androidx.room.Room
import com.danielomeara.buildabudget.features.bottomnavigation.budget.data.repository.BudgetRepositoryImpl
import com.danielomeara.buildabudget.features.bottomnavigation.budget.domain.repository.BudgetRepository
import com.danielomeara.buildabudget.database.BuildABudgetDatabase
import com.danielomeara.buildabudget.utils.keyvaluestore.KeyValueStore
import com.danielomeara.buildabudget.utils.keyvaluestore.KeyValueStoreFactory
import com.danielomeara.buildabudget.features.login.data.repository.UserRepositoryImpl
import com.danielomeara.buildabudget.features.login.domain.models.User
import com.danielomeara.buildabudget.features.login.domain.repository.UserRepository
import com.danielomeara.buildabudget.features.login.domain.usecases.AddUser
import com.danielomeara.buildabudget.features.login.domain.usecases.GetUser
import com.danielomeara.buildabudget.utils.LOGGED_IN_USER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKeyStoreValue(app: Application): KeyValueStore {
        return KeyValueStoreFactory.create(app, false)
    }

    @Provides
    @Singleton
    fun provideBuildABudgetDatabase(app: Application): BuildABudgetDatabase {
        return Room.databaseBuilder(
            app,
            BuildABudgetDatabase::class.java,
            BuildABudgetDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: BuildABudgetDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun provideBudgetRepository(db: BuildABudgetDatabase): BudgetRepository {
        return BudgetRepositoryImpl(db.budgetDao)
    }

    @Provides
    @Singleton
    fun provideAddUserUseCase(userRepository: UserRepository, budgetRepository: BudgetRepository): AddUser {
        return AddUser(userRepository, budgetRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(repository: UserRepository): GetUser {
        return GetUser(repository)
    }

    @Provides
    @Singleton
    fun provideUser(keyValueStore: KeyValueStore): User {
        return keyValueStore.getSerializable(
            LOGGED_IN_USER,
            User(id = -1, username = "Username", password = "Password", 0),
            User::class.java
        )!!
    }

}