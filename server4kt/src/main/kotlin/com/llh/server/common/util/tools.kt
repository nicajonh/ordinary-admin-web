/**
 * tools
 *
 * CreatedAt: 2020-06-08 22:08
 *
 * @author llh
 */
package com.llh.server.common.util


fun uuidStr(): String {
    return "${SnowFlake4kt.nextId()}"
}
