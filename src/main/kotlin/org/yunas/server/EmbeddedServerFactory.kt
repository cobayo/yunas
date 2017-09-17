package org.yunas.server

import org.yunas.configuration.Configuration
import org.eclipse.jetty.server.Server

/**
 * Server Factory For Yunas.
 *
 * @author Yosuke Kobayashi
 */
interface EmbeddedServerFactory {

    fun create(conf: Configuration): Server
}
