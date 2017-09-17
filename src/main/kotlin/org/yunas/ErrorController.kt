package org.yunas

import javax.servlet.http.HttpServletRequest

/**
 * Custom ErrorController in Yunas.
 *
 * @author yosuke kobayashi
 */
typealias ErrorController = (request: HttpServletRequest, statusCode: Int) -> Any
