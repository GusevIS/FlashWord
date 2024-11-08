package com.example.flashword.di.components

import com.example.flashword.di.LoggedUserScope
import dagger.Subcomponent

@Subcomponent
@LoggedUserScope
interface UserComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }


}