package com.quoders.apps.qmoves.data.source

import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface TransportRepositoryComponent {
    var repository: TransportRepository
}