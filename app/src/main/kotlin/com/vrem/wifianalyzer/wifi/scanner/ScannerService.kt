/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2020 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.vrem.wifianalyzer.wifi.scanner

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Handler
import com.vrem.wifianalyzer.MainActivity
import com.vrem.wifianalyzer.settings.Settings
import com.vrem.wifianalyzer.wifi.model.WiFiData

interface ScannerService {
    fun update()
    fun wiFiData(): WiFiData
    fun register(updateNotifier: UpdateNotifier): Boolean
    fun unregister(updateNotifier: UpdateNotifier): Boolean
    fun pause()
    fun running(): Boolean
    fun resume()
    fun stop()
    fun toggle()
}

fun makeScannerService(mainActivity: MainActivity, handler: Handler, settings: Settings): ScannerService {
    val scanner = Scanner(mainActivity.wiFiManagerWrapper(), settings)
    scanner.periodicScan = PeriodicScan(scanner, handler, settings)
    scanner.resume()
    return scanner
}

internal fun MainActivity.wiFiManager(): WifiManager = this.applicationContext.wiFiManager()

internal fun Context.wiFiManager(): WifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager

internal fun MainActivity.wiFiManagerWrapper(): WiFiManagerWrapper = WiFiManagerWrapper(this.wiFiManager())
