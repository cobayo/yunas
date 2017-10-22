package yunas.server

import yunas.configuration.Configuration
import org.eclipse.jetty.server.Server

/**
 * Server Factory For Yunas.
 *
 * @author Yosuke Kobayashi
 */
interface EmbeddedServerFactory {

    fun create(conf: Configuration): Server
}
