package com.geccal.bibliotecainfantil.infra.configuration

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

object EnvironmentConfiguration {
    private val config: Config = ConfigFactory.load("sysEnv.conf").resolve()

    fun getString(propertyName: String): String = config.getString(propertyName)
    fun getInt(propertyName: String): Int = config.getInt(propertyName)
    fun getLong(propertyName: String): Long = config.getLong(propertyName)
    fun getBoolean(propertyName: String): Boolean = config.getBoolean(propertyName)

    fun getOptionalString(propertyName: String) = if (config.hasPath(propertyName)) getString(propertyName) else null
}
