/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.device.processes

import co.herod.adbwrapper.S.Companion.PIDOF
import co.herod.adbwrapper.S.Companion.SHELL

fun cmdStringPid(packageName: String) = "$SHELL $PIDOF $packageName"