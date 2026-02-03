package hr.bmestric.formulaone.framework

import hr.bmestric.formulaone.data.impl.DriverRepositoryImpl
import hr.bmestric.formulaone.data.impl.MeetingRepositoryImpl
import hr.bmestric.formulaone.data.impl.SessionRepositoryImpl
import hr.bmestric.formulaone.data.impl.SessionResultRepositoryImpl
import hr.bmestric.formulaone.data.impl.StartingGridRepositoryImpl
import hr.bmestric.formulaone.data.impl.WeatherRepositoryImpl
import hr.bmestric.formulaone.data.remote.NetworkModule
import hr.bmestric.formulaone.data.remote.api.DriverApi
import hr.bmestric.formulaone.data.remote.api.MeetingApi
import hr.bmestric.formulaone.data.remote.api.SessionApi
import hr.bmestric.formulaone.data.remote.api.SessionResultApi
import hr.bmestric.formulaone.data.remote.api.StartingGridApi
import hr.bmestric.formulaone.data.remote.api.WeatherApi
import hr.bmestric.formulaone.domain.repository.DriverRepository
import hr.bmestric.formulaone.domain.repository.MeetingRepository
import hr.bmestric.formulaone.domain.repository.SessionRepository
import hr.bmestric.formulaone.domain.repository.SessionResultRepository
import hr.bmestric.formulaone.domain.repository.StartingGridRepository
import hr.bmestric.formulaone.domain.repository.WeatherRepository

object RepositoryProvider {
    private val driverApi: DriverApi by lazy {
        NetworkModule.createService(DriverApi::class.java)
    }
    private val sessionApi: SessionApi by lazy {
        NetworkModule.createService(SessionApi::class.java)
    }

    private val sessionResultApi: SessionResultApi by lazy {
        NetworkModule.createService(SessionResultApi::class.java)
    }

    private val startingGridApi: StartingGridApi by lazy {
        NetworkModule.createService(StartingGridApi::class.java)
    }

    private val weatherApi: WeatherApi by lazy {
        NetworkModule.createService(WeatherApi::class.java)
    }

    private val meetingApi: MeetingApi by lazy {
        NetworkModule.createService(MeetingApi::class.java)
    }

    val sessionRepository: SessionRepository by lazy {
        SessionRepositoryImpl(sessionApi)
    }
    val driverRepository: DriverRepository by lazy {
        DriverRepositoryImpl(driverApi, sessionApi)
    }
    val sessionResultRepository: SessionResultRepository by lazy {
        SessionResultRepositoryImpl(sessionResultApi)
    }
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(weatherApi)
    }
    val startingGridRepository: StartingGridRepository by lazy {
        StartingGridRepositoryImpl(startingGridApi)
    }
    val meetingRepository: MeetingRepository by lazy {
        MeetingRepositoryImpl(meetingApi)
    }
}
