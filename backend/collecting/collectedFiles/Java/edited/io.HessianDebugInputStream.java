

package com.alibaba.com.caucho.hessian.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HessianDebugInputStream extends InputStream {
    private InputStream _is;

    private HessianDebugState _state;

    
    public HessianDebugInputStream(InputStream is, PrintWriter dbg) {
        _is = is;

        if (dbg == null)
            dbg = new PrintWriter(System.out);

        _state = new HessianDebugState(dbg);
    }

    
    public HessianDebugInputStream(InputStream is, Logger log, Level level) {
        this(is, new PrintWriter(new LogWriter(log, level)));
    }

    public void startTop2() {
        _state.startTop2();
    }

    
    public int read()
            throws IOException {
        int ch;

        InputStream is = _is;

        if (is == null)
            return -1;
        else {
            ch = is.read();
        }

        _state.next(ch);

        return ch;
    }

    
    public void close()
            throws IOException {
        InputStream is = _is;
        _is = null;

        if (is != null)
            is.close();

        _state.println();
    }

    static class LogWriter extends Writer {
        private Logger _log;
        private Level _level;
        private StringBuilder _sb = new StringBuilder();

        LogWriter(Logger log, Level level) {
            _log = log;
            _level = level;
        }

        public void write(char ch) {
            if (ch == '\n' && _sb.length() > 0) {
                _log.log(_level, _sb.toString());
                _sb.setLength(0);
            } else
                _sb.append((char) ch);
        }

        public void write(char[] buffer, int offset, int length) {
            for (int i = 0; i < length; i++) {
                char ch = buffer[offset + i];

                if (ch == '\n' && _sb.length() > 0) {
                    _log.log(_level, _sb.toString());
                    _sb.setLength(0);
                } else
                    _sb.append((char) ch);
            }
        }

        public void flush() {
        }

        public void close() {
        }
    }
}
