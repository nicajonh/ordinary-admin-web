package com.llh.server.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.support.DefaultPropertySourceFactory
import org.springframework.core.io.support.EncodedResource
import org.springframework.stereotype.Component

/**
 * CodeGenProperties
 *
 * CreatedAt: 2020-07-07 20:51
 *
 * @author llh
 */
@PropertySource(value = ["classpath:code-gen.yml"], factory = ResponseFactory::class)
@Component
@ConfigurationProperties(prefix = "code-gen-properties")
class CodeGenProperties {

    var typeMap: Map<String, String> = mutableMapOf()
    var typeMapJs: Map<String, String> = mutableMapOf()
    var auth: String? = null
    var tablePrefix: String? = null
    var ignoreFields: String? = null

}

/** 支持yml文件读取 */
class ResponseFactory : DefaultPropertySourceFactory() {

    override fun createPropertySource(name: String?, resource: EncodedResource): org.springframework.core.env.PropertySource<*> {
        if (resource == null) // 调用java代码，真有可能是null
            return super.createPropertySource(name, resource)
        return YamlPropertySourceLoader().load(resource.resource.filename, resource.resource)[0]
    }
}
