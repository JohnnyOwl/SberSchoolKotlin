abstract class Validator<T> {
    abstract fun validate(value: T?): List<ErrorCode>
}

class NameValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val regex = "[А-Я][а-я]+".toRegex()

        if (value.isNullOrEmpty()) {
            return listOf(ErrorCode.NULL_VALUE)
        }
        if (!value.matches(regex)) {
            return listOf(ErrorCode.INVALID_CHARACTER_NAME)
        }
        if (value.length > 16) {
            return listOf(ErrorCode.INVALID_SIZE_NAME)
        }

        return emptyList()
    }
}

class PhoneValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val regex = "(7|8)\\d+".toRegex()

        if (value == null) {
            return listOf(ErrorCode.NULL_VALUE)
        }
        if (!value.matches(regex)) {
            return listOf(ErrorCode.INVALID_CHARACTER_PHONE)
        }
        if (value.length != 11) {
            return listOf(ErrorCode.INVALID_SIZE_PHONE)
        }

        return emptyList()
    }
}

class EmailValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val regex = "[A-z]+@[A-z]+.[A-z]+".toRegex()

        if (value.isNullOrEmpty()) {
            return listOf(ErrorCode.NULL_VALUE)
        }
        if (!value.matches(regex)) {
            return listOf(ErrorCode.INVALID_CHARACTER_EMAIL)
        }
        if (value.length > 32) {
            return listOf(ErrorCode.INVALID_SIZE_EMAIL)
        }

        return emptyList()
    }
}

class SnilsValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val regex = "[0-9]+".toRegex()

        if (value == null) {
            return listOf(ErrorCode.NULL_VALUE)
        }
        if (!value.matches(regex)) {
            return listOf(ErrorCode.INVALID_CHARACTER_SNILS)
        }
        if (value.length > 11) {
            return listOf(ErrorCode.INVALID_SIZE_SNILS)
        }
        if (validateCheckNumber(value) % 101 != value.substring(9, 11).toInt()) {
            return listOf(ErrorCode.INVALID_SNILS_CHECK_NUMBER)
        }

        return emptyList()
    }

    private fun validateCheckNumber(value: String): Int {
        var i = 9
        var checkNumber = 0

        value.substring(0, 9).forEach {
            checkNumber += Character.getNumericValue(it) * i
            i--
        }

        return checkNumber
    }
}