package info.nightscout.androidaps.plugins.pump.omnipod.dash.driver.comm.message

import info.nightscout.androidaps.logging.LTag
import info.nightscout.androidaps.plugins.pump.omnipod.dash.driver.comm.ltk.LTKExchanger
import info.nightscout.androidaps.utils.extensions.toHex
import java.nio.ByteBuffer

data class Id(val address: ByteArray) {
    init {
        require(address.size == 4)
    }

    /**
     * Used to obtain podId from controllerId
     */
    fun increment(): Id {
        val nodeId = address.copyOf()
        nodeId[3] = (nodeId[3].toInt() and -4).toByte()
        nodeId[3] = (nodeId[3].toInt() or PERIPHERAL_NODE_INDEX).toByte()
        return Id(nodeId)
    }

    override fun toString(): String {
        val asInt = ByteBuffer.wrap(address).getInt()
        return "${asInt}/${address.toHex()}"
    }

    companion object {
        private val PERIPHERAL_NODE_INDEX = 1 // TODO: understand the meaning of this value. It comes from preferences

        fun fromInt(v: Int): Id {
            return Id(ByteBuffer.allocate(4).putInt(v).array())
        }
    }

}
