package com.llh.server.common.util

/**
 * SnowFlake4kt 雪花算法
 *
 * CreatedAt: 2020-06-21 00:11
 *
 * @author llh
 */
object SnowFlake4kt {

    /** 机器 id */
    private var workerId = 3  // 5bit

    /** 数据中心id */
    private var dataCenterId = 3  // 5bit

    //长度为5位
    private var workerIdBits = 5
    private var dataCenterIdBits = 5

    /**
     * 最大机器 id 的值
     * 最大值 -1 左移 5，得结果a，-1 异或 a：利用位运算计算出5位能表示的最大正整数是多少
     */
    private var maxWorkerId = -1L xor (-1L shl workerIdBits)

    /** 最大数据中心id的值 */
    private var maxDataCenterId = -1L xor (-1L shl dataCenterIdBits)


    /** 初始时间戳 */
    private var startTimestamp = 761243268000L  // 1994年

    /** 序列号:用来记录本机上相同毫秒内产生的不同id。 */
    private var sequence = -1 // 12bit
    private var sequenceBits = 12 //12bit
    private var sequenceMax = -1L xor (-1L shl sequenceBits)  //4095


    // 需要左移的位数
    /** workerId需要左移的位数  */
    private var workerIdShift = sequenceBits  //12bit

    /** dataCenterId需要左移的位数  */
    private var dataCenterIdShift = sequenceBits + workerIdBits //12+5=17bit

    /** timestamp需要左移的位数  */
    private var timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits //12+5+5=22bit

    /** 上次时间戳，初始值为负数 */
    private var lastTimestamp = -1L


    /**
     * 配置工作区与机器id
     * 可选的。
     */
    fun initConfig(workerId: Int, dataCenterId: Int) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw IllegalArgumentException("worker Id can't be greater than $maxWorkerId or less than 0")
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw IllegalArgumentException("dataCenter  Id can't be greater than $maxDataCenterId or less than 0")
        }
        SnowFlake4kt.workerId = workerId
        SnowFlake4kt.dataCenterId = dataCenterId
    }

    /**
     * 获取系统时间戳
     */
    private fun currentTime(): Long = System.currentTimeMillis()

    /**
     * 获取时间戳，不小于上次使用的时间戳
     */
    private fun tilNextMillis(): Long {
        var timestamp = currentTime()
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime()
        }
        return timestamp
    }

    /**
     * 产生下一个ID
     */
    @Synchronized
    fun nextId(): Long {
        var currTime = currentTime()
        if (currTime < lastTimestamp) {
            throw RuntimeException("Clock moved backwards.  Refusing to generate id")
        }
        if (currTime == lastTimestamp) {
            // 通过位与运算保证计算的结果范围始终是 0-4095
            sequence = (sequence + 1) and sequenceMax.toInt()
            if (sequence == 0) {
                currTime = tilNextMillis()
            }
        } else
            sequence = 0
        // 将上次时间戳值刷新
        lastTimestamp = currTime
        return (currTime - startTimestamp) shl timestampLeftShift or (
            dataCenterId shl dataCenterIdShift //数据中心部分
            ).toLong() or (workerId shl workerIdShift //机器标识部分
            ).toLong() or sequence.toLong() //序列号部分
    }

}
