package com.jetbrains.handson.kmm.shared.cache

import com.jetbrains.handson.kmm.shared.entity.Links
import com.jetbrains.handson.kmm.shared.entity.Rocket
import com.jetbrains.handson.kmm.shared.entity.RocketLaunch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    /**
     * Drop all collections in the DB
     */
    internal fun clearDatabase() {
        dbQuery.removeAllLaunches()
        dbQuery.removeAllRockets()
    }

    /**
     * Get a list of all launches in the database
     */
    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    /**
     * Decode a db launch into a RocketLaunch class business domain object
     */
    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        launchYear: Int,
        rocketId: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        missionPatchUrl: String?,
        articleUrl: String?,
        rocket_id: String?,
        name: String?,
        type: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            launchYear = launchYear,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            rocket = Rocket(
                id = rocketId,
                name = name!!,
                type = type!!
            ),
            links = Links(
                missionPatchUrl = missionPatchUrl,
                articleUrl = articleUrl
            )
        )
    }

    /**
     * Create a list of db transactions to insert launch objects into the db
     * Also insert the launches
     */
    internal fun createLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                val rocket = dbQuery.selectRocketById(launch.rocket.id).executeAsOneOrNull()
                if (rocket == null) {
                    insertRocket(launch)
                }
            }
        }
    }

    /**
     * insert a rocket into the db
     */
    private fun insertRocket(launch: RocketLaunch) {
        dbQuery.insertRocket(
            id = launch.rocket.id,
            name = launch.rocket.name,
            type = launch.rocket.type
        )
    }

    /**
     * insert a launch into the db
     */
    private fun insertLaunch(launch: RocketLaunch) {
        dbQuery.insertLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.missionName,
            launchYear = launch.launchYear,
            rocketId = launch.rocket.id,
            details = launch.details,
            launchSuccess = launch.launchSuccess ?: false,
            launchDateUTC = launch.launchDateUTC,
            missionPatchUrl = launch.links.missionPatchUrl,
            articleUrl = launch.links.articleUrl
        )
    }

}