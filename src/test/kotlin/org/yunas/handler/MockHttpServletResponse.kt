package org.yunas.handler

import java.io.PrintWriter
import java.util.*
import javax.servlet.ServletOutputStream
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


class MockHttpServletResponse : HttpServletResponse {

    var mockContentType : String = ""

    var mockStatus: Int = 200

    override fun encodeURL(url: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun encodeUrl(url: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addIntHeader(name: String?, value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCookie(cookie: Cookie?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun encodeRedirectUrl(url: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun flushBuffer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun encodeRedirectURL(url: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendRedirect(location: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBufferSize(size: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocale(): Locale {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendError(sc: Int, msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendError(sc: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setContentLengthLong(len: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCharacterEncoding(charset: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDateHeader(name: String?, date: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLocale(loc: Locale?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHeaders(name: String?): MutableCollection<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addHeader(name: String?, value: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setContentLength(len: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBufferSize(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetBuffer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reset() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setDateHeader(name: String?, date: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStatus(): Int {
        return mockStatus //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCharacterEncoding(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCommitted(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setStatus(sc: Int) {
         mockStatus = sc //To change body of created functions use File | Settings | File Templates.
    }

    override fun setStatus(sc: Int, sm: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHeader(name: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContentType(): String {
        return mockContentType//To change body of created functions use File | Settings | File Templates.
    }

    override fun getWriter(): PrintWriter {
        return PrintWriter(outputStream)    //To change body of created functions use File | Settings | File Templates.
    }

    override fun containsHeader(name: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setIntHeader(name: String?, value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHeaderNames(): MutableCollection<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setHeader(name: String?, value: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOutputStream(): ServletOutputStream {
       return MockServletOutputStream() //To change body of created functions use File | Settings | File Templates.
    }

    override fun setContentType(type: String?) {
        mockContentType = type.orEmpty() //To change body of created functions use File | Settings | File Templates.
    }
}