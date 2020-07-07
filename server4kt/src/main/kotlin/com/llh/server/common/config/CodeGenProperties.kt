package com.llh.server.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * CodeGenProperties
 *
 * CreatedAt: 2020-07-07 20:51
 *
 * @author llh
 */
@Component
@ConfigurationProperties(prefix = "code-gen-properties")
class CodeGenProperties {

    var typeMap: Map<String, String> = mutableMapOf()
    var auth: String? = null
    var tablePrefix: String? = null
    var ignoreFields: String? = null
}
