package com.gholem.moneylab.features.template.navigation

sealed class TemplateNavigationEvent {
    object ToNextScreen : TemplateNavigationEvent()
    object ToPreviousScreen : TemplateNavigationEvent()
    object ToErrorScreen : TemplateNavigationEvent()
    data class TimplateData(val age: Int): TemplateNavigationEvent()
}