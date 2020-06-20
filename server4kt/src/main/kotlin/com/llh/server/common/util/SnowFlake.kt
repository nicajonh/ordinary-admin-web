package com.llh.server.common.util

/**
 * SnowFlake 分布式id生成算法。
 * 由idea插件将Java代码转为kotlin的，可读性较差。
 * 能用，以后空闲了再来捋一捋
 *
 * CreatedAt: 2020-06-11 22:27
 *
 * @author llh
 */
class SnowFlake(datacenterId: Long, machineId: Long) {
    private val datacenterId //数据中心
        : Long
    private val machineId //机器标识
        : Long
    private var sequence = 0L //序列号
    private var lastStmp = -1L //上一次时间戳

    /**
     * 产生下一个ID
     *
     * @return
     */
    @Synchronized
    fun nextId(): Long {
        var currStmp = newstmp
        if (currStmp < lastStmp) {
            throw RuntimeException("Clock moved backwards.  Refusing to generate id")
        }
        if (currStmp == lastStmp) {
            println("???")
            //相同毫秒内，序列号自增
            sequence = sequence + 1 and MAX_SEQUENCE
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = nextMill
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L
        }
        println("currStmp: $currStmp  sequence: $sequence lastStmp:$lastStmp TIMESTMP_LEFT: $TIMESTMP_LEFT")
        lastStmp = currStmp
        //时间戳部分
        return currStmp - START_STMP shl TIMESTMP_LEFT.toInt() or (
            datacenterId shl DATACENTER_LEFT.toInt() //数据中心部分
            ) or (machineId shl MACHINE_LEFT.toInt() //机器标识部分
            ) or sequence //序列号部分
    }

    private val nextMill: Long
        get() {
            var mill = newstmp
            while (mill <= lastStmp) {
                mill = newstmp
            }
            return mill
        }

    private val newstmp: Long
        get() = System.currentTimeMillis()

    companion object {
        /**
         * 起始的时间戳
         */
        private const val START_STMP = 1480166465631L

        /**
         * 每一部分占用的位数
         */
        private const val SEQUENCE_BIT: Long = 12 //序列号占用的位数
        private const val MACHINE_BIT: Long = 5 //机器标识占用的位数
        private const val DATACENTER_BIT: Long = 5 //数据中心占用的位数

        /**
         * 每一部分的最大值
         */
        private const val MAX_DATACENTER_NUM = -1L xor (-1L shl DATACENTER_BIT.toInt())
        private const val MAX_MACHINE_NUM = -1L xor (-1L shl MACHINE_BIT.toInt())
        private const val MAX_SEQUENCE = -1L xor (-1L shl SEQUENCE_BIT.toInt())

        /**
         * 每一部分向左的位移
         */
        private const val MACHINE_LEFT = SEQUENCE_BIT
        private const val DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT
        private const val TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT
    }

    init {
        require(!(datacenterId > MAX_DATACENTER_NUM || datacenterId < 0)) { "datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0" }
        require(!(machineId > MAX_MACHINE_NUM || machineId < 0)) { "machineId can't be greater than MAX_MACHINE_NUM or less than 0" }
        this.datacenterId = datacenterId
        this.machineId = machineId
    }
}
