package yunas.server

import org.eclipse.jetty.server.Server
import yunas.configuration.Configuration

/**
 * Server Factory For Yunas.
 *
 * @author Yosuke Kobayashi
 */
interface EmbeddedServerFactory {

    fun create(conf: Configuration): Server
}
