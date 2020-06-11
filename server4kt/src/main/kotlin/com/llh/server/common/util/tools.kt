/**
 * tools
 *
 * CreatedAt: 2020-06-08 22:08
 *
 * @author llh
 */
package com.llh.server.common.util

import java.util.UUID


fun uuidStr(): String {
    return UUID.randomUUID().toString().replace("-", "")
}
