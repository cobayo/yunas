package org.yunas.handler

import javax.servlet.ServletOutputStream
import javax.servlet.WriteListener


class MockServletOutputStream : ServletOutputStream {


    constructor()  {}

    override fun isReady(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun write(b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setWriteListener(writeListener: WriteListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}