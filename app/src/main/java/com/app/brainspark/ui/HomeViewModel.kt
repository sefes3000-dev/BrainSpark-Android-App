package com.app.brainspark.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.brainspark.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val streak: Int = 1,
    val coins: Int = 50,
    val currentRiddle: String = "أتحدث بلا فم وأسمع بلا أذن. ليس لي جسد، لكنني أحيا مع الرياح. ما أنا؟",
    val isAdLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            // قراءة البيانات المحفوظة محلياً
            userPreferences.streak.collect { streak ->
                _uiState.value = _uiState.value.copy(streak = streak)
            }
        }
        viewModelScope.launch {
            userPreferences.coins.collect { coins ->
                _uiState.value = _uiState.value.copy(coins = coins)
            }
        }
    }

    fun solveRiddle() {
        viewModelScope.launch {
            userPreferences.incrementStreak()
            userPreferences.addCoins(10)
        }
    }

    fun showInterstitialAd() {
        // محاكاة مشاهدة إعلان وكسب العملات
        _uiState.value = _uiState.value.copy(isAdLoading = true)
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000) // محاكاة وقت الإعلان
            userPreferences.addCoins(50)
            _uiState.value = _uiState.value.copy(isAdLoading = false)
        }
    }
}
