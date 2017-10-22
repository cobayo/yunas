package yunas.server;

import org.eclipse.jetty.server.Server;
import yunas.configuration.Configuration;

/**
 * Server Factory For Yunas.
 *
 * @author Yosuke Kobayashi
 */
public interface EmbeddedServerFactory {

    public Server create(Configuration conf);
}
