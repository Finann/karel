package parsing

const val EOF = '\u0000'

abstract class LexerBase(private val input: String) {

    var start: Int = -1
        private set

    var index: Int = -1
        private set

    fun startAtIndex() {
        start = index
    }

    var current: Char = next()
        private set

    fun next(): Char {
        current = if (++index < input.length) input[index] else EOF
        return current
    }

    protected fun lexeme(): String {
        return input.substring(start, index)
    }

    protected fun token(kind: TokenKind): Token {
        return token(kind, lexeme())
    }

    protected fun token(kind: TokenKind, lexeme: String): Token {
        return Token(kind, start, lexeme)
    }

    protected fun pooled(kind: TokenKind): Token {
        return token(kind, kind.show())
    }

    protected fun nextPooled(kind: TokenKind): Token {
        next()
        return pooled(kind)
    }

    protected fun error(message: String): Nothing {
        throw Diagnostic(index, message)
    }
}
