package utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.datastore.dataStorePreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus

interface CoroutinesComponent {
    val mainImmediateDispatcher: CoroutineDispatcher
    val applicationScope: CoroutineScope
}

internal class CoroutinesComponentImpl private constructor() : CoroutinesComponent {

    companion object {
        fun create(): CoroutinesComponent = CoroutinesComponentImpl()
    }

    override val mainImmediateDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
    override val applicationScope: CoroutineScope
        get() = CoroutineScope(
            SupervisorJob() + mainImmediateDispatcher,
        )
}

interface CoreComponent : CoroutinesComponent {
    val appPreferences: AppPreferences
}

internal class CoreComponentImpl internal constructor() : CoreComponent,
    CoroutinesComponent by CoroutinesComponentImpl.create() {

    private val dataStore: DataStore<Preferences> = dataStorePreferences(
        corruptionHandler = null,
        coroutineScope = applicationScope + Dispatchers.IO,
        migrations = emptyList()
    )

    override val appPreferences : AppPreferences = AppPreferencesImpl(dataStore)
}