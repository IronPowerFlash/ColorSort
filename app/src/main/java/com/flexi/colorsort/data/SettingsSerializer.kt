package com.flexi.colorsort.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<UserAppSettings> {
    override val defaultValue: UserAppSettings = UserAppSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserAppSettings {
        try{
            return UserAppSettings.parseFrom(input)
        }
        catch (exception: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(
        t: UserAppSettings,
        output: OutputStream,
    ) {
        t.writeTo(output)
    }
}