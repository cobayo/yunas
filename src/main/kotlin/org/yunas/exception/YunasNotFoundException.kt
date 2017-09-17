package org.yunas.exception

/**
 * YunasNotFoundException.
 */
class YunasNotFoundException internal constructor(private val code: String, message: String) : RuntimeException(message)
